package multi.connect.smartcar.fcm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;

import multi.connect.smartcar.R;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class FCMActivity extends AppCompatActivity {
    String car_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm);
        getToken(car_id);
    }
/*    public void request(View view){
        new rquestThread(car_id,type).start();
    }*/
    //토큰을 생성하고 만드는 작업
    public void getToken(final String car_id){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        //토큰을 가져오다 실패하면 실행하게 된다.
                        if (!task.isSuccessful()) {
                            Log.d("myfcm", "getInstanceId failed", task.getException());
                            return;
                        }
                        String token = task.getResult().getToken();

                        Log.d("myfcm2", token);
                        new SendTokenThread(token,car_id).start();
                    }
                });
    }

    class SendTokenThread extends Thread{
        String token;
        String car_id;
        public SendTokenThread(String token,String car_id) {
            Log.d("myfcm2", car_id+"::"+token+"::");
            this.token = token;
            this.car_id = car_id;
        }


        @Override
        public void run() {
                super.run();
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request.Builder builder = new Request.Builder();
                    builder = builder.url("http://192.168.43.229:8088/connectedcar/fcm/fcm_check.do?token="+token +"&car_id=" +car_id);
                    Request request = builder.build();
                    Log.d("myfcm2",builder+">>"+car_id+">>"+token);
                    Call newcall = client.newCall(request);
                    newcall.execute();
                    Log.d("myfcm2", newcall+">>들어오나?");
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    public static class rquestThread extends Thread{
            String type;
            String car_id;
            public rquestThread(String car_id,String type) {
                this.car_id = car_id;
                this.type = type;
            }

        @Override
        public void run() {
            super.run();
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder = builder.url("http://192.168.43.229:8088/connectedcar/fcm/sendClient?car_id="+car_id+"&type="+type);
                Request request = builder.build();
                Call newcall = client.newCall(request);
                newcall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static class rquestGPSThread extends Thread{
        String gps;
        String car_id;
        public rquestGPSThread(String car_id,String gps) {
            this.car_id = car_id;
            this.gps = gps;
        }

        @Override
        public void run() {
            super.run();
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder = builder.url("http://192.168.43.229:8088/connectedcar/fcm/sendGps?car_id="+car_id+"&gps="+gps);
                Request request = builder.build();
                Call newcall = client.newCall(request);
                newcall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
