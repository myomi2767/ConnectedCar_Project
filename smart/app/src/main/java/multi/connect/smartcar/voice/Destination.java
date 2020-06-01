package multi.connect.smartcar.voice;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

import multi.connect.smartcar.POI;

public class Destination implements RecognitionListener {
    Context context;
    EditText destiName;
    TMapView tMapView;
    LinearLayout naviSearchView;
    ArrayAdapter<POI> POIAdapter;

    public Destination(Context context, EditText destiName, TMapView tMapView, LinearLayout naviSearchView) {
        this.context = context;
        this.destiName = destiName;
        this.tMapView = tMapView;
        this.naviSearchView = naviSearchView;
        POIAdapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1);
    }

    public ArrayAdapter getAdapter() {
        return POIAdapter;
    }
    @Override
    public void onReadyForSpeech(Bundle params) {
        Toast.makeText(context,"음성인식을 시작합니다.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {
        String message;

        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "오디오 에러";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "클라이언트 에러";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "퍼미션 없음";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "네트워크 에러";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "네트웍 타임아웃";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "찾을 수 없음";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RECOGNIZER가 바쁨";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "서버가 이상함";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "말하는 시간초과";
                break;
            default:
                message = "알 수 없는 오류임";
                break;
        }

        Toast.makeText(context, "에러가 발생하였습니다. : " + message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches =
                results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        for(int i = 0; i < matches.size() ; i++){
            destiName.setText(matches.get(i));
            searchPOI();
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm!=null) {
                imm.hideSoftInputFromWindow(naviSearchView.getWindowToken(), 0);
            }
        }
    }

    public void searchPOI() {
        TMapData data = new TMapData();
        String keyword = destiName.getText().toString();
        if (!TextUtils.isEmpty(keyword)) {
            data.findAllPOI(keyword, new TMapData.FindAllPOIListenerCallback() {
                @Override
                public void onFindAllPOI(final ArrayList<TMapPOIItem> arrayList) {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            POIAdapter.clear();

                            for (TMapPOIItem poi : arrayList) {
                                POIAdapter.add(new POI(poi));
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
