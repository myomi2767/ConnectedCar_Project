package connected.car.infotablet.TMapMarker;

import android.graphics.Color;

import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolygon;

import java.util.ArrayList;

public class TMapMarker {
    TMapPoint point;
    ArrayList<TMapPoint> polygonPoint = new ArrayList<TMapPoint>();
    public TMapMarker(TMapPoint point){
        this.point = point;
    }
    public TMapPolygon tMapPolygon(){
        polygonPoint.add(point); // 현재위치
        polygonPoint.add(new TMapPoint(point.getLatitude() + 0.0004875, point.getLongitude() - 0.0008776));//왼쪽 끝위치
        polygonPoint.add(new TMapPoint(point.getLatitude() + 0.0004875, point.getLongitude() + 0.0008764));//오른쪽 끝위치

        TMapPolygon tMapPolygon = new TMapPolygon();
        tMapPolygon.setLineColor(Color.BLUE);
        tMapPolygon.setPolygonWidth(2);
        tMapPolygon.setAreaColor(Color.GRAY);
        tMapPolygon.setAreaAlpha(100);
        for (int i = 0; i < polygonPoint.size(); i++) {
            tMapPolygon.addPolygonPoint(polygonPoint.get(i));
        }
        return tMapPolygon;
    }
}
