package connected.car.infotablet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class InfoClient {
    Context context;
    Socket socket;
    BufferedReader br;
    InputStream  is;
    InputStreamReader isr;
    OutputStream os;
    PrintWriter pw;
    StringTokenizer token;
    //String id;
    String car_id;



    public InfoClient(Context context,String car_id) {
        this.context = context;
       // this.id = id;
        this.car_id = car_id;
        new AsyncTaskMain().execute();

    }

    public void sendMessage(final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                pw.println("message:"+msg+":info:"+car_id);
            }
        }).start();
    }

    class AsyncTaskMain extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {

            try {
                //메인서버 아이피 입력

                socket = new Socket("192.168.43.229", 12345);
                Log.d("server","");
                Log.d("server","소켓소켓"+socket);
                if (socket != null) {
                    ioWork();
                }
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                    while (true) {
                        String msg;
                        try {
                            msg = br.readLine();
                            if(msg.length() > 0) {
                                Log.d("test", "서버로 부터 수신된 메시지>>"
                                        + msg);
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
    }

    public void ioWork(){
        try {
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            os = socket.getOutputStream();
            pw = new PrintWriter(os,true);
            Log.d("server","xdsafhgjkljeifjlsf들어왔음");
            pw.println("info:"+car_id);
            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void filteringMsg(String msg) {
        token = new StringTokenizer(msg, ":");
        String protocol = token.nextToken();
        String message = token.nextToken();
        if(protocol.equals("job")){
            String category = token.nextToken();
            String id = token.nextToken();
            Intent intent = null;
            if(message.charAt(0)=='S'){
                intent = new Intent(context,MainActivity.class);
            }else {
                intent = new Intent(context,BackGroundActivity.class);
            }
            context.startActivity(intent);
            ((Activity)context).finish();
        }else if(protocol.equals("message")){
            String category = token.nextToken();
            String carNum = token.nextToken();
            switch (category) {
                case "EM": {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("carNum", carNum);
                    intent.putExtra("messageType", "EM");
                    Log.d("alarm","EM 알람 테스트");
                    break;
                }
                case "TRUNK": {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("carNum", carNum);
                    intent.putExtra("messageType", "TRUNK");
                    Log.d("alarm","TRUNK 알람 테스트");
                    break;
                }
                case "CAUTION": {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("carNum", carNum);
                    intent.putExtra("messageType", "CAUTION");
                    Log.d("alarm"," CAUTION 알람 테스트");
                    break;
                }
            }
        }else if (protocol.equals("location")){
            LocationVO vo = ((BackGroundActivity) context).getLocationVO();
            pw.println("location:search:info:"+car_id+":"+vo.getLatitude()+":"+vo.getLongitude());
        }
    }


}
