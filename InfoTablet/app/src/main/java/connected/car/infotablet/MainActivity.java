package connected.car.infotablet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{
    String id;
    String carNum;
    Button msgCheck;
    TextView recieveArea;
    TMapView tmapview;
    LinearLayout linearLayoutTmap;
    Bitmap carImg, startImg, endImg, carNumImg, sendmsg;
    String[] permission_list = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECORD_AUDIO
    };
    LinearLayout loading, mapViewTotal;
    Location location;
    LocationManager lm;

    TMapGpsManager tMapGpsManager;
    AlertDialog alertDialog;

    //메시지 주고받기 관련
    InfoClient infoClient;

    //mChatId = getIntent().getStringExtra("chat_Id");
    String messageType = null;

    //주행 거리 추가 버튼
    Button sendDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectViews();

        Intent intent = getIntent();
        //main 통신 - 메시지 주고받기
        id = "backCar";
        carNum = "34가6773";
        /*infoClient = new InfoClient(this,intent.getStringExtra("carNum"));*/
        infoClient = new InfoClient(this, carNum);
        messageType = getIntent().getStringExtra("messageType");
        if (messageType != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            final View receiveView = LayoutInflater.from(MainActivity.this).inflate(R.layout.receive_msg, null);
            final TextView receiveMsg = receiveView.findViewById(R.id.receiveMsg);
            final Button msgCheck = receiveView.findViewById(R.id.msgCheck);
            if (messageType.equals("EM")) {
                String text = "EM";
                receiveMsg.setText(text);
            } else if (messageType.equals("TRUNK")) {
                String text = "TRUNK";
                receiveMsg.setText(text);
            } else if (messageType.equals("CAUTION")) {
                String text = "CAUTION";
                receiveMsg.setText(text);
            }
            //이동거리 보내기
            sendDitance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SendDistance().execute();
                }
            });

            msgCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            builder.setView(receiveView);
            alertDialog = builder.create();

            alertDialog.show();
            messageType = null;

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 1000);
        } else {
            init();
        }

    }

    public void connectViews() {
        recieveArea = findViewById(R.id.receiveMsg);
        msgCheck = findViewById(R.id.msgCheck);
        //기본 화면 fragment (큰 틀)
        linearLayoutTmap = findViewById(R.id.layoutMapView);
        loading = findViewById(R.id.loading);
        mapViewTotal = findViewById(R.id.mapViewTotal);

        sendDistance = findViewById(R.id.distanceShare);
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
        tmapview.setZoomLevel(19);
        //아이콘 표시
        carImg = BitmapFactory.decodeResource(this.getResources(), R.drawable.pickupcar);
        startImg = BitmapFactory.decodeResource(this.getResources(), R.drawable.pin);
        endImg = BitmapFactory.decodeResource(this.getResources(), R.drawable.endpin);
        carNumImg = BitmapFactory.decodeResource(this.getResources(), R.drawable.carnum);
        sendmsg = BitmapFactory.decodeResource(this.getResources(), R.drawable.sendmsg);
        Bitmap carIcon = Bitmap.createScaledBitmap(carImg, 40, 60, true);

        linearLayoutTmap.addView(tmapview);
        tmapview.setIcon(carIcon);
        tmapview.setIconVisibility(true);

        //현재위치를 중앙으로 이동
        Timer timer = new Timer();

        TimerTask TT = new TimerTask() {
            @Override
            public void run() {
                // 반복실행할 구문
                //현재위치 불러오기
                tmapview.setTrackingMode(true);
            }
        };
        timer.schedule(TT, 0, 1000); //Timer 실행
        // 보는 방향 전조등
        tmapview.setSightVisible(true);
        //현재 보는 방향
        tmapview.setCompassMode(true);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location net_location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location gps_location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (net_location != null) {
            location = net_location;
        } else {
            if (gps_location != null) {
                location = gps_location;
            }
        }
        tmapview.setLocationPoint(location.getLongitude(), location.getLatitude());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.INVISIBLE);
                mapViewTotal.setVisibility(View.VISIBLE);
            }
        }, 1900);
    }

    public void setGps() {
        final LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        tMapGpsManager = new TMapGpsManager(this);
        tMapGpsManager.setMinTime(1000);
        tMapGpsManager.setMinDistance(5);
        tMapGpsManager.setProvider(TMapGpsManager.NETWORK_PROVIDER);
        tMapGpsManager.OpenGps();

    }
}