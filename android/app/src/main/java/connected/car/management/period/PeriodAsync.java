package connected.car.management.period;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import connected.car.management.R;
import connected.car.management.control.MainActivity;
import connected.car.management.member.MemberVO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PeriodAsync extends AsyncTask<TermVO, Void, TermVO> {
    PeriodFragment context;

    public PeriodAsync(PeriodFragment context) {
        this.context = context;
    }

    @Override
    protected TermVO doInBackground(TermVO... termVos) {
        TermVO resultVo = null;
        Log.d("myip",context.getString(R.string.myip)+"");
        try {
            URL url = new URL("http://"+ context.getString(R.string.myip)+":8088/connectedcar/term/select.do");
            JSONObject obj = new JSONObject();
            obj.put("car_brand", termVos[0].car_brand);
            obj.put("car_fuel_type", termVos[0].car_fuel_type);

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("application/json"), obj.toString()))
                    .build();

            Response response = client.newCall(request).execute();
            Gson gson = new Gson();
            resultVo = gson.fromJson(response.body().string(), TermVO.class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultVo;
    }

    @Override
    protected void onPostExecute(TermVO vo) {
        super.onPostExecute(vo);
        if(vo != null) {
            //웹서버에서 가져온 데이터가 json형식이므로
            //파싱해서 JSONObject를 MenuVO로 변환
            //변환한 AlertHistoryDTO를 ArrayList에 저장


        } else {
            //로그인 실패

        }
    }
}
