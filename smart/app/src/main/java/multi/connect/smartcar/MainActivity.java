package multi.connect.smartcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapPolygon;
import com.skt.Tmap.TMapTapi;
import com.skt.Tmap.TMapView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import de.nitri.gauge.Gauge;
import multi.connect.smartcar.fcm.FCMActivity;

import multi.connect.smartcar.TMapMarker.TMapCarNumber;
import multi.connect.smartcar.TMapMarker.TMapMarker;
import multi.connect.smartcar.TMapMarker.TMapRoute;
import multi.connect.smartcar.voice.Destination;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
            TMapGpsManager.onLocationChangedCallback{
        LinearLayout sendDitance;
        AsyncTaskSerial asyncTaskSerial;
        String car_id;
        Button msgCheck;
        TextView recieveArea;
        StringTokenizer token;
        InputStream is;
        InputStreamReader isr;
        BufferedReader br;
        Socket socket;
        OutputStream os;
        PrintWriter pw;
        Button btn30, btn60, btn90;
        ImageButton up, down;
        Gauge speedometer;
        TMapView tmapview;
        LinearLayout linearLayoutTmap;
        ToggleButton power;
        Bitmap carImg,startImg,endImg,carNumImg,sendmsg;

        AlertDialog alert;
        TextView distance;
        int speed = 0;
        String[] permission_list = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECORD_AUDIO
        };
        LinearLayout loading,mapViewTotal,naviSearchView;
        TMapPoint startpoint,endpoint;
        Location location;
        LocationManager lm;
        FloatingActionButton fabNavi,btnBack,fabMsg,fabCancel;
        EditText destiName;
        Button btnDesti;
        ListView destiList;
        TMapData tMapData;

        String des,loc_lat,loc_lon;
        TMapTapi tmaptapi;
        boolean isTmapApp;
        TMapGpsManager tMapGpsManager;
        AlertDialog alertDialog;
        final ArrayList<String> carnumber = new ArrayList();
        //서버에 보낼 현재위치
        String loc_sendgps;
        Timer timer1;
        //음성인식 버튼
        Button btnMic;
        Intent voiceIntent;
        SpeechRecognizer voiceRecognizer;
        Destination destination;
        //메시지 보낼 차량의 gps
        TMapPoint otherPoint;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            car_id = "82가1005";
            FCMActivity fcmActivity = new FCMActivity();
            fcmActivity.getToken(car_id);
            //findViewById 호출
            connectViews();
            //main 통신 - 메시지 주고받기
            /*infoClient = new InfoClient(this,intent.getStringExtra("carNum"));*/
            //infoClient = new InfoClient(this,carNum);

            //상대차량 gps 셋팅하기


            //이동거리 보내기
            sendDitance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SendDistance().execute();
                }
            });

            //Serial 통신
            asyncTaskSerial = new AsyncTaskSerial();
            asyncTaskSerial.execute(10,20);
            //power -setting
            power.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){

                        power.setBackgroundDrawable(getResources().getDrawable(R.drawable.switchoff));
                        Toast.makeText(MainActivity.this,"주행 보조 시스템이 켜졌습니다.",Toast.LENGTH_SHORT).show();
                        btn30.setEnabled(true);
                        btn60.setEnabled(true);
                        btn90.setEnabled(true);
                        up.setEnabled(true);
                        down.setEnabled(true);
                        new Thread(new Runnable() {
                            String message = "";
                            @Override
                            public void run() {
                                message = "system:auto_on";
                                pw.println(message);
                                pw.flush();
                            }
                        }).start();
                    }else{
                        power.setBackgroundDrawable(getResources().getDrawable(R.drawable.switchon));
                        Toast.makeText(MainActivity.this,"주행 보조 시스템이 꺼졌습니다.",Toast.LENGTH_SHORT).show();
                        btn30.setEnabled(false);
                        btn60.setEnabled(false);
                        btn90.setEnabled(false);
                        up.setEnabled(false);
                        down.setEnabled(false);
                        new Thread(new Runnable() {
                            String message = "";
                            @Override
                            public void run() {
                                message = "system:auto_off";
                                pw.println(message);
                                pw.flush();
                            }
                        }).start();
                    }
                }
            });

            //음성인식
            voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            voiceIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
            voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");
            destination = new Destination(this, destiName, tmapview, naviSearchView);
            btnMic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    voiceRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
                    voiceRecognizer.setRecognitionListener(destination);
                    voiceRecognizer.startListening(voiceIntent);
                }
            });

            speedometer.setLowerText("0");
            //power -setting
            btn30.setEnabled(false);
            btn60.setEnabled(false);
            btn90.setEnabled(false);
            up.setEnabled(false);
            down.setEnabled(false);

            destiList.setAdapter(destination.getAdapter());
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
                                    destiName.setText("");
                                    destination.getAdapter().clear();
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
                                    destiName.setText("");
                                    destination.getAdapter().clear();
                                    startpoint = tMapGpsManager.getLocation();
                                    endpoint = new TMapPoint(Double.parseDouble(loc_lat),Double.parseDouble(loc_lon));
                                    TMapRoute tMapRoute =new TMapRoute(startpoint,endpoint,MainActivity.this,startImg,endImg,tmapview);
                                    tMapRoute.search();
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

    public void connectViews() {
        //fcm 통신
        recieveArea = findViewById(R.id.receiveMsg);
        msgCheck = findViewById(R.id.msgCheck);
        sendDitance = findViewById(R.id.distanceShare);
        //Serial 통신
        distance = findViewById(R.id.distance);
        //power -setting
        power = findViewById(R.id.power);
        //계기판
        speedometer = findViewById(R.id.myGauge);
        //속도 제어
        up = findViewById(R.id.btnUp);  up.setOnClickListener(this);
        down = findViewById(R.id.btnDown);  down.setOnClickListener(this);
        btn30 = findViewById(R.id.btn30);  btn30.setOnClickListener(this);
        btn60 = findViewById(R.id.btn60);  btn60.setOnClickListener(this);
        btn90 = findViewById(R.id.btn90);  btn90.setOnClickListener(this);
        //네비 목적지 검색
        naviSearchView = findViewById(R.id.naviSearchView);
        fabMsg = findViewById(R.id.fabMsg);
        fabMsg.setOnClickListener(this);
        fabCancel = findViewById(R.id.fabCancel);
        fabCancel.setOnClickListener(this);
        fabNavi = findViewById(R.id.fabNavi);
        fabNavi.setOnClickListener(this);
        destiName = findViewById(R.id.destiName);
        btnDesti = findViewById(R.id.btnDesti);
        btnDesti.setOnClickListener(this);
        destiList = findViewById(R.id.destiList);
        //네비 목적지 검색화면에서 뒤로가기 버튼
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        //음성인식
        btnMic = findViewById(R.id.btnMic);
        //기본 화면 fragment (큰 틀)
        linearLayoutTmap = findViewById(R.id.layoutMapView);
        loading = findViewById(R.id.loading);
        mapViewTotal = findViewById(R.id.mapViewTotal);
    }

    @Override
    public void onLocationChange(Location location) {
        tmapview.setLocationPoint(location.getLongitude(),location.getLatitude());
    }

    //라떼 서버 연결
    class AsyncTaskSerial extends AsyncTask<Integer,String,String> {

        @Override
        protected String doInBackground(Integer... integers) {
            try {
                socket = new Socket("192.168.43.87", 50000);
                if (socket != null) {
                    ioWork();

                }
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            String msg;
                            try {
                                msg = br.readLine();
                                if(msg.length() > 0) {
                                    Log.d("test", "서버로 부터 수신된 메시지>>"
                                            + msg);
                                    //publishProgress(msg);
                                    filteringMsg(msg);
                                }
                            } catch (IOException e) {
                            }
                        }
                    }
                });
                t1.start();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
        private void filteringMsg(String msg){
            token = new StringTokenizer(msg,":");
            String protocol = token.nextToken();
            String message = token.nextToken();
            System.out.println("프로토콜:"+protocol+",메시지:"+message);
            if(protocol.equals("sonic")){
                publishProgress("sonic","msg",message);
            }else if(protocol.equals("speed")){
                publishProgress("speed","msg",message);
            }
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String state = values[0];
            Log.d("progress",values[0]+":::"+values[1]+":::"+values[2]);
            if(power.isChecked()) {
                if (state.equals("sonic")) {
                    distance.setText(values[2] + "cm");
                }else if(state.equals("speed")){
                    speed = Integer.parseInt(values[2]);
                    speedometer.moveToValue(speed);
                    speedometer.setLowerText(values[2]);
                }
            }else{
                distance.setText("");
            }
        }
    }
    void ioWork(){
        try {
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            os = socket.getOutputStream();
            pw = new PrintWriter(os,true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //gps 반복보내기 종료
        timer1.cancel();
        timer1 = null;


        //다른 차량 gps 받아오는것 종료
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
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
        startImg = BitmapFactory.decodeResource(this.getResources(),R.drawable.pin);
        endImg =BitmapFactory.decodeResource(this.getResources(),R.drawable.endpin);
        carNumImg = BitmapFactory.decodeResource(this.getResources(),R.drawable.carnum);
        sendmsg = BitmapFactory.decodeResource(this.getResources(),R.drawable.sendmsg);
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
        if (net_location!=null){
            location = net_location;
        }else {
            if (gps_location!=null){
                location =gps_location;
            }
        }
        tmapview.setLocationPoint(location.getLongitude(),location.getLatitude());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.INVISIBLE);
                mapViewTotal.setVisibility(View.VISIBLE);
            }
        },1900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //클라이언트 서버로 현재위치 10초마다 보내기
                timer1 = new Timer();
                TimerTask TT1 = new TimerTask() {
                    @Override
                    public void run() {
                        if (tMapGpsManager.getLocation()!=null) {
                            loc_sendgps = tMapGpsManager.getLocation().getLatitude() + "," + tMapGpsManager.getLocation().getLongitude();
                            Log.d("hgwh", loc_sendgps);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    new FCMActivity.rquestGPSThread(car_id,loc_sendgps).start();
                                }
                            }).start();
                        }
                    }
                };
                timer1.schedule(TT1, 0, 10000); //Timer 실행
            }
        },15000);

        //다른 차량 gps LocalBroadcastManager로 FCMService에서 받기
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter("otherGPS"));
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                tmapview.setLocationPoint(longitude, latitude);
                tmapview.setCenterPoint(longitude, latitude);

                if (tMapGpsManager.getLocation().equals(endpoint)){
                    tmapview.removeTMapPath();
                }
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void setGps() {
        final LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
        tMapGpsManager.setProvider(TMapGpsManager.NETWORK_PROVIDER);
        tMapGpsManager.OpenGps();

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnUp){
            if(speed<=237) {
                speed = speed + 3;
            }else {
                speed = 240;
            }
            new Thread(new Runnable() {
                String message = "";
                @Override
                public void run() {
                    message = "tablet:plus3";
                    pw.println(message);
                    pw.flush();
                }
            }).start();
            speedometer.moveToValue(speed);
            speedometer.setLowerText(Integer.toString(speed));
        }else if(v.getId()==R.id.btnDown){
            if(speed>=3) {
                speed = speed - 3;
            }else {
                speed = 0;
            }
            new Thread(new Runnable() {
                String message = "";
                @Override
                public void run() {
                    message = "tablet:minus3";
                    pw.println(message);
                    pw.flush();
                }
            }).start();
            speedometer.moveToValue(speed);
            speedometer.setLowerText(Integer.toString(speed));
        }else if(v.getId()==R.id.btn30){
            sendSpeed(30);
        }else if(v.getId()==R.id.btn60){
            sendSpeed(60);
        }else if(v.getId()==R.id.btn90){
            sendSpeed(90);
        }else if(v.getId()==R.id.btnBack){
            destiName.setText("");
            naviSearchView.setVisibility(View.INVISIBLE);
            mapViewTotal.setVisibility(View.VISIBLE);
        }else if(v.getId()==R.id.fabNavi){
            tmapview.removeTMapPath();
            naviSearchView.setVisibility(View.VISIBLE);
            mapViewTotal.setVisibility(View.INVISIBLE);
        }else if(v.getId()==R.id.btnDesti){
            destination.searchPOI();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm!=null) {
                imm.hideSoftInputFromWindow(naviSearchView.getWindowToken(), 0);
            }
        }else if(v.getId()==R.id.fabCancel){
            //취소 누를 경우 지도에 삼각형 및 번호판 지우기
            tmapview.removeTMapPolygon("Line1");
            for (int i = 0; i < carnumber.size(); i++) {
                tmapview.removeMarkerItem(carnumber.get(i));
            }
            fabCancel.hide();
            tmapview.setZoomLevel(16);
        }else if(v.getId()==R.id.fabMsg) {
            fabCancel.show();
            tmapview.setZoomLevel(19);
            //메시지 보낼때 지도에 삼각형 범위 찍기
            TMapPoint nowLoc = tMapGpsManager.getLocation();
            TMapMarker tMapMarker = new TMapMarker(nowLoc);
            tmapview.addTMapPolygon("Line1", tMapMarker.tMapPolygon());

            //지도에 메시지 보낼 상대차량 번호판 찍기
            final ArrayList alTMapPoint = new ArrayList();
            carnumber.clear();
            TMapPolyLine line = new TMapPolyLine();
            line.addLinePoint(nowLoc);
            line.addLinePoint(otherPoint);
            Double dist = line.getDistance();
            if(dist<10.0) {
                alTMapPoint.add(new TMapPoint(nowLoc.getLatitude() + 0.0002775, nowLoc.getLongitude()));//앞차
                carnumber.add("82가1008");
            }
            /*alTMapPoint.add(new TMapPoint(nowLoc.getLatitude() + 0.0003275, nowLoc.getLongitude() - 0.0004382));//앞왼차
            carnumber.add("222가2222");
            alTMapPoint.add(new TMapPoint(nowLoc.getLatitude() + 0.0003275, nowLoc.getLongitude() + 0.0004382));//앞오른차
            carnumber.add("333가3333");*/
            //carnumber.add("111가1111");
//            alTMapPoint.add(new TMapPoint(nowLoc.getLatitude() + 0.0003275, nowLoc.getLongitude() - 0.0004382));//앞왼차
//            carnumber.add("222가2222");
//            alTMapPoint.add(new TMapPoint(nowLoc.getLatitude() + 0.0003275, nowLoc.getLongitude() + 0.0004382));//앞오른차
//            carnumber.add("333가3333");
            final Bitmap carNum = Bitmap.createScaledBitmap(carNumImg, 230, 100, true);
            Bitmap sendMessage = Bitmap.createScaledBitmap(sendmsg, 70, 50, true);
            for (int i = 0; i < alTMapPoint.size(); i++) {
                TMapCarNumber tMapCarNumber = new TMapCarNumber((TMapPoint)alTMapPoint.get(i),carNum,sendMessage);
                tmapview.addMarkerItem(carnumber.get(i),tMapCarNumber.markerItem());
                Log.d("point", "다중마커 생성" + carnumber.get(i));
            }
                tmapview.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
                    @Override
                    public void onCalloutRightButton(TMapMarkerItem tMapMarkerItem) {
                        Toast.makeText(MainActivity.this, tMapMarkerItem.getID() + "풍선뷰 클릭", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        builder.setCancelable(true);
                        final View addView = LayoutInflater.from(MainActivity.this).inflate(R.layout.sendmsgview, null);
                        final Button btnTrunk = addView.findViewById(R.id.btnTrunk);
                        btnTrunk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "트렁크가 열렸습니다", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String type = "TRUNK";
                                        new FCMActivity.rquestThread(car_id,type).start();
                                    }
                                }).start();
                                tmapview.removeTMapPolygon("Line1");
                                for (int i = 0; i < carnumber.size(); i++) {
                                    tmapview.removeMarkerItem(carnumber.get(i));
                                }
                                tmapview.setZoomLevel(15);
                                fabCancel.hide();
                                afterSendMsg();
                            }
                        });
                        final Button btnWeird = addView.findViewById(R.id.btnWeird);
                        btnWeird.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "안전운전하세요", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String type = "CAUTION";
                                        new FCMActivity.rquestThread(car_id,type).start();
                                    }
                                }).start();
                                tmapview.removeTMapPolygon("Line1");
                                for (int i = 0; i < carnumber.size(); i++) {
                                    tmapview.removeMarkerItem(carnumber.get(i));
                                }
                                tmapview.setZoomLevel(15);
                                fabCancel.hide();
                                afterSendMsg();
                            }
                        });
                        final Button btnEm = addView.findViewById(R.id.btnEm);
                        btnEm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "응급상황입니다. 옆으로 비켜주세요", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String type = "EM";
                                        new FCMActivity.rquestThread(car_id,type).start();
                                    }
                                }).start();
                                tmapview.removeTMapPolygon("Line1");
                                for (int i = 0; i < carnumber.size(); i++) {
                                    tmapview.removeMarkerItem(carnumber.get(i));
                                }
                                tmapview.setZoomLevel(15);
                                fabCancel.hide();
                                afterSendMsg();
                            }
                        });
                        builder.setView(addView);
                        alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
            }
        }

        public void afterSendMsg(){
            alertDialog.dismiss();
            tmapview.removeTMapPolygon("Line1");
            for (int i = 0; i < carnumber.size(); i++) {
                tmapview.removeMarkerItem(carnumber.get(i));
            }
            tmapview.setZoomLevel(16);
            fabCancel.hide();
        }
        public void sendSpeed(int speedVal){
            speed = speedVal;
            speedometer.moveToValue(speed);
            speedometer.setLowerText(Integer.toString(speed));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    pw.println("tablet:speed"+speed);
                    pw.flush();
                }
            }).start();
        }
        private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String otherLoc = intent.getStringExtra("gps");
                String[] receiveGps = otherLoc.split(",");
                Double otherLat = Double.parseDouble(receiveGps[0]);
                Double otherLog = Double.parseDouble(receiveGps[1]);
                otherPoint = new TMapPoint(otherLat,otherLog);
            }
        };
    }
