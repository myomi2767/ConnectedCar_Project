package connected.car.management.member;

import android.app.Activity;
import android.app.ProgressDialog;
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
import connected.car.management.control.MainActivity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginAsync extends AsyncTask<MemberVO, Void, MemberVO> {
    Context context;
    ProgressDialog loadLogin;

    public LoginAsync(Context context) {
        this.context = context;
        loadLogin = new ProgressDialog(this.context);
    }

    @Override
    protected void onPreExecute() {
        loadLogin.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadLogin.setMessage("로그인 중입니다");
        loadLogin.show();
        super.onPreExecute();
    }

    @Override
    protected MemberVO doInBackground(MemberVO... memberVos) {
        MemberVO resultVo = null;
        Log.d("myip",context.getString(R.string.myip)+"");
        try {
            URL url = new URL("http://"+ context.getString(R.string.myip)+":8088/connectedcar/member/login.do");
            JSONObject obj = new JSONObject();
            obj.put("user_id", memberVos[0].user_id);
            obj.put("user_password", memberVos[0].user_password);

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("application/json"), obj.toString()))
                    .build();

            Response response = client.newCall(request).execute();
            Gson gson = new Gson();
            resultVo = gson.fromJson(response.body().string(), MemberVO.class);

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
    protected void onPostExecute(MemberVO vo) {
        super.onPostExecute(vo);
        loadLogin.dismiss();
        if(vo != null) {
            //로그인 성공
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("userInfo", vo);
            context.startActivity(intent);
            ((Activity)context).finish();
        } else {
            //로그인 실패
            Toast.makeText(context, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
