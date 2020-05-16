package connected.car.management;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    BottomAppBar bottomAppBar;
    FloatingActionButton fab1,fab2,fab3;
    condition condition;
    car_part car_part;
    car_info car_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomAppBar = findViewById(R.id.bottom_bar);
        fab1 = findViewById(R.id.fab1);
        fab1.setOnClickListener(this);
        fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(this);
        fab3 = findViewById(R.id.fab3);
        fab3.setOnClickListener(this);
        car_info = new car_info();
        car_part = new car_part();
        condition = new condition();

        getSupportFragmentManager().beginTransaction().add(R.id.page,condition).commit();
    }

    @Override
    public void onClick(View v) {
        Fragment select = null;
        if(v.getId()==R.id.fab1){
            select = car_info;
        }else if(v.getId()==R.id.fab2){
            select = new condition();
        }else if(v.getId()==R.id.fab3){
            select = car_part;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.page,select).commit();

    }
}
