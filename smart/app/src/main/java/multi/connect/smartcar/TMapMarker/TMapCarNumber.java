package multi.connect.smartcar.TMapMarker;

import android.graphics.Bitmap;

import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;

public class TMapCarNumber {
    TMapPoint TMapPoint;
    Bitmap carNum;
    Bitmap sendmsg;
    public TMapCarNumber(TMapPoint alTMapPoint,Bitmap carNum,Bitmap sendmsg){
        this.TMapPoint = alTMapPoint;
        this.carNum = carNum;
        this.sendmsg = sendmsg;
    }
    public TMapMarkerItem markerItem(){
        TMapMarkerItem markerItem = new TMapMarkerItem();
        // 마커 아이콘 지정
        markerItem.setIcon(carNum);
        //markerItem1.setIcon(point);
        //마커의 오른쪽에 이미지 넣기
        markerItem.setCalloutRightButtonImage(sendmsg);
        //마커의 타이틀(차량번호)
        markerItem.setCalloutTitle("메시지 보내기");
        //풍선뷰 사용하기
        markerItem.setCanShowCallout(true);
        //마커의 모든 풍선뷰 자동활성화(키면 풍선뷰 클릭이벤트 실행 안됨)
        //markerItem1.setAutoCalloutVisible(true);
        // 마커의 좌표 지정
        markerItem.setTMapPoint(TMapPoint);
        return markerItem;
    }
}
