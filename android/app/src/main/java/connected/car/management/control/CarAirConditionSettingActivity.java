package connected.car.management.control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
import connected.car.management.sqlite.DBHelper;

public class CarAirConditionSettingActivity extends AppCompatActivity {

    SeekBar airseekbar;
    SeekBar enginetimeseekbar;
    TextView temperture;
    TextView engineStart;
    TextView text_select;
    Button airSettingSubmitBtn;
    DBHelper dbHelper;
    SQLiteDatabase db;

    int min = 17;
    int max = 28;
    int minTime =2;
    int maxTime =10;
    String carid = "12가1234";
    String viewtemp = "";
    String viewengine = "";
    int engineTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_air_condition_setting);

        airseekbar = (SeekBar) findViewById(R.id.seekbar_air);
        enginetimeseekbar = (SeekBar) findViewById(R.id.seekbar_enginetime);
        temperture = (TextView) findViewById(R.id.text_temperture);
        engineStart = (TextView) findViewById(R.id.text_enginetime);
        text_select = (TextView) findViewById(R.id.text_select);
        airSettingSubmitBtn = (Button) findViewById(R.id.btn_airsetting);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        airseekbar.setMax(max-min);
        enginetimeseekbar.setMax(maxTime-minTime);

        airseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
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

    }

    //SQLite에 저장하는 메소드
    public void insert(View v){
        ContentValues contentValues = new ContentValues();
        contentValues.put("carid",carid);
        contentValues.put("airtemp", temperture.getText().toString() );//이거
        contentValues.put("enginetime", engineStart.getText().toString());//이거
        contentValues.put("setime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

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
    }

}
