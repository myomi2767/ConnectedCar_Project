package connected.car.management.control;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.StringTokenizer;

import connected.car.management.R;
import connected.car.management.controlresult.ControlResultVO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarRemoteControlFragment extends Fragment {

    ImageButton powerOn;
    ImageButton airControl;
    ImageButton engineOff;

    Button btnEmerOn;
    Button btnEmerOff;

    AsyncTaskPower asyncTaskPower;
    Socket socket;

    InputStream is;
    InputStreamReader isr;
    BufferedReader br;

    OutputStream os;
    PrintWriter pw;
    //String id;
    String carId;

    StringTokenizer st;

    Notification notification;
    public CarRemoteControlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car_remote_control, container, false);
        carId= "11111";

        powerOn = view.findViewById(R.id.powerOn);
        engineOff = view.findViewById(R.id.engineOff);
        airControl = view.findViewById(R.id.airControl);
        btnEmerOn = view.findViewById(R.id.btnEmerLightOn);
        btnEmerOff = view.findViewById(R.id.btnEmerLightOnSiren);

        powerOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_msg(v);
            }
        });
        engineOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_msg(v);
            }
        });
        btnEmerOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_msg(v);
            }
        });
        btnEmerOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_msg(v);
            }
        });

        notification = new Notification(getActivity());

        asyncTaskPower = new AsyncTaskPower();
        asyncTaskPower.execute();
        return view;
    }
    public void send_msg(final View view){
        new Thread(new Runnable() {
            String message = "";
            @Override
            public void run() {
                if(view.getId()==R.id.powerOn){
                    message = "engineStart";
                }else if(view.getId()==R.id.engineOff){
                    message = "engineStop";
                }else if(view.getId()==R.id.btnEmerLightOn){
                    message = "emergencyOn";
                }else if(view.getId()==R.id.btnEmerLightOnSiren){
                    message = "emergencyAndSiren";
                }
                pw.println("job:"+message+":phone:"+carId);
                pw.flush();
            }
        }).start();
    }
    class AsyncTaskPower extends AsyncTask<Void, String, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                socket = new Socket("192.168.200.180", 12345);

                if(socket != null){
                    ioWork();
                }
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true) {
                            String msg;
                            try {
                                msg = br.readLine();
                                Log.d("remote", "서버로부터 수신된 메시지>>" + msg);
                                filteringMsg(msg);
                            } catch (IOException e) {
                                try {
                                    is.close();
                                    isr.close();
                                    br.close();
                                    os.close();
                                    socket.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                break;
                            }
                        }
                    }
                });
                t1.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        private void filteringMsg(String msg){
            st = new StringTokenizer(msg, ":");
            String protocol = st.nextToken();
            int result=0;
            if(protocol.equals("job")) {
                String message = st.nextToken();
                String category = st.nextToken();
                String id = st.nextToken();
                String control_result="";
                if(message.equals("success")){
                    Log.d("remote", "제어성공");
                    control_result = "성공";
                }else if(message.equals("fail")){
                    Log.d("remote", "제어실패");
                    control_result = "실패";
                }
                onProgressUpdate(control_result);
                ControlResultVO vo = new ControlResultVO(carId, null, "제어구분자", control_result, null);
                try {
                    String path = "http://"+getContext().getString(R.string.myip)+":8088/connectedcar/remote/insert.do";
                    URL url = new URL(path);
                    Gson gson = new Gson();
                    String sVo = gson.toJson(vo);
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
                    Log.d("msg",result+"개 삽입");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        void ioWork(){
            //최초접속할 때 서버에게 접속한 아이디에 정보를 보내기
            try {
                is = socket.getInputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);

                os = socket.getOutputStream();
                pw = new PrintWriter(os, true);
                //DB에 있는 id와 차량번호를 넘긴다.
                pw.println("phone:"+carId);
                pw.flush();



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            notification
                    .setData(intent)
                    .setTitle("제어결과 알림")
                    .setText(values[0])
                    .notification();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if(socket!=null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
