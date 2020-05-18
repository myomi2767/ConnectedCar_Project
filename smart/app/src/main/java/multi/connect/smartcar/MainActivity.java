package multi.connect.smartcar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.skt.Tmap.TMapCircle;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapTapi;
import com.skt.Tmap.TMapView;

import de.nitri.gauge.Gauge;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn30, btn60, btn90;
    ImageButton up, down;
    Gauge speedometer;
    TMapView tmapview;
    LinearLayout linearLayoutTmap;
    Bitmap carImg;
    float speed = 0;
    String[] permission_list = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    LinearLayout loading;
    TMapPoint tMapPoint;
    TMapCircle tMapCircle;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 1000);
        } else {
            init();
        }
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
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.INVISIBLE);
                linearLayoutTmap.setVisibility(View.VISIBLE);
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
                tmapview.addTMapCircle("circle", tMapCircle);
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
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자(실내에선 NETWORK_PROVIDER 권장)
                1000, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);
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
        }
    }


}