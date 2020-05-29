package connected.car.management.control;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import connected.car.management.R;
import connected.car.management.car.RegisterCarActivity;
import connected.car.management.member.MemberVO;
import connected.car.management.period.PeriodFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //====로그인 액티비티에서 받아온 변수들 -> 연결된 모든 프래그먼트에서 쉽게 쓰려고 일단 적어놓음
    public String main_user_id="";
    public String main_car_id="";
    //로그인 액티비티에서 받은 변수 끝


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
        Log.d("===","getFromLoginPage:"+vo.toString());

        //=====로그인액티비티에서 온 데이터 전역 변수에 담기//
        main_user_id = vo.getUser_id();
        main_car_id = vo.getCar_id();
        //=====로그인액티비티에서 온 데이터 정제 끝 ///

        //car_info = new CarInfo();
        car_part = new CarExpendableFragment();
        condition = new CarControlFragment(vo.getCar_id());
        periodFragment = new PeriodFragment();
        
        fabCenter.setOnClickListener(this);
        fabTerm.setOnClickListener(this);
        fabInfo.setOnClickListener(this);


        fragmentManager.beginTransaction().add(R.id.page,condition).commit();


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

}
