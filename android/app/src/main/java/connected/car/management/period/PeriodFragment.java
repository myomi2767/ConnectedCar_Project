package connected.car.management.period;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import connected.car.management.HttpHandler.StringURLHttpHandler;
import connected.car.management.R;
import connected.car.management.application.MyApplication;
import connected.car.management.control.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeriodFragment extends Fragment {

    PeriodAdapter myadapter;
    LinearLayoutManager manager;
    RecyclerView list;
    List<MyexpendVO> periodlist;
    TextView myCarName;
    FragmentManager fragmentManager;

    TextView notice;

    public String car_id;
    public String car_model_name;
    public String car_fuel_type;
    public int drive_distance;

    getPeriodHttpTask task;


    public PeriodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        //Log.d("===", "period프래그먼트 생기기직전: ");
        View view = inflater.inflate(R.layout.fragment_period, container, false);
        list = view.findViewById(R.id.rlist);
        myCarName = view.findViewById(R.id.text_myCarModelName);
        car_id = MyApplication.CarInfo.getCar_id(); //액티비티로부터 가져온 로그인된 car_id
        car_model_name = MyApplication.CarInfo.getCar_model_name();
        drive_distance = MyApplication.CarInfo.getDrive_distance();

        notice = view.findViewById(R.id.text_notice);


        Log.d("===", "period프래그먼트: "+car_model_name);
        Log.d("===","period프래그먼트의 주행거리정보:===>>"+drive_distance);

        myCarName.setText(car_model_name+"▶"+car_id);

        periodlist = new ArrayList<MyexpendVO>();

        myadapter = new PeriodAdapter(getActivity().getApplicationContext(),
                R.layout.period_row, periodlist, drive_distance, car_model_name);

        manager = new LinearLayoutManager(getActivity().getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        list.setLayoutManager(manager);
        list.setAdapter(myadapter);


        if(car_model_name.equals("") || car_model_name==null){
            notice.setText("아직 등록된 차량이 없습니다! \n 화면 우측 상단 차량 추가 버튼을 통해 차량을 등록해주세요.");
            return view;
        }else {
            task = new getPeriodHttpTask(car_id);
            task.execute();
            return view;

        }


    }




    //리싸이클러뷰
    class getPeriodHttpTask extends AsyncTask<Void, Void, String> {

        String url;
        ProgressDialog showPeriod = new ProgressDialog(getContext());


        public getPeriodHttpTask(String car_id) {
            url = "http://" + getString(R.string.myip) + ":8088/connectedcar/period/getPeriodlist.do?";
            url += "car_id=" + car_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showPeriod.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            showPeriod.setMessage("Now Loading");
            showPeriod.show();


        }

        @Override
        protected String doInBackground(Void... voids) {
            return StringURLHttpHandler.requestData(url);
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                Log.d("===", "포스트익스큐트스트링periodlist:" + s);
                if (s.length() > 10) { //데이터가 제대로왔는지 확인하려고.
                    //웹서버에서 가져온 데이터가 json형식이므로
                    //파싱해서 JSONObject를 MenuVO로 변환
                    //변환한 AlertHistoryDTO를 ArrayList에 저장

                    JSONArray ja = null;
                    try {
                        ja = new JSONArray(s);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            String my_expend_no = jo.getString("my_expend_no");
                            String car_id = jo.getString("car_id");
                            String expend_kind = jo.getString("expend_kind");
                            String expend_type = jo.getString("expend_type");
                            String expend_term = jo.getString("expend_term");
                            String expend_id = jo.getString("expend_id");
                            String my_expend_replace = jo.getString("my_expend_replace");
                            String my_expend_km = jo.getString("my_expend_km");


                            MyexpendVO perioditem = new MyexpendVO(my_expend_no, car_id, expend_kind, expend_term,
                                    expend_type, expend_id, my_expend_replace, my_expend_km);
                            periodlist.add(perioditem);
                        }

                        myadapter.notifyDataSetChanged();
                        //Log.d("===","어댑터 붙임");
                        showPeriod.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }


        }

    }

    }


