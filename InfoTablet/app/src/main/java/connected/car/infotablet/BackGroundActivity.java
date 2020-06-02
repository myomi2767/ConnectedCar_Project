package connected.car.infotablet;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BackGroundActivity extends AppCompatActivity {
    String id;
    String carNum;
    InfoClient info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_ground);
        id = "backCar";
        carNum = "34가6773";
        info = new InfoClient(this,carNum);
    }
}
