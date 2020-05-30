package connected.car.management.period;

public class TermVO {

    String car_brand;
    String car_fuel_type;
    String expend_type;
    String expend_term;

    public TermVO(){

    }

    public TermVO(String car_brand, String car_fuel_type) {
        this.car_brand = car_brand;
        this.car_fuel_type = car_fuel_type;
    }

    public TermVO(String car_brand, String car_fuel_type, String expend_type, String expend_term) {
        this.car_brand = car_brand;
        this.car_fuel_type = car_fuel_type;
        this.expend_type = expend_type;
        this.expend_term = expend_term;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

    public String getCar_fuel_type() {
        return car_fuel_type;
    }

    public void setCar_fuel_type(String car_fuel_type) {
        this.car_fuel_type = car_fuel_type;
    }

    public String getExpend_type() {
        return expend_type;
    }

    public void setExpend_type(String expend_type) {
        this.expend_type = expend_type;
    }

    public String getExpend_term() {
        return expend_term;
    }

    public void setExpend_term(String expend_term) {
        this.expend_term = expend_term;
    }

    @Override
    public String toString() {
        return "TermVO{" +
                "car_brand='" + car_brand + '\'' +
                ", car_fuel_type='" + car_fuel_type + '\'' +
                ", expend_type='" + expend_type + '\'' +
                ", expend_term='" + expend_term + '\'' +
                '}';
    }
}
