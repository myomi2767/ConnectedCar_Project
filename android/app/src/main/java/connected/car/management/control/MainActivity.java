package connected.car.management.control;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import connected.car.management.HttpHandler.StringURLHttpHandler;
import connected.car.management.R;
import connected.car.management.application.MyApplication;
import connected.car.management.car.CarVO;
import connected.car.management.car.RegisterCarActivity;
import connected.car.management.controlresult.RemoteControlResultActivity;
import connected.car.management.map.MapActivity;
import connected.car.management.map.MapFragment;
import connected.car.management.member.MemberVO;
import connected.car.management.period.PeriodFragment;

public class MainActivity extends AppCompatActivity{

    //====로그인 액티비티에서 받아온 변수들 -> 연결된 모든 프래그먼트에서 쉽게 쓰려고 일단 적어놓음
    public String main_user_id="";
    //public String main_car_id="";
    //로그인 액티비티에서 받은 변수 끝

    //=========자동차 정보 변수들=단, 바로는 안나오고 refresh되거나 그 이후 다른 fragment로 이동했을 때 사용가능.============//
    public String main_car_brand="";
    public String main_car_model="";
    public String main_car_model_name="";
    public String main_car_fuel_type="";
    public String main_car_year="";
    public String main_drive_distatnce ="";
    public String main_car_volume="";
    public String main_special_car="";
    //========자동차에서 받은 변수 들 끝==========


    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    //fragment 선언
    CarControlFragment condition;
    PeriodFragment periodFragment;
    CarInfoFragment car_info;
    MapFragment mapFragment;

    MemberVO vo;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        toolbar = findViewById(R.id.toolbar);

        //로그인액티비티에서 로그인 성공 시 intent로 값 넘겨와서 main액티비티로 온다.
        final Intent intent = getIntent();
        //인텐트 제어조건 수정할 것!!!!!!!
        vo = intent.getParcelableExtra("userInfo");
        if(vo != null) {
            Log.d("===", "getFromLoginPage:" + vo.toString());
            //=====로그인액티비티에서 온 데이터 전역 변수에 담기//
            main_user_id = vo.getUser_id();
            MyApplication.CarInfo.setCar_id(vo.getCar_id());
        }

        condition = new CarControlFragment(MyApplication.CarInfo.getCar_id());
        periodFragment = new PeriodFragment();
        car_info = new CarInfoFragment();
        mapFragment = new MapFragment(MyApplication.CarInfo.getCar_id(), condition);
        //=====로그인액티비티에서 온 데이터 정제 끝 ///
        //car_info = new CarInfo();

        fragmentManager = getSupportFragmentManager();
        if(intent.getIntExtra("fromPeriod", 0) == 1) {
            fragmentManager.beginTransaction().replace(R.id.page,periodFragment).commitAllowingStateLoss();
        } else {
            fragmentManager.beginTransaction().replace(R.id.page,condition).commitAllowingStateLoss();
        }

        bottomNavigationView.setSelectedItemId(R.id.homeMenuItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.termMenuItem:
                        getSupportFragmentManager().beginTransaction().replace(R.id.page, periodFragment).commitAllowingStateLoss();
                        return true;
                    case R.id.locationMenuItem:
                        getSupportFragmentManager().beginTransaction().replace(R.id.page, mapFragment).commitAllowingStateLoss();
                        return true;
                    case R.id.homeMenuItem:
                        getSupportFragmentManager().beginTransaction().replace(R.id.page, condition).commitAllowingStateLoss();
                        return true;
                    case R.id.controlResultMenuItem:
                        Intent intent1 = new Intent(getApplicationContext(), RemoteControlResultActivity.class);
                        intent1.putExtra("carId",MyApplication.CarInfo.getCar_id());
                        startActivity(intent1);
                        return true;
                    case R.id.infoMenuItem:
                        getSupportFragmentManager().beginTransaction().replace(R.id.page, car_info).commitAllowingStateLoss();
                        return true;
                }
                return false;
            }
        });

        //입력한 로그인에서 car_id를 통해 자동차 정보를 가져옵니다.
        getCarInfoHttpTask carinfoTask = new getCarInfoHttpTask(MyApplication.CarInfo.getCar_id());
        carinfoTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addCar:
                Intent intent = new Intent(getApplicationContext(), RegisterCarActivity.class);
                intent.putExtra("userInfo",vo);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //로그인 한 정보를 토대로, 자동차 정보를 가져오기 위한 기능
    class getCarInfoHttpTask extends AsyncTask<Void, Void, String>{

        String url;


        public getCarInfoHttpTask(String car_id){
            url = "http://"+getString(R.string.myip)+":8088/connectedcar/mycar/getcarinfo.do?";
            url += "car_id="+car_id;
        }


        @Override
        protected String doInBackground(Void... voids) {
            return StringURLHttpHandler.requestData(url);
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null){
                Log.d("===", "포스트익스큐트스트링:" + s);
                if(s.length()>10){ //데이터가 제대로왔는지 확인하려고.
                    Log.d("===", "포스트익스큐트if/받아온데이터:" + s);
                    JSONObject jo = null;
                    try {
                        jo = new JSONObject(s);
                        main_car_brand=jo.getString("car_brand");
                        main_car_model=jo.getString("car_model");
                        main_car_fuel_type=jo.getString("car_fuel_type");
                        main_car_year=jo.getString("car_year");
                        main_drive_distatnce =jo.getString("drive_distance");
                        main_car_volume=jo.getString("car_volume");
                        main_special_car=jo.getString("special_car");
                        main_car_model_name = jo.getString("car_model_name");
                        Gson gson = new Gson();
                        MyApplication.setCarInfo(gson.fromJson(jo.toString(), CarVO.class));

                        Log.d("===","mainactivity에서 포스트이후 main_car_model_name."+main_car_model_name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }else{
                Toast.makeText(MainActivity.this,"아직 등록된 자동차 정보가 없습니다.\n" +
                        "우측 상단의 추가 버튼을 통해 내 자동차 정보를 입력해보세요!",Toast.LENGTH_LONG).show();
            }

        }
    }

}
