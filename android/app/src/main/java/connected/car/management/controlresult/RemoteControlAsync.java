package connected.car.management.controlresult;

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
import connected.car.management.member.LoginActivity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RemoteControlAsync extends AsyncTask<ControlResultVO, Void, Integer> {
    Context context;

    public RemoteControlAsync(){

    }

    public RemoteControlAsync(Context context){
        this.context = context;
    }

    @Override
    protected Integer doInBackground(ControlResultVO... vo) {
        int result = 0;
        Log.d("myip",context.getString(R.string.myip)+"");
        try {
            String path = "http://"+context.getString(R.string.myip)+":8088/connectedcar/remote/insert.do";
            URL url = new URL(path);
            Gson gson = new Gson();
            String sVo = gson.toJson(vo[0]);
            //보내고 받는 작업
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

        }else if(value == 0) {
            Toast.makeText(context, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
