package connected.car.management.member;

import android.os.Parcel;
import android.os.Parcelable;

public class MemberVO implements Parcelable {
    String user_id;
    String car_id;
    String user_password;
    String user_name;
    String user_birthdate;
    String user_gender;
    String driver_license;

    public MemberVO(String user_id, String user_password) {
        this.user_id = user_id;
        this.user_password = user_password;
    }

    public MemberVO(String user_id, String car_id, String user_password, String user_name, String user_birthdate, String user_gender, String driver_license) {
        this.user_id = user_id;
        this.car_id = car_id;
        this.user_password = user_password;
        this.user_name = user_name;
        this.user_birthdate = user_birthdate;
        this.user_gender = user_gender;
        this.driver_license = driver_license;
    }

    public MemberVO(Parcel in) {
        user_id = in.readString();
        car_id = in.readString();
        user_password = in.readString();
        user_name = in. readString();
        user_birthdate = in.readString();
        user_gender = in.readString();
        driver_license = in.readString();
    }

    public static final Creator<MemberVO> CREATOR = new Creator<MemberVO>() {
        @Override
        public MemberVO createFromParcel(Parcel in) {
            return new MemberVO(in);
        }

        @Override
        public MemberVO[] newArray(int size) {
            return new MemberVO[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(car_id);
        dest.writeString(user_password);
        dest.writeString(user_name);
        dest.writeString(user_birthdate);
        dest.writeString(user_gender);
        dest.writeString(driver_license);
    }

}
