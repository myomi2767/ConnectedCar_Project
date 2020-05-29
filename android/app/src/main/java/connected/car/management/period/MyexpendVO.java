package connected.car.management.period;

public class MyexpendVO {
    String expend_id;
    String car_id;
    String expend_type;
    String my_expend_replace;
    String my_expend_km;



    public MyexpendVO(){

    }

    public MyexpendVO(String expend_id, String car_id, String expend_type, String my_expend_replace, String my_expend_km) {
        this.expend_id = expend_id;
        this.car_id = car_id;
        this.expend_type = expend_type;
        this.my_expend_replace = my_expend_replace;
        this.my_expend_km = my_expend_km;
    }

    public String getExpend_id() {
        return expend_id;
    }

    public void setExpend_id(String expend_id) {
        this.expend_id = expend_id;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getExpend_type() {
        return expend_type;
    }

    public void setExpend_type(String expend_type) {
        this.expend_type = expend_type;
    }

    public String getMy_expend_replace() {
        return my_expend_replace;
    }

    public void setMy_expend_replace(String my_expend_replace) {
        this.my_expend_replace = my_expend_replace;
    }

    public String getMy_expend_km() {
        return my_expend_km;
    }

    public void setMy_expend_km(String my_expend_km) {
        this.my_expend_km = my_expend_km;
    }

    @Override
    public String toString() {
        return "MyexpendVO{" +
                "expend_id='" + expend_id + '\'' +
                ", car_id='" + car_id + '\'' +
                ", expend_type='" + expend_type + '\'' +
                ", my_expend_replace='" + my_expend_replace + '\'' +
                ", my_expend_km='" + my_expend_km + '\'' +
                '}';
    }
}
