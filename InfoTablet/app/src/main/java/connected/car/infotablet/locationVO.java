package connected.car.infotablet;

public class locationVO {
    private double latitude;
    private double longitude;

    public locationVO(){

    }

    public locationVO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "locationVO{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
