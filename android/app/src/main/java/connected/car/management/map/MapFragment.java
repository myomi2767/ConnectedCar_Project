package connected.car.management.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
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
import connected.car.management.control.CarControlFragment;
import connected.car.management.control.CarRemoteControlFragment;
import connected.car.management.control.RemoteControlAsync;
import connected.car.management.map.adapter.MapRouteItem;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnCameraMoveListener{
    GoogleMap gMap;
    String[] permission_list = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    //location 정보 가져올 변수
    Button searchMyCar;

    RemoteControlAsync remoteControlAsync;
    LocationVO locationVO;
    PrintWriter pw;
    String carId;
    CarControlFragment carControlFragment;
    boolean isOnLoc;

    public MapFragment(String carId, CarControlFragment fragment) {
        // Required empty public constructor
        this.carId = carId;
        this.carControlFragment = fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_route, container, false);
        remoteControlAsync = carControlFragment.getCarRemoteControlFragment().getRemoteControlAsync();

        searchMyCar = view.findViewById(R.id.myCarSearch);
        init();

        searchMyCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_msg(v);
                while((locationVO = remoteControlAsync.getLocationVO()) == null){

                }
                Log.d("locationtest","====>"+locationVO+"");
                //setView();
                setMyLocation(locationVO.getLatitude(),locationVO.getLongitude());
                remoteControlAsync.setMessageIn(false);
            }
        });

        return view;
    }
    public void setMyLocation(double lat, double longi){
        double latitude = lat;
        double longitude = longi;

        LatLng latLng = new LatLng(latitude,longitude);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(18).build();
        Log.d("locationtest",gMap+"");
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("차량 위치");
        markerOptions.snippet("내차 위치입니다.");
        gMap.addMarker(markerOptions).showInfoWindow();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        Log.d("locationtest","onMapReady"+gMap);
        if(gMap != null) {
            gMap.getUiSettings().setZoomControlsEnabled(true);
            gMap.getUiSettings().setRotateGesturesEnabled(true);
            gMap.getUiSettings().setScrollGesturesEnabled(true);
            gMap.getUiSettings().setTiltGesturesEnabled(true);

            gMap.setOnCameraMoveListener(this);
            gMap.getUiSettings().setZoomControlsEnabled(true);
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
    }
    public void checkPermissions(String[] permission_list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 101);
        } else {
            init();
        }
    }
    public void init() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.plan_map);
        mapFragment.getMapAsync(this);
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
                if(view.getId()==R.id.myCarSearch){
                    message="search";
                }
                pw.println("location:"+message+":phone:"+carId);
                pw.flush();
            }
        }).start();
    }

    @Override
    public void onCameraMove() {

    }
}
