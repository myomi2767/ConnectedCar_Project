package connected.car.management.control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import connected.car.management.R;

public class CarAirConditionSettingActivity extends AppCompatActivity {

    SeekBar airseekbar;
    SeekBar airseekbar2;
    TextView temperture;
    TextView engineStart;
    Button airSettingSubmitBtn;
    int min = 17;
    int max = 28;
    int minTime =2;
    int maxTime =10;
    //int temp = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_air_condition_setting);

        airseekbar = (SeekBar) findViewById(R.id.seekbar_air);
        airseekbar2 = (SeekBar) findViewById(R.id.seekbar_air2);
        temperture = (TextView) findViewById(R.id.text_temperture);
        engineStart = (TextView) findViewById(R.id.text_engineStart);
        airSettingSubmitBtn = (Button) findViewById(R.id.btn_airsetting);

        airseekbar.setMax(max-min);
        airseekbar2.setMax(maxTime-minTime);

        airseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int realtemp = min + progress;
                String viewtemp = "";
                viewtemp = realtemp+"";
                temperture.setText("차량 내부 온도 : "+viewtemp);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        airseekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int engineTime = minTime + progress;
                String viewengine = "";
                viewengine = engineTime+"";
                engineStart.setText("시동 유지시간 : "+viewengine);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
