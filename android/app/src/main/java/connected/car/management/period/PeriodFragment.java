package connected.car.management.period;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import connected.car.management.HttpHandler.StringURLHttpHandler;
import connected.car.management.R;
import connected.car.management.control.MainActivity;
import connected.car.management.member.LoginAsync;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeriodFragment extends Fragment  {
    PeriodAdapter myadapter;
    LinearLayoutManager manager;
    RecyclerView list;
    List<MyexpendVO> periodlist = new ArrayList<MyexpendVO>();
    TextView myCarName;
    FragmentManager fragmentManager;

    public String car_id;
    public String car_model_name;
    public String car_fuel_type;
    public int drive_distance;


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
        car_id = ((MainActivity)getActivity()).main_car_id; //액티비티로부터 가져온 로그인된 car_id
        car_model_name = ((MainActivity)getActivity()).main_car_model_name;
        drive_distance = Integer.parseInt(((MainActivity)getActivity()).main_driver_distatnce);

        Log.d("===", "period프래그먼트: "+car_model_name);

        myCarName.setText(car_model_name+"▶"+car_id);

        getPeriodHttpTask task = new getPeriodHttpTask(car_id);
        task.execute();




        return view;
    }

    public void distanceData(int distance) {
       distance = drive_distance;
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
                        myadapter = new PeriodAdapter(getActivity().getApplicationContext(),
                                R.layout.period_row, periodlist);




                        manager = new LinearLayoutManager(getActivity().getApplicationContext());
                        manager.setOrientation(LinearLayoutManager.VERTICAL);


                        list.setLayoutManager(manager);
                        list.setAdapter(myadapter);
                        myadapter.notifyDataSetChanged();
                        Log.d("===","어댑터 붙임");
                        showPeriod.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }


        }

    }

    }


