package connected.car.management.period;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import connected.car.management.HttpHandler.StringURLHttpHandler;
import connected.car.management.R;

public class PeriodExchangeActivity extends AppCompatActivity {

    String ex_kind="";
    String ex_type="";
    String ex_term="";
    int my_expend_km=0;
    String car_id="";
    String my_expend_no="";
    String car_model_name="";

    RecyclerView list;
    List<ExpendableVO> expendlist;
    TextView selectedExpend;
    ExchangeAdapter myadapter;
    LinearLayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_exchange);
        Intent intent = getIntent();
        ex_kind = intent.getStringExtra("expend_kind");
        ex_type = intent.getStringExtra("expend_type");
        ex_term = intent.getStringExtra("expend_term");
        my_expend_km = intent.getIntExtra("drive_distance", 1); //값이 없으면 1로 들어옵니다.
        car_id= intent.getStringExtra("car_id");
        my_expend_no = intent.getStringExtra("my_expend_no");
        car_model_name = intent.getStringExtra("car_model_name");
        //리사이클러뷰로 부품리스트 가져와서 보여줘야 합니다.
        //어댑터로 만들고, 각 항목에는 [선택] 버튼이 있으며 선택 시 맨 위에 setText되고
        //[선택]버튼을 누를 때마다 setText되어 최종값만 반영됩니다.
        //그리고 [확인] 버튼을 누르면 setText된 부품이 db에 update되고 액티비티가 꺼집니다.

        //리사이클러뷰에 보여줄 select문 : select * from expendable where expend_type=#{expend_type} and car_model_name=#{car_model_name}
        //냉각수, 디프렌셜오일, 브레이크액, 트랜스퍼오일, 밸브간극부품, 에어컨냉매, 에어컨필터, 연료계부품, 연료필터, 연료필터카트리지,
        //예열플러그, 타이밍벨트, 타이밍시스템, 타이어위치, 휠얼라인먼트 car_model_name이 들어가지 않도록 컨트롤러에서 설계(무조건 다나오게)
        //DB에 업데이트 해줄 update문 : update my_expendable set my_expend_replace=sysdate, my_expend_km=#{my_expend_km},
        //expend_id = #{expend_id} where my_expend_no = #{my_expend_no}

        //이제 어댑터를 만듭시다.

        expendlist = new ArrayList<ExpendableVO>();

        myadapter = new ExchangeAdapter(this,
                R.layout.period_exchange_row, expendlist);

        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        list.setLayoutManager(manager);
        list.setAdapter(myadapter);
        getPeriodHttpTask task = new getPeriodHttpTask(ex_type, car_model_name);
        task.execute();

        Log.d("===", "expendlist"+expendlist);

    }


    //리싸이클러뷰
    class getPeriodHttpTask extends AsyncTask<Void, Void, String> {

        String url;
        /*ProgressDialog showPeriod = new ProgressDialog(getApplicationContext());*/


        public getPeriodHttpTask(String ex_type, String car_model_name) {
            Log.d("===", "task로 들어갈 ex_type, car_model_name"+ex_type+car_model_name);
            url = "http://" + getString(R.string.myip) + ":8088/connectedcar/period/getExpendlist.do?";
            url += "expend_type=" + ex_type;
            url += "&car_model_name="+car_model_name;
        }



        @Override
        protected String doInBackground(Void... voids) {
            return StringURLHttpHandler.requestData(url);
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                Log.d("===", "post에서 부품리스트:" + s);
                if (s.length() > 3) { //데이터가 제대로왔는지 확인하려고.
                    //웹서버에서 가져온 데이터가 json형식이므로
                    //파싱해서 JSONObject를 MenuVO로 변환
                    //변환한 AlertHistoryDTO를 ArrayList에 저장

                    JSONArray ja = null;
                    try {
                        ja = new JSONArray(s);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            String expend_id = jo.getString("my_expend_no");
                            String expend_code = jo.getString("expend_code");
                            String expend_type = jo.getString("expend_type");
                            String expend_name = jo.getString("expend_name");
                            String expend_price = jo.getString("expend_price");
                            String expend_brand = jo.getString("expend_brand");
                            String car_name = jo.getString("car_model_name");



                            ExpendableVO expenditem = new ExpendableVO(expend_id, expend_code, expend_type,
                                    expend_name, expend_price, expend_brand, car_name);
                            expendlist.add(expenditem);

                        }

                        myadapter.notifyDataSetChanged();
                        //Log.d("===","어댑터 붙임");
                        //showPeriod.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }


        }

    }
}
