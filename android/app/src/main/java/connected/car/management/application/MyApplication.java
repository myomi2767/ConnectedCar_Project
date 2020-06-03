package connected.car.management.application;

import android.app.Application;

import connected.car.management.car.CarVO;

public class MyApplication extends Application {
    public static CarVO CarInfo;
    @Override
    public void onCreate() {
        super.onCreate();
        CarInfo = new CarVO("","","","","","","",0,0,0,"",0);
    }

    public static void setCarInfo(CarVO car) {
        MyApplication.CarInfo.setCar_id(car.getCar_id());
        MyApplication.CarInfo.setCar_brand(car.getCar_brand());
        MyApplication.CarInfo.setCar_model(car.getCar_model());
        MyApplication.CarInfo.setCar_fuel_type(car.getCar_fuel_type());
        MyApplication.CarInfo.setCar_year(car.getCar_year());
        MyApplication.CarInfo.setCar_volume(car.getCar_volume());
        MyApplication.CarInfo.setSpecial_car(car.getSpecial_car());
        MyApplication.CarInfo.setDrive_distance(car.getDrive_distance());
        MyApplication.CarInfo.setCar_model_name(car.getCar_model_name());
    }
}
