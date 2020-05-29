package connected.car.management.controlresult;

public class ControlResultVO {
    String car_id;
    String control_date;
    String control_code;
    String control_result;
    String control_remark;

    public ControlResultVO(){

    }

    public ControlResultVO(String car_id) {
        this.car_id = car_id;
    }



    public ControlResultVO(String control_date, String control_code, String control_result, String control_remark) {
        this.control_date = control_date;
        this.control_code = control_code;
        this.control_result = control_result;
        this.control_remark = control_remark;
    }

    public ControlResultVO(String car_id, String control_date, String control_code, String control_result, String control_remark) {
        this.car_id = car_id;
        this.control_date = control_date;
        this.control_code = control_code;
        this.control_result = control_result;
        this.control_remark = control_remark;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getControl_date() {
        return control_date;
    }

    public void setControl_date(String control_date) {
        this.control_date = control_date;
    }

    public String getControl_code() {
        return control_code;
    }

    public void setControl_code(String control_code) {
        this.control_code = control_code;
    }

    public String getControl_result() {
        return control_result;
    }

    public void setControl_result(String control_result) {
        this.control_result = control_result;
    }

    public String getControl_remark() {
        return control_remark;
    }

    public void setControl_remark(String control_remark) {
        this.control_remark = control_remark;
    }

    @Override
    public String toString() {
        return "RemoteControlResultItem{" +
                "control_date='" + control_date + '\'' +
                ", control_code='" + control_code + '\'' +
                ", control_result='" + control_result + '\'' +
                ", control_remark='" + control_remark + '\'' +
                '}';
    }
}
