package connected.car.management.control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

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
import connected.car.management.map.LocationVO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RemoteControlAsync extends AsyncTask<String, String, Void> {
    Context context;
    Socket socket;
    //수신받을 Stream 객체들
    InputStream is;
    InputStreamReader isr;
    BufferedReader br;
    //송신할 Stream 객체들
    OutputStream os;
    PrintWriter pw;

    StringTokenizer st;

    //자동차ID
    String carId;

    //알림
    Notification notification;

    boolean isOnPrintWriter;
    boolean isMessageIn;

    //제어결과 받을 멤버변수
    boolean engineStatus;
    boolean doorStatus;
    boolean airconditionStatus;
    boolean emergencyStatus;
    boolean[] statusResult = new boolean[4];

    //차량위치 받은 변수
    LocationVO locationVO;

    public RemoteControlAsync(){

    }

    public RemoteControlAsync(Context context){
        this.context = context;
        notification = new Notification(context);
    }

    @Override
    protected Void doInBackground(String... id) {
        try {
            carId = id[0];
            socket = new Socket(context.getString(R.string.myip), 12345);

            if(socket != null){
                ioWork();
            }
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        String msg;
                        try {
                            msg = br.readLine(); //read만. 제어 결과 받는다.
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
    private void filteringMsg(String msg){ //서버에서 받은 메세지를 갖고 온다.
        st = new StringTokenizer(msg, ":");
        String protocol = st.nextToken(); //가장 먼저 들어오는게 protocol,
        int result=0;
        if(protocol.equals("job")) {
            String message = st.nextToken();
            String category = st.nextToken();
            String id = st.nextToken();
            String control_code="";
            String control_result="";
            /* ==============================제어결과에 대한 DB접근================================= */
            if(message.charAt(message.length()-1)=='P'|message.charAt(message.length()-1)=='F'){
                //성공 or 실패 구분
                if(message.charAt(message.length()-1)=='P'){
                    Log.d("remote", "제어성공");
                    control_result = "성공";
                }else{
                    Log.d("remote", "제어실패");
                    control_result = "실패";
                }
                //제어 구분자 파악
                if(message.startsWith("EO")){
                    control_code = "비상등";
                }else if(message.startsWith("EAS")){
                    control_code = "비상등+경적";
                }else if(message.startsWith("DO")){
                    control_code = "문열림";
                }else if(message.startsWith("DL")){
                    control_code = "문닫힘";
                }else if(message.startsWith("ES")){
                    control_code = "엔진켜기";
                }else if(message.startsWith("ET")){
                    control_code = "엔진끄기";
                }
                onProgressUpdate(control_result);
                ControlResultVO vo = new ControlResultVO(carId, null, control_code, control_result);
                try {
                    String path = "http://"+context.getString(R.string.myip)+":8088/connectedcar/remote/insert.do";
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
                    Log.d("test",result+"개 삽입");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            /* ==============================현재 제어 상태에 대한 결과================================= */
            }else{
                Log.d("test","status 진입");
                Log.d("test",message);
                setStatus(message);
                isMessageIn = true;
            }
        }else if(protocol.equals("location")){
            String message = st.nextToken();
            String category = st.nextToken();
            String id = st.nextToken();
            String latitude = st.nextToken();
            String longitude = st.nextToken();
            locationVO = new LocationVO(Double.parseDouble(latitude), Double.parseDouble(longitude));
            Log.d("locationtest",locationVO+"");
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

            isOnPrintWriter = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isOnPW() {
        return isOnPrintWriter;
    }
    public boolean isMsgIn() {
        return isMessageIn;
    }
    public void setMessageIn(boolean bIsMsg) {
        isMessageIn = bIsMsg;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Intent intent = new Intent(((Activity)context), MainActivity.class);
        notification
                .setData(intent)
                .setTitle("제어결과 알림")
                .setText(values[0])
                .notification();
    }
    //pw값 메소드 호출하는 부분
    public PrintWriter getPrintWriter(){
        return pw;
    }

    //제어상태를 set하는 메소드
    public void setStatus(String result){
        char[] status = result.toCharArray();

        //엔진상태
        if(status[0]=='0'){
            engineStatus = false;
        }else if (status[0]=='1'){
            engineStatus = true;
        }
        statusResult[0] = engineStatus;
        //도어상태
        if(status[1]=='0'){
            doorStatus = false;
        }else if (status[1]=='1'){
            doorStatus = true;
        }
        statusResult[1] = doorStatus;
        //공조상태
        if(status[2]=='0'){
            airconditionStatus = false;
        }else if (status[2]=='1'){
            airconditionStatus = true;
        }
        statusResult[2] = airconditionStatus;
        //비상등상태
        if(status[0]=='0'){
            emergencyStatus = false;
        }else if (status[0]=='1'){
            emergencyStatus = true;
        }
        statusResult[3] = emergencyStatus;
    }

    //제어결과 호출하는 메소드
    public boolean[] getStatusResult(){
        return statusResult;
    }
    //차량위치정보 호출하는 메소드
    public LocationVO getLocationVO(){
        return locationVO;
    }

}
