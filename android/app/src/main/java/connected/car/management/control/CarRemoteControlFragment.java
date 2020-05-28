package connected.car.management.control;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import connected.car.management.R;


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
    class AsyncTaskPower extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                socket = new Socket("70.12.116.67", 12345);
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
            if(protocol.equals("job")) {
                String message = st.nextToken();
                String category = st.nextToken();
                String id = st.nextToken();
                if(message.equals("success")){
                    Log.d("remote", "제어성공");
                }else if(message.equals("fail")){
                    Log.d("remote", "제어실패");
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
