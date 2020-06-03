package connected.car.infotablet.TMapMarker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

public class TMapRoute {
    TMapPoint startpoint;
    TMapPoint endpoint;
    Context context;
    Bitmap startImg;
    Bitmap endImg;
    TMapView tmapview;
    public TMapRoute(TMapPoint startpoint, TMapPoint endpoint, Context context, Bitmap startImg, Bitmap endImg, TMapView tMapView){
        this.startpoint = startpoint;
        this.endpoint = endpoint;
        this.context = context;
        this.startImg = startImg;
        this.endImg = endImg;
        this.tmapview = tMapView;
    }
    public void search(){
        TMapData data = new TMapData();
        data.findPathData(startpoint,endpoint, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(final TMapPolyLine path) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap startIcon = Bitmap.createScaledBitmap(startImg, 40, 50, true);
                        Bitmap endIcon = Bitmap.createScaledBitmap(endImg, 40, 50, true);
                        path.setLineWidth(5);
                        path.setLineColor(Color.RED);
                        tmapview.addTMapPath(path);
                        tmapview.setTMapPathIcon(startIcon,endIcon);
                    }
                });
            }
        });
    }
}
