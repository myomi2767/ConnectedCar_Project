package connected.car.infotablet.fcm;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import connected.car.infotablet.R;


public class MsgActivity extends AppCompatActivity {
    String message;
    TextView receiveMsg;
    String text;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_msg);
        receiveMsg = findViewById(R.id.receiveMsg);
        Button OK = findViewById(R.id.msgCheck);
        Bundle extras = getIntent().getExtras();
        message = extras.getString("message");
        if(message.equals("EM")){
            text =getString(R.string.EM);
            mediaPlayer = MediaPlayer.create(MsgActivity.this,R.raw.em);
        }else if(message.equals("TRUNK")){
            text =getString(R.string.TRUNK);
            mediaPlayer = MediaPlayer.create(MsgActivity.this,R.raw.trunk);
        }else if(message.equals("CAUTION")) {
            text = getString(R.string.CAUTION);
            mediaPlayer = MediaPlayer.create(MsgActivity.this,R.raw.caution);
        }
        mediaPlayer.start();
        receiveMsg.setText(text);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.release();
            }
        });
    }
}
