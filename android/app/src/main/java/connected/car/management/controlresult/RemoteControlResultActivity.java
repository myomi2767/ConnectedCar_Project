package connected.car.management.controlresult;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import connected.car.management.R;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RemoteControlResultActivity extends AppCompatActivity {
    RecyclerView list;
    ControlResultVO controlResultVO;
    String carId;
    List<ControlResultVO> recycle_datalist;
    RemoteControlResultAdapter adapter;
    LinearLayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control_result);
        list = findViewById(R.id.controlResultList);

        Intent intent = getIntent();
        carId = intent.getStringExtra("carId");
        Log.d("msg","제어결과의 carID=>"+carId);

        recycle_datalist = new ArrayList<ControlResultVO>();

        //DB에 접근하여 제어결과값 가져오는 부분
        controlResultVO = new ControlResultVO(carId);
        HttpSelect task = new HttpSelect();
        task.execute(controlResultVO);

        manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter = new RemoteControlResultAdapter(this,
                R.layout.activity_remote_control_result_item,recycle_datalist);

        list.setLayoutManager(manager);
        list.setAdapter(adapter);

    }

    //Oracle DB 접근 위한 Async 메소드
    class HttpSelect extends AsyncTask<ControlResultVO, Void, String>{
        URL url = null;
        String result = "";
        JSONObject object = new JSONObject();
        @Override
        protected String doInBackground(ControlResultVO... items) {
            try {
                object.put("car_id", items[0].getCar_id());

                String path = "http://"+getApplicationContext().getString(R.string.myip)+":8088/connectedcar/remote/list.do";
                url = new URL(path);

                OkHttpClient client = new OkHttpClient();
                String json = object.toString();
                Log.d("result",json);
                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MediaType.parse("application/json"), json))
                        .build();

                Response response = client.newCall(request).execute();
                result = response.body().string();
                Log.d("result",result);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("result","Post쪽:"+s);
            JSONArray ja = null;
            try {
                ja = new JSONArray(s);
                for (int i=0;i<ja.length();i++){
                    JSONObject object = ja.getJSONObject(i);
                    String control_date = object.getString("control_date");
                    String control_code = object.getString("control_code");
                    String control_result = object.getString("control_result");

                    ControlResultVO item = new ControlResultVO(control_date,control_code,control_result);
                    recycle_datalist.add(item);
                }
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}


