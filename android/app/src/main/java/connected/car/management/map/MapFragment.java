package connected.car.management.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import connected.car.management.R;
import connected.car.management.control.RemoteControlAsync;
import connected.car.management.map.adapter.MapRouteItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnCameraMoveListener{
    GoogleMap gMap;
    MarkerOptions markerOptions;
    String[] permission_list = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    MapLocation mapLocation;

    //검색 기준 데이터
    long arrivalTime;
    String location;
    String myAddress;

    //레이아웃
    LinearLayout container;
    RecyclerView recyclerView;
    HashMap<String, TextView> mapTextView;

    //경로 정보 list
    List<MapRouteItem> wayDataList;

    //location 정보 가져올 변수
    RemoteControlAsync remoteControlAsync;
    LocationVO locationVO;
    PrintWriter pw;
    String carId;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.map_route, container, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        if(gMap != null) {
            mapLocation = new MapLocation(getContext(), permission_list);

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(mapLocation.getMyLocation());
            markerOptions.title("나의 위치");
            markerOptions.snippet("현재 내가 있는 위치");
            gMap.addMarker(markerOptions);
            gMap.getUiSettings().setZoomControlsEnabled(true);

            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapLocation.getMyLocation(), 15));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if(result == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getContext(), "권한이 설정되지 않았습니다. 앱을 다시 실행해주세요",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        init();
        loadDirections();
    }
    public void checkPermissions(String[] permission_list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 101);
        } else {
            init();
            loadDirections();
        }
    }
    public void init() {
        FragmentManager manager = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) manager.findFragmentById(R.id.plan_map);
        mapFragment.getMapAsync(this);
    }

    public void setView() {
        mapTextView = new HashMap<String, TextView>();
        mapTextView.put("소요시간", (TextView) getActivity().findViewById(R.id.map_duration));
        mapTextView.put("출발시간", (TextView) getActivity().findViewById(R.id.map_departure_time));
        mapTextView.put("도착시간", (TextView) getActivity().findViewById(R.id.map_arrival_time));

        container = getActivity().findViewById(R.id.map_bar_container);
        recyclerView = getActivity().findViewById(R.id.map_route_list);

        wayDataList = new ArrayList<MapRouteItem>();
    }

    public void loadDirections() {
        if(gMap != null) {
            gMap.moveCamera(CameraUpdateFactory.newLatLng(mapLocation.getMyLocation()));
            gMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        }
        HttpsJson jsonWayInfo = new HttpsJson();
        jsonWayInfo.execute();

    }

    @Override
    public void onCameraMove() {

    }

    class HttpsJson extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... voids) {
            if (mapLocation == null) {
                mapLocation = new MapLocation(getContext(), permission_list);
            }
            //Log.d("test", mapLocation.getLatLngFromAddress("경기도 수원시 장안구 조원동 898") + "");
            String path = getPath(mapLocation.getMyLocation(),
                    mapLocation.getLatLngFromAddress(location),
                    System.currentTimeMillis() / 1000);
            BufferedReader in = null;
            StringBuffer sb = null;
            JSONObject json = null;
            try {
                URL url = new URL(path);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");

                if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    sb = new StringBuffer();
                    String data = null;
                    while ((data = in.readLine()) != null) {
                        data = data.replaceAll("(^\\p{Z}+|\\p{Z}+$)", "");
                        sb.append(data);
                    }
                    //
                    json = new JSONObject(sb.toString());

                    in.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            super.onPostExecute(json);
            if (json == null) {
                Log.d("test", "null이다아아아아아");
                return;
            }
            try {
                Log.d("test", json.toString());
                JSONArray legs = json.getJSONArray("routes")
                        .getJSONObject(0)
                        .getJSONArray("legs");
                //Log.d("test1", legs.toString());
                JSONObject overview_polyline = json.getJSONArray("routes")
                        .getJSONObject(0)
                        .getJSONObject("overview_polyline");
                //Log.d("test1", overview_polyline.toString());

                ArrayList<LatLng> listPoints =
                        (ArrayList<LatLng>) DecodePolyline.getPoints(overview_polyline.getString("points"));
                /*for (LatLng point : listPoints) {
                    Log.d("test", point.latitude + "," + point.longitude);
                }*/
                Polyline line = gMap.addPolyline(new PolylineOptions()
                        .color(Color.argb(220, 86, 129, 218))
                        .width(10));
                line.setPoints(listPoints);

                //setViewDetail(legs);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public String getPath(LatLng origin, LatLng destination, long time) {
            StringBuffer path = new StringBuffer("https://maps.googleapis.com/maps/api/directions/json?");
            path.append("origin=").append(origin.latitude).append(",").append(origin.longitude).append("&");
            path.append("destination=").append(destination.latitude).append(",").append(destination.longitude).append("&");
            path.append("mode=transit&");
            path.append("arrival_time=").append(time).append("&");
            path.append("language=ko&");
            path.append("key=").append("AIzaSyABcoK6IL4ctXEp3TOXQ_fxrKN8v0eP9MI");
            return path.toString();
        }
    }

    public void send_msg(final View view){
        if(pw == null) {
            Log.d("test", "null인가");
            if(remoteControlAsync.isOnPW()) {
                pw = remoteControlAsync.getPrintWriter();
                Log.d("test", pw.toString());
            }
        }
        new Thread(new Runnable() {
            String message = "";
            @Override
            public void run() {
                if(view.getId()==R.id.btnRefresh){
                    message="Z";
                }
                pw.println("job:"+message+":phone:"+carId);
                pw.flush();
            }
        }).start();
    }
}
