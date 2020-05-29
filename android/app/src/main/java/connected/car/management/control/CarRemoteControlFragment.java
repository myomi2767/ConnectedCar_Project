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
    Button btnDoorOpen;
    Button btnDoorLock;

    Socket socket;

    PrintWriter pw;

    String carId;
    Notification notification;

    RemoteControlAsync remoteControlAsync;

    public CarRemoteControlFragment(String car_id) {
        // Required empty public constructor
        this.carId = car_id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car_remote_control, container, false);

        powerOn = view.findViewById(R.id.powerOn);
        engineOff = view.findViewById(R.id.engineOff);
        airControl = view.findViewById(R.id.airControl);
        btnEmerOn = view.findViewById(R.id.btnEmerLightOn);
        btnEmerOff = view.findViewById(R.id.btnEmerLightOnSiren);
        btnDoorOpen = view.findViewById(R.id.btnDoorOpen);
        btnDoorLock = view.findViewById(R.id.btnDoorLock);

        powerOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CarAirConditionSettingActivity.class);
                startActivity(intent);
                getActivity().finish();
                //send_msg(v);
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
                    message = "engineStart";
                }else if(view.getId()==R.id.engineOff){
                    message = "engineStop";
                }else if(view.getId()==R.id.btnEmerLightOn){
                    message = "emergencyOn";
                }else if(view.getId()==R.id.btnEmerLightOnSiren){
                    message = "emergencyAndSiren";
                }else if(view.getId()==R.id.btnDoorOpen){
                    message="doorOpen";
                }else if(view.getId()==R.id.btnDoorLock){
                    message="doorLock";
                }
                pw.println("job:"+message+":phone:"+carId);
                pw.flush();
            }
        }).start();
    }

    public RemoteControlAsync getRemoteControlAsync() {
        return remoteControlAsync;
    }
}
