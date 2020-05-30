package connected.car.management.period;

public class MyexpendVO {
    String my_expend_no;
    String car_id;
    String expend_item;
    String expend_term;
    String expend_id;
    String my_expend_replace;
    String my_expend_km;

    public MyexpendVO(){

    }

    public MyexpendVO(String my_expend_no, String car_id, String expend_item, String expend_term, String expend_id, String my_expend_replace, String my_expend_km) {
        this.my_expend_no = my_expend_no;
        this.car_id = car_id;
        this.expend_item = expend_item;
        this.expend_term = expend_term;
        this.expend_id = expend_id;
        this.my_expend_replace = my_expend_replace;
        this.my_expend_km = my_expend_km;
    }

    public String getMy_expend_no() {
        return my_expend_no;
    }

    public void setMy_expend_no(String my_expend_no) {
        this.my_expend_no = my_expend_no;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getExpend_item() {
        return expend_item;
    }

    public void setExpend_item(String expend_item) {
        this.expend_item = expend_item;
    }

    public String getExpend_term() {
        return expend_term;
    }

    public void setExpend_term(String expend_term) {
        this.expend_term = expend_term;
    }

    public String getExpend_id() {
        return expend_id;
    }

    public void setExpend_id(String expend_id) {
        this.expend_id = expend_id;
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
                "my_expend_no='" + my_expend_no + '\'' +
                ", car_id='" + car_id + '\'' +
                ", expend_item='" + expend_item + '\'' +
                ", expend_term='" + expend_term + '\'' +
                ", expend_id='" + expend_id + '\'' +
                ", my_expend_replace='" + my_expend_replace + '\'' +
                ", my_expend_km='" + my_expend_km + '\'' +
                '}';
    }
}
