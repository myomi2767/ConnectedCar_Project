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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import connected.car.management.HttpHandler.StringURLHttpHandler;
import connected.car.management.R;
import connected.car.management.car.RegisterCarActivity;
import connected.car.management.member.MemberVO;
import connected.car.management.period.PeriodFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //====로그인 액티비티에서 받아온 변수들 -> 연결된 모든 프래그먼트에서 쉽게 쓰려고 일단 적어놓음
    public String main_user_id="";
    public String main_car_id="";
    //로그인 액티비티에서 받은 변수 끝

    //=========자동차 정보 변수들=단, 바로는 안나오고 refresh되거나 그 이후 다른 fragment로 갈때만 사용.============//
    public String main_car_brand="";
    public String main_car_model="";
    public String main_car_model_name="";
    public String main_car_fuel_type="";
    public String main_car_year="";
    public String main_driver_distatnce="";
    public String main_car_volume="";
    public String main_special_car="";
    //========자동차에서 받은 변수 들 끝==========


    Toolbar toolbar;
    FloatingActionButton fabCenter;
    FloatingActionButton fabTerm;
    FloatingActionButton fabInfo;
    CarControlFragment condition;
    CarExpendableFragment car_part;
    PeriodFragment periodFragment;
    CarInfoFragment car_info;

    MemberVO vo;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        fabCenter = findViewById(R.id.centerMenu);
        fabTerm = findViewById(R.id.termMenu);
        fabInfo = findViewById(R.id.infoMenu);

        fragmentManager = getSupportFragmentManager();

        //로그인액티비티에서 로그인 성공 시 intent로 값 넘겨와서 main액티비티로 온다.
        Intent intent = getIntent();
        vo = intent.getParcelableExtra("userInfo");
        if(vo != null) {
            Log.d("===", "getFromLoginPage:" + vo.toString());
            //=====로그인액티비티에서 온 데이터 전역 변수에 담기//
            main_user_id = vo.getUser_id();
            main_car_id = vo.getCar_id();
        }

        //=====로그인액티비티에서 온 데이터 정제 끝 ///
        //car_info = new CarInfo();
        car_part = new CarExpendableFragment();
        condition = new CarControlFragment(main_car_id);
        periodFragment = new PeriodFragment();

        fabCenter.setOnClickListener(this);
        fabTerm.setOnClickListener(this);
        fabInfo.setOnClickListener(this);


        fragmentManager.beginTransaction().add(R.id.page,condition).commit();

        getCarInfoHttpTask carinfoTask = new getCarInfoHttpTask(main_car_id);
        carinfoTask.execute();

        Log.d("===","car_model_detail"+main_car_model_name+"//car_fule_type"+main_car_fuel_type+"//car_brand"+main_car_brand);


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.centerMenu:
                fragmentManager.beginTransaction().replace(R.id.page, condition).commit();
                break;
            case R.id.termMenu:
                fragmentManager.beginTransaction().replace(R.id.page, periodFragment).commit();
                break;
            case R.id.infoMenu:
                break;

        }
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
                        main_driver_distatnce=jo.getString("driver_distance");
                        main_car_volume=jo.getString("car_volume");
                        main_special_car=jo.getString("special_car");
                        main_car_model_name = jo.getString("car_model_name");

                        Log.d("===","포스트이후다."+main_car_model_name);
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
