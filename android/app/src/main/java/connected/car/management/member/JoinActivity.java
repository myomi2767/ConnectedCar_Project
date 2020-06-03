package connected.car.management.member;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Pattern;

import connected.car.management.R;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnFocusChangeListener {
    EditText editTextId;
    EditText editTextCarId;
    EditText editTextPassword;
    EditText editTextName;
    EditText editTextBirth;
    Spinner spinner;
    EditText editTextLicense;

    Button btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        setViews();

        setSpinner();
    }

    public void setViews() {
        editTextId = findViewById(R.id.edit_id);
        editTextCarId = findViewById(R.id.edit_carNum);
        editTextPassword = findViewById(R.id.edit_password);
        editTextName = findViewById(R.id.edit_name);
        editTextBirth = findViewById(R.id.edit_birth);
        editTextLicense = findViewById(R.id.edit_license);

        editTextId.setOnFocusChangeListener(this);
        editTextCarId.setOnFocusChangeListener(this);
        editTextPassword.setOnFocusChangeListener(this);
        editTextName.setOnFocusChangeListener(this);
        editTextBirth.setOnFocusChangeListener(this);
        editTextLicense.setOnFocusChangeListener(this);

        /*editTextId.setOnEditorActionListener(this);
        editTextCarId.setOnEditorActionListener(this);
        editTextPassword.setOnEditorActionListener(this);
        editTextName.setOnEditorActionListener(this);
        editTextBirth.setOnEditorActionListener(this);
        editTextLicense.setOnEditorActionListener(this);*/

        spinner = findViewById(R.id.spinner_gender);

        btnJoin = findViewById(R.id.btn_join);
        btnJoin.setOnClickListener(this);
    }

    public void setSpinner() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_join) {
            if(checkRule()) {
                MemberVO vo = new MemberVO(
                        editTextId.getText().toString(),
                        editTextCarId.getText().toString(),
                        editTextPassword.getText().toString(),
                        editTextName.getText().toString(),
                        editTextBirth.getText().toString(),
                        spinner.getSelectedItem().toString(),
                        editTextLicense.getText().toString()
                );
                new JoinAsync(this).execute(vo);
                Toast.makeText(this, "차량 등록이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public boolean checkRule() {
        if(editTextId.getText().toString().equals("") |
                editTextCarId.getText().toString().equals("") |
                editTextPassword.getText().toString().equals("") |
                editTextName.getText().toString().equals("") |
                editTextBirth.getText().toString().equals("") |
                editTextLicense.getText().toString().equals("")) {
            Toast.makeText(this, "입력하지 않은 정보가 있습니다!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            manager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
            switch (v.getId()) {
                case R.id.edit_id:
                    if(!Pattern.matches("^[a-zA-Z0-9]*$", editTextId.getText().toString())) {
                        Toast.makeText(this, "아이디는 영문자와 숫자로만 이루어져야 합니다.",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.edit_carNum:
                    break;
                case R.id.edit_password:
                    if(!Pattern.matches("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{8,15}$",
                            editTextPassword.getText().toString())) {
                        Toast.makeText(this, "숫자, 문자, 특수문자 중 2가지 포함(8~15자)",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.edit_name:
                    break;
                case R.id.edit_birth:
                    if(!Pattern.matches("([0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[1,2][0-9]|3[0,1]))",
                            editTextBirth.getText().toString())) {
                        Toast.makeText(this, "6자리로 입력. ex) 900101", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.edit_license:
                    break;
            }
        }

    }

}
