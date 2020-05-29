package connected.car.management.sqlite;

public class AirSettingVO {
    int air_setting_no;
    String set_time;
    String car_id;
    String air_temp;
    String engine_time;

    public AirSettingVO(int air_setting_no, String set_time, String car_id, String air_temp, String engine_time) {
        this.air_setting_no = air_setting_no;
        this.set_time = set_time;
        this.car_id = car_id;
        this.air_temp = air_temp;
        this.engine_time = engine_time;
    }

    @Override
    public String toString() {
        return "AirSettingVO{" +
                "air_setting_no=" + air_setting_no +
                ", set_time='" + set_time + '\'' +
                ", car_id='" + car_id + '\'' +
                ", air_temp='" + air_temp + '\'' +
                ", engine_time='" + engine_time + '\'' +
                '}';
    }

    public int getAir_setting_no() {
        return air_setting_no;
    }

    public void setAir_setting_no(int air_setting_no) {
        this.air_setting_no = air_setting_no;
    }

    public String getSet_time() {
        return set_time;
    }

    public void setSet_time(String set_time) {
        this.set_time = set_time;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getAir_temp() {
        return air_temp;
    }

    public void setAir_temp(String air_temp) {
        this.air_temp = air_temp;
    }

    public String getEngine_time() {
        return engine_time;
    }

    public void setEngine_time(String engine_time) {
        this.engine_time = engine_time;
    }
}
