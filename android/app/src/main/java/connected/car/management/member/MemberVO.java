package connected.car.management.member;

public class MemberVO {
    String user_id;
    String car_id;
    String user_password;
    String user_name;
    String user_birthdate;
    String user_gender;
    String driver_license;

    public MemberVO(String user_id, String car_id, String user_password, String user_name, String user_birthdate, String user_gender, String driver_license) {
        this.user_id = user_id;
        this.car_id = car_id;
        this.user_password = user_password;
        this.user_name = user_name;
        this.user_birthdate = user_birthdate;
        this.user_gender = user_gender;
        this.driver_license = driver_license;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "user_id='" + user_id + '\'' +
                ", car_id='" + car_id + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_birthdate='" + user_birthdate + '\'' +
                ", user_gender='" + user_gender + '\'' +
                ", driver_license='" + driver_license + '\'' +
                '}';
    }
}
