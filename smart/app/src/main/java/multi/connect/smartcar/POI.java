package multi.connect.smartcar;

import com.skt.Tmap.TMapPOIItem;

public class POI {
    TMapPOIItem item;

    public POI() {
    }

    public POI(TMapPOIItem item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return item.getPOIName()+"  ,"+item.getPOIPoint();
    }
}
