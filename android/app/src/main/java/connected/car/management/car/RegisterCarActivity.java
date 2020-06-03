package connected.car.management.car;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import connected.car.management.R;
import connected.car.management.member.MemberVO;

public class RegisterCarActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    TextView textCarNum;
    Spinner brandSpinner;
    Spinner modelSpinner;
    Spinner detailModelSpinner;
    Spinner fuelSpinner;
    Spinner carYearSpinner;
    EditText editCarCC;
    EditText editCarKm;
    RadioGroup radioCar;
    Button btnSubmit;

    ArrayAdapter adapterH;
    ArrayAdapter adapterK;

    ArrayAdapter adapterDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercar);
        //DB에서 불러온 차량번호 입력하는 부분
        textCarNum = findViewById(R.id.text_carNum);
        Intent intent = getIntent();
        MemberVO vo = intent.getParcelableExtra("userInfo");
        textCarNum.setText(vo.getCar_id());

        setViews();
        setSpinner();

    }

    public void setViews() {
        textCarNum = findViewById(R.id.text_carNum);

        brandSpinner = findViewById(R.id.spinner_brand);
        modelSpinner = findViewById(R.id.spinner_model);
        detailModelSpinner = findViewById(R.id.spinner_detail_model);
        fuelSpinner = findViewById(R.id.spinner_fuel);
        carYearSpinner = findViewById(R.id.spinner_year);
        editCarCC = findViewById(R.id.car_cc);
        editCarKm = findViewById(R.id.car_km);
        radioCar = findViewById(R.id.car_radio);
        btnSubmit = findViewById(R.id.btn_submit);

        brandSpinner.setOnItemSelectedListener(this);
        modelSpinner.setOnItemSelectedListener(this);
        btnSubmit.setOnClickListener(this);
    }

    public void setSpinner() {
        ArrayAdapter adapterBrand = ArrayAdapter.createFromResource(this, R.array.brand, android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setAdapter(adapterBrand);

        adapterH = ArrayAdapter.createFromResource(this, R.array.hyundai_model, android.R.layout.simple_spinner_dropdown_item);
        adapterK = ArrayAdapter.createFromResource(this, R.array.kia_model, android.R.layout.simple_spinner_dropdown_item);

        adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_avante, android.R.layout.simple_spinner_dropdown_item);
        detailModelSpinner.setAdapter(adapterDetail);

        ArrayAdapter adapterFuel = ArrayAdapter.createFromResource(this, R.array.fuel, android.R.layout.simple_spinner_dropdown_item);
        fuelSpinner.setAdapter(adapterFuel);

        ArrayAdapter adapterYear = ArrayAdapter.createFromResource(this, R.array.car_year, android.R.layout.simple_spinner_dropdown_item);
        carYearSpinner.setAdapter(adapterYear);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_submit:
                Log.d("test",((RadioButton)findViewById(radioCar.getCheckedRadioButtonId())).getText().toString());
                CarVO vo = new CarVO(textCarNum.getText().toString(),
                        brandSpinner.getSelectedItem().toString(),
                        modelSpinner.getSelectedItem().toString(),
                        detailModelSpinner.getSelectedItem().toString(),
                        fuelSpinner.getSelectedItem().toString(),
                        carYearSpinner.getSelectedItem().toString(),
                        editCarCC.getText().toString(),
                        0,0,0,
                        ((RadioButton)findViewById(radioCar.getCheckedRadioButtonId())).getText().toString(),
                        Integer.parseInt(editCarKm.getText().toString()));
                Log.d("test","어싱크돌려질 회원가입정보:"+vo);
                new CarAsync(this).execute(vo);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int sId = parent.getId();
        switch (sId) {
            case R.id.spinner_brand:
                if (parent.getAdapter().getItem(position).equals("현대")) {
                    modelSpinner.setAdapter(adapterH);
                } else {
                    modelSpinner.setAdapter(adapterK);
                }
                break;
            case R.id.spinner_model:
                setDetailSpinner(parent.getAdapter().getItem(position).toString());
                break;
        }
    }

    public void setDetailSpinner(String name) {
        switch (name) {
            case "아반떼":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_avante, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "쏘나타":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_sonata, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "그랜저":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_grandeur, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "i30":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_i30, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "i40":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_i40, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "구 제네시스, 현 G 시리즈":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_genesis, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "에쿠스":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_equus, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "아이오닉":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_ioniq, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "엑센트":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_accent, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "코나":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_kona, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "포터":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_porter, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "클릭":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_click, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "벨로스터":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_veloster, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "베르나":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_verna, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "넥쏘":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_nexo, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "아슬란":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_aslan, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "투싼":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_tucson, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "블루온":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_blueon, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "아토스":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_atos, android.R.layout.simple_spinner_dropdown_item);
                break;

            case "K3":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_k3, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "K5":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_k5, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "K7":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_k7, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "K9":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_k9, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "모닝":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_morning, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "세라토":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_serato, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "포르테":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_forte, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "스팅어":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_stinger, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "프라이드":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_pride, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "쏘울":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_soul, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "레이":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_ray, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "오피러스":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_opirus, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "모하비":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_mohave, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "니로":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_niro, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "로체":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_lotze, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "비스토":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_visto, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "스펙트라":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_spectra, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "쏘렌토":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_sorento, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "엔터프라이즈":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_enterprise, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "세피아":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_sepia, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "리갈/옵티마":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_optima_rigal, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "크레도스":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_credos, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "포텐샤":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_potentia, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "리오":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_rio, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "봉고":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_bongo, android.R.layout.simple_spinner_dropdown_item);
                break;
        }

        detailModelSpinner.setAdapter(adapterDetail);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
