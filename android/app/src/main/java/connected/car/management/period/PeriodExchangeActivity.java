package connected.car.management.period;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import connected.car.management.R;

public class PeriodExchangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_exchange);

        //리사이클러뷰로 부품리스트 가져와서 보여줘야 합니다.
        //어댑터로 만들고, 각 항목에는 [선택] 버튼이 있으며 선택 시 맨 위에 setText되고
        //[선택]버튼을 누를 때마다 setText되어 최종값만 반영됩니다.
        //그리고 [확인] 버튼을 누르면 setText된 부품이 db에 update되고 액티비티가 꺼집니다.
    }
}
