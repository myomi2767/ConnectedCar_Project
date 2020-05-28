package connected.car.management.car;

public class CarVO {
    String car_id;
    String car_brand;
    String car_model;
    String car_fuel_type;
    String car_year;
    String car_volume;
    int rapid_speed;
    int drop_speed;
    int overspeed;
    String special_car;
    int driver_distance;

    public CarVO(String car_id, String car_brand, String car_model, String car_fuel_type, String car_year, String car_volume, int rapid_speed, int drop_speed, int overspeed, String special_car, int driver_distance) {
        this.car_id = car_id;
        this.car_brand = car_brand;
        this.car_model = car_model;
        this.car_fuel_type = car_fuel_type;
        this.car_year = car_year;
        this.car_volume = car_volume;
        this.rapid_speed = rapid_speed;
        this.drop_speed = drop_speed;
        this.overspeed = overspeed;
        this.special_car = special_car;
        this.driver_distance = driver_distance;
    }

    @Override
    public String toString() {
        return "CarVO{" +
                "car_id='" + car_id + '\'' +
                ", car_brand='" + car_brand + '\'' +
                ", car_model='" + car_model + '\'' +
                ", car_fuel_type='" + car_fuel_type + '\'' +
                ", car_year='" + car_year + '\'' +
                ", car_volume='" + car_volume + '\'' +
                ", rapid_speed=" + rapid_speed +
                ", drop_speed=" + drop_speed +
                ", overspeed=" + overspeed +
                ", special_car='" + special_car + '\'' +
                ", driver_distance=" + driver_distance +
                '}';
    }
}
