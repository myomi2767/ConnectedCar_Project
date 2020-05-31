package multi.connect.smartcar;

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
    AsyncTaskMain asyncTaskMain;
    Socket socket;
    BufferedReader br;
    InputStream  is;
    InputStreamReader isr;
    OutputStream os;
    PrintWriter pw;
    StringTokenizer token;
    String id;
    String carNum;

    public InfoClient(Context context,String id, String carNum) {
        this.context = context;
        this.id = id;
        this.carNum = carNum;
        asyncTaskMain = new AsyncTaskMain();
        asyncTaskMain.execute();
    }

    public void sendMessage(String msg) {
        final String message = msg;
        new Thread(new Runnable() {
            @Override
            public void run() {


                pw.println(message);
            }
        }).start();
    }

    class AsyncTaskMain extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {

            try {
                //메인서버 아이피 입력
                socket = new Socket("192.168.43.190", 12345);
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
    void ioWork(){
        try {
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            os = socket.getOutputStream();
            pw = new PrintWriter(os,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void filteringMsg(String msg) {
        token = new StringTokenizer(msg, "/");
        String protocol = token.nextToken();
        String message = token.nextToken();
        System.out.println("프로토콜:" + protocol + ",메시지:" + message);
        if(protocol.equals("engine_on")){
            Intent intent = new Intent(context,MainActivity.class);
            intent.putExtra("carNum", carNum);
            intent.putExtra("id", id);
            context.startActivity(intent);
            ((Activity)context).finish();
        }else if(protocol.equals("emergency")){
            //publishProgress("sonic","msg",message);
        }else if(protocol.equals("trunk")){
           /// publishProgress("speed","msg",message);
        }else if(protocol.equals("caution")){

        }
    }
}
