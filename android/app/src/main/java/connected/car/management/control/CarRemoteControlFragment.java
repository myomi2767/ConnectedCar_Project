package connected.car.management.control;

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

    DBHelper dbHelper;
    SQLiteDatabase db;
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

        airControl.setOnClickListener(this);

        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

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
                    //db에 저장된 온도 정보를 읽어서 보내줌
                    String sql = "select * from airsetting where car_id = ? and datetime(set_time) = max(datetime(set_time))";
                    Cursor cursor = db.rawQuery(sql, new String[] {
                            carId
                    });
                    cursor.moveToNext();
                    if(cursor != null) {
                        AirSettingVO vo = new AirSettingVO(cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4));
                        Log.d("test", vo.toString());
                        message = "S" + vo.getAir_temp() + vo.getSet_time();
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
