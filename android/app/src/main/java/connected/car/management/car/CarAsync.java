package connected.car.management.car;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import connected.car.management.R;
import connected.car.management.application.MyApplication;
import connected.car.management.control.MainActivity;
import connected.car.management.member.LoginActivity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CarAsync extends AsyncTask<CarVO, Void, Integer> {
    Context context;

    public CarAsync(){

    }

    public CarAsync(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(CarVO... vo) {
        int result = 0;
        Log.d("myip",context.getString(R.string.myip)+"");
        try {
            URL url = new URL("http://"+context.getString(R.string.myip)+":8088/connectedcar/mycar/insert.do");
            Gson gson = new Gson();
            CarVO car = vo[0];
            String sVo = gson.toJson(car);
            Log.d("test",car.toString());
            MyApplication.setCarInfo(car);

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("application/json"), sVo))
                    .build();

            Response response = client.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            Log.d("test", json.toString());
            result = json.getInt("resultNum");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(Integer value) {
        super.onPostExecute(value);
        if(value == 1) {
            ((Activity)context).finish();
        }
        else if(value == 0)
            Toast.makeText(context, "차량 추가에 실패했습니다.", Toast.LENGTH_SHORT).show();
    }
}
