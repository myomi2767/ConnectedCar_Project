package multi.connect.smartcar;

public class mapPoint {
    private String Name;
    private double latitude;
    private double longitude;

    public mapPoint() {
        super();
    }

    public mapPoint(String name, double latitude, double longitude) {
        Name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
        return "mapPoint{" +
                "Name='" + Name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
