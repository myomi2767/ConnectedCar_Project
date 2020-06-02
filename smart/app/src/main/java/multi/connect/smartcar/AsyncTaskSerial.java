package multi.connect.smartcar;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import de.nitri.gauge.Gauge;
//라떼 서버 연결  AsyncTaskSerial 클래스
public class AsyncTaskSerial extends AsyncTask<Void,String,String> {
    ToggleButton power;
    Gauge speedometer;
    TextView distance;
    Socket socket;
    Context context;
    OutputStream os;
    PrintWriter pw;
    int speed ;
    //String id = "backCar";
    String carNum = "82가1004";
    BufferedReader br;
    StringTokenizer token;
    InputStream is;
    InputStreamReader isr;
    MediaPlayer mediaPlayer;

    public AsyncTaskSerial(Context context,ToggleButton power, int speed, Gauge speedometer, TextView distance) {
        this.context = context;
        this.power = power;
        this.speedometer = speedometer;
        this.distance = distance;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            socket = new Socket("70.12.228.164", 50000);
            if (socket != null) {
                ioWork();
                pw.println("info:" + carNum);
                pw.flush();
            }
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String msg;
                        try {
                            msg = br.readLine();
                            if (msg.length() > 0) {
                                Log.d("test", "서버로 부터 수신된 메시지>>"
                                        + msg);
                                //publishProgress(msg);
                                filteringMsg(msg);
                            }
                        } catch (IOException e) {
                        }
                    }
                }
            });
            t1.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
      return null;
    }

    private void filteringMsg(String msg) {
        token = new StringTokenizer(msg, ":");
        String protocol = token.nextToken();
        String message = token.nextToken();
        System.out.println("프로토콜:" + protocol + ",메시지:" + message);
        if (protocol.equals("sonic")) {
            publishProgress("sonic", "msg", message);
        } else if (protocol.equals("speed")) {
            publishProgress("speed", "msg", message);
        }/*else if(protocol.equals("job")){
                Intent intent = new Intent(,MainActivity.class);
                intent.putExtra("carNum", carNum);
                //intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }*/ else if (protocol.equals("message")) {
            String category = token.nextToken();
            String carNum = token.nextToken();
            switch (category) {
                case "EM": {
                    Log.d("alarm", "EM 알람 테스트");
                    break;
                }
                case "TRUNK": {
                    Log.d("alarm", "TRUNK 알람 테스트");
                    break;
                }
                case "CAUTION": {
                    Log.d("alarm", " CAUTION 알람 테스트");
                    break;
                }
            }
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        String state = values[0];
        Log.d("progress", values[0] + ":::" + values[1] + ":::" + values[2]);
        if (power.isChecked()) {
            if (state.equals("sonic")) {
                distance.setText(values[2] + "cm");
            } else if (state.equals("speed")) {
                speed = Integer.parseInt(values[2]);
                speedometer.moveToValue(speed);
                speedometer.setLowerText(values[2]);
            }
        } else {
            distance.setText("");
        }
    }

    void ioWork() {
        try {
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            os = socket.getOutputStream();
            pw = new PrintWriter(os, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // MediaPlayer 해지
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    */
}

