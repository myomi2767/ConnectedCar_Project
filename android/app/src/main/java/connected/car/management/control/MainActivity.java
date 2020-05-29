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
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import connected.car.management.R;
import connected.car.management.car.RegisterCarActivity;
import connected.car.management.member.MemberVO;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    BottomAppBar bottomAppBar;
    Toolbar toolbar;
    FloatingActionButton fab;
    CarControlFragment condition;
    CarExpendableFragment car_part;
    CarInfoFragment car_info;

    MemberVO vo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        bottomAppBar = findViewById(R.id.bottom_bar);



        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.page,car_info);
            }
        });*/

        //car_info = new CarInfo();
        car_part = new CarExpendableFragment();
        condition = new CarControlFragment();

        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        //bottomAppBar의 floating action을 가운데로 맞춰준다.
        bottomAppBar.setFabCradleRoundedCornerRadius(100);
        bottomAppBar.setFabCradleMargin(20);

        getSupportFragmentManager().beginTransaction().add(R.id.page,condition).commit();

        Intent intent = getIntent();
        vo = intent.getParcelableExtra("userInfo");
        Log.d("test", vo.toString());

    }


    @Override
    public void onClick(View v) {

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

    /*  @Override
    public void onClick(View v) {
        Fragment select = null;
        if(v.getId()==R.id.fab1){
            select = car_info;
        }else if(v.getId()==R.id.fab2){
            select = new CarControl();
        }else if(v.getId()==R.id.fab3){
            select = car_part;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.page,select).commit();

    }*/
}
