package connected.car.management.car;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import connected.car.management.R;

public class UserCarActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Spinner brandSpinner;
    Spinner modelSpinner;
    Spinner detailModelSpinner;
    Spinner fuelSpinner;
    EditText editCarYear;
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
        setViews();
        setSpinner();
    }

    public void setViews() {
        brandSpinner = findViewById(R.id.spinner_brand);
        modelSpinner = findViewById(R.id.spinner_model);
        detailModelSpinner = findViewById(R.id.spinner_detail_model);
        fuelSpinner = findViewById(R.id.spinner_fuel);
        editCarYear = findViewById(R.id.car_year);
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

        ArrayAdapter adapterFuel = ArrayAdapter.createFromResource(this, R.array.fuel, android.R.layout.simple_spinner_dropdown_item);
        fuelSpinner.setAdapter(adapterFuel);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_submit:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int rId = view.getId();
        switch (rId) {
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
            case "싼타페":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_santafe, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "투싼":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_tucson, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "코나":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_kona, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "i30":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_i30, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "팰리세이드":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_palisade, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "구 제네시스, 현 G 시리즈":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_genesis, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "스타렉스":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_starex, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "포터":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_porter, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "벨로스터":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_veloster, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "엑센트":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_accent, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "맥스크루즈":
                adapterDetail = ArrayAdapter.createFromResource(this, R.array.hyundai_maxcruz, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "베뉴":
                //adapterDetail = ArrayAdapter.createFromResource(this, R.array.kia_ve, android.R.layout.simple_spinner_dropdown_item);
                break;
            case "베라크루즈":
                break;
            case "아이오닉":
                break;
            case "i40":
                break;
            case "에쿠스":
                break;
            case "트라제XG":
                break;
            case "넥쏘":
                break;
            case "베르나":
                break;
            case "아슬란":
                break;
            case "마이티":
                break;
            case "투스카니":
                break;
            case "테라칸":
                break;
            case "메가트럭":
                break;
            case "트라고 엑시언트":
                break;
            case "클릭":
                break;
            case "카운티":
                break;
            case "K5":
                break;
            case "모닝":
                break;
            case "쏘렌토":
                break;
            case "스포티지":
                break;
            case "K3":
                break;
            case "K7":
                break;
            case "K9":
                break;
            case "카니발":
                break;
            case "니로":
                break;
            case "레이":
                break;
            case "셀토스":
                break;
            case "프라이드":
                break;
            case "포르테":
                break;
            case "모하비":
                break;
            case "스팅어":
                break;
            case "카렌스":
                break;
            case "스토닉":
                break;
            case "쏘울":
                break;
            case "봉고":
                break;
            case "쎄라토":
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
