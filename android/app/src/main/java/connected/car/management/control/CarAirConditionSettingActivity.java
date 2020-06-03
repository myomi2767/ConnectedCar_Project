package connected.car.management.control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import connected.car.management.R;
import connected.car.management.sqlite.DBHandler;
import connected.car.management.sqlite.DBHelper;

public class CarAirConditionSettingActivity extends AppCompatActivity {

    SeekBar airseekbar;
    SeekBar enginetimeseekbar;
    TextView temperture;
    TextView engineStart;
    TextView text_select;
    Button airSettingSubmitBtn;

    DBHandler handler;

    int min = 17;
    int max = 32;
    int minTime =2;
    int maxTime =10;
    String carid = "";
    String viewtemp = "";
    String viewengine = "";
    int engineTime;

    String temp;
    String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_air_condition_setting);

        airseekbar = (SeekBar) findViewById(R.id.seekbar_air);
        enginetimeseekbar = (SeekBar) findViewById(R.id.seekbar_enginetime);
        temperture = (TextView) findViewById(R.id.text_temperture);
        engineStart = (TextView) findViewById(R.id.text_enginetime);
        //text_select = (TextView) findViewById(R.id.text_select);
        airSettingSubmitBtn = (Button) findViewById(R.id.btn_airsetting);

        handler = new DBHandler(this);

        carid = getIntent().getStringExtra("carId");

        airseekbar.setMax(max-min);
        enginetimeseekbar.setMax(maxTime-minTime);

        Cursor cursor = handler.select();
        if(handler.select().getCount()!=0) {
            while (cursor.moveToNext()) {
                temp = cursor.getString(0);
                time = cursor.getString(1);
            }
            airseekbar.setProgress(Integer.parseInt(temp)-min);
            temperture.setText(temp);
            enginetimeseekbar.setProgress(Integer.parseInt(time)-minTime);

            engineStart.setText(time);
        }else{
            airseekbar.setProgress(24-min);
            temperture.setText("24");
            enginetimeseekbar.setProgress(6-minTime);
            engineStart.setText("6");
        }

        airseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //realtemp가 최소값을 부르는 부분
                int realtemp = min + progress;
                viewtemp = realtemp+"";

                temperture.setText(viewtemp);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        enginetimeseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                engineTime = minTime + progress;

                viewengine = engineTime+"";
                engineStart.setText(viewengine);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        airSettingSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(handler.select().getCount()==0){
                    handler.insert(temperture.getText().toString(),engineStart.getText().toString());
                }else{
                    handler.update(temperture.getText().toString(),engineStart.getText().toString());
                }
                finish();
            }
        });

    }

    //SQLite에 저장하는 메소드
    /*public void insert(View v){
        ContentValues contentValues = new ContentValues();
        contentValues.put("car_id",carid);
        contentValues.put("air_temp", temperture.getText().toString() );//이거
        contentValues.put("engine_time", engineStart.getText().toString());//이거
        contentValues.put("set_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        db.insert("airsetting",null,contentValues);
        Toast.makeText(this,"contentValues삽입성공",Toast.LENGTH_SHORT).show();
    }

    //selectAll
    public void selectAll(View v){
        text_select.setText("");
        String sql = "select * from airsetting";
        Cursor cursor = db.query("airsetting",null,null,null,null,null,null);
        int count = cursor.getCount();//레코드 갯수
        Toast.makeText(this,"조회된 row : "+count,Toast.LENGTH_LONG).show();
        while(cursor.moveToNext()) {
            int airsettingno = cursor.getInt(0);
            String setime = cursor.getString(1);
            String carid = cursor.getString(2);
            String airtemp = cursor.getString(3);
            String enginetime = cursor.getString(4);

            text_select.append("번호: " + airsettingno + "\n" +
                            "저장시간: " + setime + "\n" +
                            "차 번호: " + carid + "\n" +
                            "온도: " + airtemp + "\n" +
                            "시동 유지시간: " + enginetime + "\n"+
                    "===================\n"
            );
        }
    }*/

}
