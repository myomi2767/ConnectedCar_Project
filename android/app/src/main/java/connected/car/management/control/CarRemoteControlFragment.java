package connected.car.management.control;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
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
import connected.car.management.sqlite.AirSettingVO;
import connected.car.management.sqlite.DBHandler;
import connected.car.management.sqlite.DBHelper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarRemoteControlFragment extends Fragment implements View.OnClickListener {

    ImageButton powerOn;
    ImageButton airControl;
    ImageButton engineOff;

    Button btnEmerOn;
    Button btnEmerOff;
    Button btnDoorOpen;
    Button btnDoorLock;

    Socket socket;

    PrintWriter pw;

    String carId;
    Notification notification;

    RemoteControlAsync remoteControlAsync;
    //query문 만들기 위한 부분
    DBHandler handler;

    public CarRemoteControlFragment(String car_id) {
        // Required empty public constructor
        this.carId = car_id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car_remote_control, container, false);
        //DBHandler
        handler = new DBHandler(getContext());

        powerOn = view.findViewById(R.id.powerOn);
        engineOff = view.findViewById(R.id.engineOff);
        airControl = view.findViewById(R.id.airControl);
        btnEmerOn = view.findViewById(R.id.btnEmerLightOn);
        btnEmerOff = view.findViewById(R.id.btnEmerLightOnSiren);
        btnDoorOpen = view.findViewById(R.id.btnDoorOpen);
        btnDoorLock = view.findViewById(R.id.btnDoorLock);
        //공조설정 넘어가는 부분
        airControl.setOnClickListener(this);

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
        btnDoorOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_msg(v);

            }
        });
        btnDoorLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_msg(v);
            }
        });

        notification = new Notification(getActivity());

        remoteControlAsync = new RemoteControlAsync(getContext());
        remoteControlAsync.execute(carId);


        return view;
    }
    public void send_msg(final View view){
        if(pw == null) {
            if(remoteControlAsync.isOnPW()) {
                pw = remoteControlAsync.getPrintWriter();
            }
        }
        new Thread(new Runnable() {
            String message = "";
            @Override
            public void run() {
                if(view.getId()==R.id.powerOn){
                    if(handler.select().getCount()==0){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getContext(), CarAirConditionSettingActivity.class);
                                intent.putExtra("carId", carId);
                                startActivity(intent);
                                Toast.makeText(getContext(),"공조설정을 하세요",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        //db에 저장된 온도 정보를 읽어서 보내줌
                        Cursor cursor = handler.select();
                        String temp = "";
                        String time = "";
                        if (handler.select().getCount() != 0) {
                            while (cursor.moveToNext()) {
                                temp = cursor.getString(0);
                                time = cursor.getString(1);
                            }
                        }
                        if (Integer.parseInt(time) <= 9) {
                            time = "0" + time;
                        }
                        message = "S" + temp + time;
                        Log.d("test", message);
                    }
                }else if(view.getId()==R.id.engineOff){
                    //엔진 스탑
                    message = "T";
                }else if(view.getId()==R.id.btnEmerLightOn){
                    //비상등 ON
                    message = "A";
                }else if(view.getId()==R.id.btnEmerLightOnSiren){
                    //비상등&경적 ON
                    message = "B";
                }else if(view.getId()==R.id.btnDoorOpen){
                    //도어 Open
                    message="O";
                }else if(view.getId()==R.id.btnDoorLock){
                    //도어 Lock
                    message="L";
                }
                pw.println("job:"+message+":phone:"+carId);
                pw.flush();
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.airControl:
                Intent intent = new Intent(getContext(), CarAirConditionSettingActivity.class);
                intent.putExtra("carId", carId);
                startActivity(intent);
                break;
        }
    }

    public RemoteControlAsync getRemoteControlAsync() {
        return remoteControlAsync;
    }
}
