package connected.car.management.period;

public class MyexpendVO {
    String my_expend_no;
    String car_id;
    String expend_kind;
    String expend_term;
    String expend_type;
    String expend_id;
    String my_expend_replace;
    String my_expend_km;

    public MyexpendVO(){

    }

    public MyexpendVO(String my_expend_no, String car_id, String expend_kind, String expend_term, String expend_type, String expend_id, String my_expend_replace, String my_expend_km) {
        this.my_expend_no = my_expend_no;
        this.car_id = car_id;
        this.expend_kind = expend_kind;
        this.expend_term = expend_term;
        this.expend_type = expend_type;
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

    public String getExpend_kind() {
        return expend_kind;
    }

    public void setExpend_kind(String expend_kind) {
        this.expend_kind = expend_kind;
    }

    public String getExpend_term() {
        return expend_term;
    }

    public void setExpend_term(String expend_term) {
        this.expend_term = expend_term;
    }

    public String getExpend_type() {
        return expend_type;
    }

    public void setExpend_type(String expend_type) {
        this.expend_type = expend_type;
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
                ", expend_kind='" + expend_kind + '\'' +
                ", expend_term='" + expend_term + '\'' +
                ", expend_type='" + expend_type + '\'' +
                ", expend_id='" + expend_id + '\'' +
                ", my_expend_replace='" + my_expend_replace + '\'' +
                ", my_expend_km='" + my_expend_km + '\'' +
                '}';
    }
}
