package connected.car.management.period;

public class ExpendableVO {
    String expend_id;
    String expend_code;
    String expend_type;
    String expend_name;
    String expend_price;
    String expend_brand;
    String car_model_name;




    public ExpendableVO(){

    }

    public ExpendableVO(String expend_id, String expend_type, String expend_name, String car_model_name) {
        this.expend_id = expend_id;
        this.expend_type = expend_type;
        this.expend_name = expend_name;
        this.car_model_name = car_model_name;
    }

    public ExpendableVO(String expend_id, String expend_code, String expend_type, String expend_name, String expend_price, String expend_brand, String car_model_name) {
        this.expend_id = expend_id;
        this.expend_code = expend_code;
        this.expend_type = expend_type;
        this.expend_name = expend_name;
        this.expend_price = expend_price;
        this.expend_brand = expend_brand;
        this.car_model_name = car_model_name;
    }

    public String getExpend_id() {
        return expend_id;
    }

    public void setExpend_id(String expend_id) {
        this.expend_id = expend_id;
    }

    public String getExpend_code() {
        return expend_code;
    }

    public void setExpend_code(String expend_code) {
        this.expend_code = expend_code;
    }

    public String getExpend_type() {
        return expend_type;
    }

    public void setExpend_type(String expend_type) {
        this.expend_type = expend_type;
    }

    public String getExpend_name() {
        return expend_name;
    }

    public void setExpend_name(String expend_name) {
        this.expend_name = expend_name;
    }

    public String getExpend_price() {
        return expend_price;
    }

    public void setExpend_price(String expend_price) {
        this.expend_price = expend_price;
    }

    public String getExpend_brand() {
        return expend_brand;
    }

    public void setExpend_brand(String expend_brand) {
        this.expend_brand = expend_brand;
    }

    public String getCar_model_name() {
        return car_model_name;
    }

    public void setCar_model_name(String car_model_name) {
        this.car_model_name = car_model_name;
    }

    @Override
    public String toString() {
        return "ExpendableVO{" +
                "expend_id='" + expend_id + '\'' +
                ", expend_code='" + expend_code + '\'' +
                ", expend_type='" + expend_type + '\'' +
                ", expend_name='" + expend_name + '\'' +
                ", expend_price='" + expend_price + '\'' +
                ", expend_brand='" + expend_brand + '\'' +
                ", car_model_name='" + car_model_name + '\'' +
                '}';
    }
}
