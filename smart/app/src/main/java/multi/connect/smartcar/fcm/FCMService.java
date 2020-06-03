package multi.connect.smartcar.fcm;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import multi.connect.smartcar.MainActivity;
import multi.connect.smartcar.R;


public class FCMService extends FirebaseMessagingService {
    public static final int MSG_SEND_TO_ACTIVITY = 4;
    public FCMService() {
    }
    //토큰값이 새롭게 갱신되면 호출되는 메소드 - 토큰값을 가지고 있게 되는데 거의 변경되지 않는다.
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("myfcm","new token:"+s);
    }
    //firebase서버에서 메시지가 도착                                                                                                                                                                                       하면 자동으로 호출되는 메소드
    // 안드로이드 OS가 별도의 쓰레드를 발생시켜서 호출하게 된다.
    //Toast를 띄운다던가 이런 작업을 하려면 별도의 핸들러를 이용해서 작업을 해야 한다.
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //도착한 메시지가 있다면...
        if(remoteMessage.getNotification()!=null){
            //메시지를 추출한다. 메인쓰레드를 얻어와서 작업해야 한다.
            final String car_id = remoteMessage.getNotification().getTitle();
            final String message = remoteMessage.getNotification().getBody();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(car_id==null){

                        //Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
                        Intent popupIntent = new Intent(getBaseContext(), MsgActivity.class);
                        popupIntent.putExtra("message",message);
                        PendingIntent pi = PendingIntent.getActivity(getBaseContext(), 0, popupIntent, PendingIntent.FLAG_ONE_SHOT);
                        try {
                            pi.send();
                        } catch (PendingIntent.CanceledException e) {
                            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG);
                        }
                    }else {
                        //Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
                        setOtherGps(message);
                    }
                }
            });
        }
    }
    public void setOtherGps(String message){
        Intent intent = new Intent("otherGPS");
        intent.putExtra("gps",message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
