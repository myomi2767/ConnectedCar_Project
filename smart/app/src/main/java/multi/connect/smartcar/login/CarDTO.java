package multi.connect.smartcar.login;

public class CarDTO {
    public String speed;
    public String distance;

    public CarDTO(){

    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "speed='" + speed + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }

    public CarDTO(String speed, String distance) {
        this.speed = speed;
        this.distance = distance;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
