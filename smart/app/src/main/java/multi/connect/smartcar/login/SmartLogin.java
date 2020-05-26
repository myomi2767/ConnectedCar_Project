package multi.connect.smartcar.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import multi.connect.smartcar.MainActivity;
import multi.connect.smartcar.R;

public class SmartLogin extends AppCompatActivity {
    EditText carId;
    EditText carPass;
    Button login;
    CheckBox autoCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_login);
        carId = findViewById(R.id.carId);
        carPass = findViewById(R.id.carPass);
        login = findViewById(R.id.login);
        autoCheck = findViewById(R.id.autoCheck);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SmartLogin.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
