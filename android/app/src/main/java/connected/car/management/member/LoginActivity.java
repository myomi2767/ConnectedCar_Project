package connected.car.management.member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import connected.car.management.R;
import connected.car.management.control.MainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextId;
    EditText editTextPass;
    Button btnSignup;
    Button btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setViews();

    }

    public void setViews() {
        editTextId = findViewById(R.id.edit_id);
        editTextPass = findViewById(R.id.edit_password);

        btnSignup = findViewById(R.id.btn_to_join);
        btnSignin = findViewById(R.id.btn_login);

        btnSignup.setOnClickListener(this);
        btnSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        switch (id) {
            case R.id.btn_to_join:
                intent = new Intent(this, JoinActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_login:
                MemberVO memberVO = new MemberVO(editTextId.getText().toString(),
                        editTextPass.getText().toString());
                new LoginAsync(this).execute(memberVO);
                break;
        }
    }
}
