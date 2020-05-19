package multi.connect.smartcar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skt.Tmap.TMapCircle;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapTapi;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;
import java.util.HashMap;

import de.nitri.gauge.Gauge;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn30, btn60, btn90;
    ImageButton up, down;
    Gauge speedometer;
    TMapView tmapview;
    LinearLayout linearLayoutTmap;
    Bitmap carImg;
    AlertDialog alert;
    float speed = 0;
    String[] permission_list = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    LinearLayout loading,mapViewTotal,naviSearchView;
    TMapPoint tMapPoint,startpoint,endpoint;
    TMapCircle tMapCircle;
    Location location;
    LocationManager lm;
    FloatingActionButton fabNavi;
    EditText destiName;
    Button btnDesti;
    ListView destiList;
    ArrayAdapter<POI> POIAdapter;
    String des,loc_lat,loc_lon;
    TMapTapi tmaptapi;
    boolean isTmapApp;
    TMapGpsManager tMapGpsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speedometer = findViewById(R.id.myGauge);
        up = findViewById(R.id.btnUp);
        up.setOnClickListener(this);
        down = findViewById(R.id.btnDown);
        down.setOnClickListener(this);
        btn30 = findViewById(R.id.btn30);
        btn30.setOnClickListener(this);
        btn60 = findViewById(R.id.btn60);
        btn60.setOnClickListener(this);
        btn90 = findViewById(R.id.btn90);
        btn90.setOnClickListener(this);
        speedometer.setLowerText("0");
        linearLayoutTmap = findViewById(R.id.layoutMapView);
        loading = findViewById(R.id.loading);
        mapViewTotal = findViewById(R.id.mapViewTotal);
        //네비 목적지 검색
        naviSearchView = findViewById(R.id.naviSearchView);
        fabNavi = findViewById(R.id.fabNavi);
        fabNavi.setOnClickListener(this);
        destiName = findViewById(R.id.destiName);
        btnDesti = findViewById(R.id.btnDesti);
        btnDesti.setOnClickListener(this);
        destiList = findViewById(R.id.destiList);
        POIAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        destiList.setAdapter(POIAdapter);
        destiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] destiList = parent.getItemAtPosition(position).toString().split(",");
                des = destiList[0];
                String[] locList = destiList[1].split(" ");
                loc_lat = locList[1];
                loc_lon = locList[3];
                Toast.makeText(getApplicationContext(),des,Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder
                        = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder .setTitle("목적지를 "+des+"으로 설정합니다")
                        .setPositiveButton("T네비", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tmaptapi= new TMapTapi(MainActivity.this);
                                tmaptapi.setSKTMapAuthentication("l7xx69415d661c8445a8b35bd80789e07ebf");
                                isTmapApp = tmaptapi.isTmapApplicationInstalled();
                                if (isTmapApp){
                                    startpoint = tMapGpsManager.getLocation();
                                    HashMap pathInfo = new HashMap();
                                    pathInfo.put("rGoName", des);
                                    pathInfo.put("rGoY", loc_lat);
                                    pathInfo.put("rGoX", loc_lon);
                                    tmaptapi.invokeRoute(pathInfo);
                                    naviSearchView.setVisibility(View.INVISIBLE);
                                    mapViewTotal.setVisibility(View.VISIBLE);
                                }
                            }
                        })
                        .setNeutralButton("경로표시", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startpoint = tMapGpsManager.getLocation();
                                endpoint = new TMapPoint(Double.parseDouble(loc_lat),Double.parseDouble(loc_lon));
                                searchRoute(startpoint,endpoint);
                                tmapview.setZoomLevel(16);
                                naviSearchView.setVisibility(View.INVISIBLE);
                                mapViewTotal.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton("취소", null);
                alert = builder.create();
                alert.show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 1000);
        } else {
            init();
        }
    }
    private void searchPOI() {
        TMapData data = new TMapData();
        String keyword = destiName.getText().toString();
        if (!TextUtils.isEmpty(keyword)) {
            data.findAllPOI(keyword, new TMapData.FindAllPOIListenerCallback() {
                @Override
                public void onFindAllPOI(final ArrayList<TMapPOIItem> arrayList) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tmapview.removeAllMarkerItem();
                            POIAdapter.clear();

                            for (TMapPOIItem poi : arrayList) {
                                POIAdapter.add(new POI(poi));
                            }
                        }
                    });
                }
            });
        }
    }
    /*public void addMarker(TMapPOIItem poi) {
        TMapMarkerItem item = new TMapMarkerItem();
        item.setTMapPoint(poi.getPOIPoint());
        Bitmap icon = ((BitmapDrawable) ContextCompat.getDrawable(this, android.R.drawable.ic_input_add)).getBitmap();
        item.setIcon(icon);
        item.setPosition(0.5f, 1);
        item.setCalloutTitle(poi.getPOIName());
        item.setCalloutSubTitle(poi.getPOIContent());
        Bitmap left = ((BitmapDrawable) ContextCompat.getDrawable(this, android.R.drawable.ic_dialog_alert)).getBitmap();
        item.setCalloutLeftImage(left);
        Bitmap right = ((BitmapDrawable) ContextCompat.getDrawable(this, android.R.drawable.ic_input_get)).getBitmap();
        item.setCalloutRightButtonImage(right);
        item.setCanShowCallout(true);
        tmapview.addMarkerItem(poi.getPOIID(), item);
    }*/

    private void moveMap(double lat,double lng){
        tmapview.setCenterPoint(lng,lat);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return;
            }
        }
        init();
        setGps();
    }

    public void init() {
        tmapview = new TMapView(this);
        tmapview.setSKTMapApiKey("l7xx69415d661c8445a8b35bd80789e07ebf");
        tmapview.setZoomLevel(20);
        //아이콘 표시
        carImg = BitmapFactory.decodeResource(this.getResources(), R.drawable.carimoticon);
        Bitmap carIcon = Bitmap.createScaledBitmap(carImg, 30, 50, true);
        tmapview.setIcon(carIcon);
        tmapview.setIconVisibility(true);

        //현재위치를 중앙으로 이동
        tmapview.setTrackingMode(true);
        //보는 방향 전조등
        tmapview.setSightVisible(true);
        //현재 보는 방향
        tmapview.setCompassMode(true);
        //현재위치 불러오기
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location net_location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location gps_location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (net_location!=null){
            location = net_location;
        }else {
            if (gps_location!=null){
                location =gps_location;
            }
        }
        tmapview.setLocationPoint(location.getLongitude(),location.getLatitude());
        //차 반경 10m 원 표시
        tMapPoint = new TMapPoint(location.getLatitude(),location.getLongitude());
        tMapCircle = new TMapCircle();
        tMapCircle.setCenterPoint(tMapPoint);
        tMapCircle.setRadius(10);
        tMapCircle.setCircleWidth(2);
        tMapCircle.setLineColor(Color.BLUE);
        tMapCircle.setAreaColor(Color.GRAY);
        tMapCircle.setAreaAlpha(100);
        tmapview.addTMapCircle("circle1", tMapCircle);

        linearLayoutTmap.addView(tmapview);

        /*//지도에 마커찍기
        final ArrayList alTMapPoint = new ArrayList();
        alTMapPoint.add(new TMapPoint(37.576016, 126.976867));//광화문
        alTMapPoint.add(new TMapPoint(37.570432, 126.992169));// 종로3가
        alTMapPoint.add(new TMapPoint(37.570194, 126.983045));//종로5가
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mapmark);
        for(int i=0; i<alTMapPoint.size(); i++){
            TMapMarkerItem markerItem1 = new TMapMarkerItem();
            // 마커 아이콘 지정
            markerItem1.setIcon(bitmap);
            // 마커의 좌표 지정
            markerItem1.setTMapPoint((TMapPoint)alTMapPoint.get(i));
            //지도에 마커 추가
            tmapview.addMarkerItem("markerItem"+i, markerItem1);
        }*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.INVISIBLE);
                mapViewTotal.setVisibility(View.VISIBLE);
            }
        },1900);

    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                tmapview.setLocationPoint(longitude, latitude);
                tmapview.setCenterPoint(longitude, latitude);

                //차량 주변 원 따라다니기
                tmapview.removeTMapCircle("circle1");
                tMapPoint = new TMapPoint(location.getLatitude(),location.getLongitude());
                tMapCircle = new TMapCircle();
                tMapCircle.setCenterPoint(tMapPoint);
                tMapCircle.setRadius(10);
                tMapCircle.setCircleWidth(2);
                tMapCircle.setLineColor(Color.BLUE);
                tMapCircle.setAreaColor(Color.GRAY);
                tMapCircle.setAreaAlpha(100);
                tmapview.addTMapCircle("circle1", tMapCircle);

                if (tMapGpsManager.getLocation().equals(endpoint)){
                    tmapview.removeTMapPath();
                }
            }

        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    public void setGps() {
        final LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자(실내에선 NETWORK_PROVIDER 권장)
                    1000, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
        }else if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
        }
        tMapGpsManager = new TMapGpsManager(this);
        tMapGpsManager.setMinTime(1000);
        tMapGpsManager.setMinDistance(5);
        tMapGpsManager.setProvider(TMapGpsManager.GPS_PROVIDER);
        tMapGpsManager.OpenGps();
    }

    public void searchRoute(TMapPoint startpoint,TMapPoint endpoint){
        TMapData data = new TMapData();
        data.findPathData(startpoint,endpoint, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(final TMapPolyLine path) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        path.setLineWidth(5);
                        path.setLineColor(Color.RED);
                        tmapview.addTMapPath(path);
                        Bitmap s = ((BitmapDrawable)ContextCompat.getDrawable(MainActivity.this,R.drawable.startpoint)).getBitmap();
                        Bitmap e = ((BitmapDrawable)ContextCompat.getDrawable(MainActivity.this,R.drawable.endpoint)).getBitmap();
                        tmapview.setTMapPathIcon(s,e);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnUp){
            if(speed<=237) {
                speed = speed + 3;
            }else if(speed>237){
                speed = 240;
            }
            speedometer.moveToValue(speed);
            speedometer.setLowerText(Integer.toString((int)speed));
        }else if(v.getId()==R.id.btnDown){
            if(speed>=3) {
                speed = speed - 3;
            }else if(speed<3){
                speed = 0;
            }
            speedometer.moveToValue(speed);
            speedometer.setLowerText(Integer.toString((int)speed));
        }else if(v.getId()==R.id.btn30){
            speed = 30;
            speedometer.moveToValue(speed);
            speedometer.setLowerText(Integer.toString((int)speed));
        }else if(v.getId()==R.id.btn60){
            speed = 60;
            speedometer.moveToValue(speed);
            speedometer.setLowerText(Integer.toString((int)speed));
        }else if(v.getId()==R.id.btn90){
            speed = 90;
            speedometer.moveToValue(speed);
            speedometer.setLowerText(Integer.toString((int)speed));
        }else if(v.getId()==R.id.fabNavi){
            naviSearchView.setVisibility(View.VISIBLE);
            mapViewTotal.setVisibility(View.INVISIBLE);
        }else if(v.getId()==R.id.btnDesti){
            searchPOI();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm!=null) {
                imm.hideSoftInputFromWindow(naviSearchView.getWindowToken(), 0);
            }
        }
    }


}