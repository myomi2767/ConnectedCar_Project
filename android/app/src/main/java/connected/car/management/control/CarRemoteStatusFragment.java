package connected.car.management.control;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
public class CarRemoteStatusFragment extends Fragment {
    CarRemoteControlFragment remoteControlFragment;
    RemoteControlAsync remoteControlAsync;

    PrintWriter pw;

    Button refresh;

    TextView engineText;
    TextView engineText2;

    TextView doorText;
    TextView doorText2;
    TextView doorText3;

    TextView airText;
    TextView airText2;

    boolean[] status = new boolean[4];

    String carId;

    public CarRemoteStatusFragment(String carId, CarRemoteControlFragment fragment) {
        // Required empty public constructor
        this.carId = carId;
        this.remoteControlFragment = fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_car_remote_status, container, false);

        refresh = view.findViewById(R.id.btnRefresh);
        engineText = view.findViewById(R.id.engineText);
        engineText2 = view.findViewById(R.id.engineText2);

        doorText = view.findViewById(R.id.doorText);
        doorText2 = view.findViewById(R.id.doorText2);
        doorText3 = view.findViewById(R.id.doorText3);

        airText = view.findViewById(R.id.airText);
        airText2 = view.findViewById(R.id.airText2);

        remoteControlAsync = remoteControlFragment.getRemoteControlAsync();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_msg(v);
                while (!remoteControlAsync.isMsgIn()) {

                }
                status = remoteControlAsync.getStatusResult();
                setView();
                remoteControlAsync.setMessageIn(false);
            }
        });
        return view;
    }

    public void send_msg(final View view){
        if(pw == null) {
            Log.d("test", "null인가");
            if(remoteControlAsync.isOnPW()) {
                pw = remoteControlAsync.getPrintWriter();
                Log.d("test", pw.toString());
            }
        }
        new Thread(new Runnable() {
            String message = "";
            @Override
            public void run() {
                if(view.getId()==R.id.btnRefresh){
                    message="Z";
                }
                pw.println("job:"+message+":phone:"+carId);
                pw.flush();
            }
        }).start();
    }

    public void setView(){
        if(status[0]){
            engineText.setText("켜짐");
            engineText.setTextColor(Color.BLUE);
            engineText2.setText("켜짐");
            engineText2.setTextColor(Color.BLUE);
        }else {
            engineText.setText("꺼짐");
            engineText.setTextColor(Color.BLACK);
            engineText2.setText("꺼짐");
            engineText2.setTextColor(Color.BLACK);
        }
        if (status[1]){
            doorText.setText("열림");
            doorText.setTextColor(Color.BLUE);
            doorText2.setText("열림");
            doorText2.setTextColor(Color.BLUE);
            doorText3.setText("모두 열림");
        }else {
            doorText.setText("닫힘");
            doorText.setTextColor(Color.BLACK);
            doorText2.setText("닫힘");
            doorText2.setTextColor(Color.BLACK);
            doorText3.setText("모두 닫힘");
        }
        if (status[2]){
            airText.setText("켜짐");
            airText.setTextColor(Color.BLUE);
            airText2.setText("켜짐");
            airText2.setTextColor(Color.BLUE);
        }else {
            airText.setText("꺼짐");
            airText.setTextColor(Color.BLACK);
            airText2.setText("꺼짐");
            airText2.setTextColor(Color.BLACK);
        }
    }
}
