package multi.connect.smartcar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class BackGroundActivity extends AppCompatActivity {
    String id;
    String carNum;
    InfoClient info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_ground);
        id = "backCar";
        carNum = "82가1004";
        info = new InfoClient(this,carNum);


    }
}
