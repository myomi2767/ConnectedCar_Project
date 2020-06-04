package connected.car.infotablet;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class BackGroundActivity extends AppCompatActivity {
    String id;
    String carNum;
    InfoClient info;
    LocationManager locationManager;
    String[] permission_list={
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    LocationVO locationVO;
    boolean permission_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_ground);
        id = "backCar";
        carNum = "12가1234";
        info = new InfoClient(this,carNum);
        //위험 권한 확인
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            permission_state = true;
            printToast("권한이 설정되었습니다.");
        }else {
            permission_state = false;
            printToast("권한을 설정해야 합니다.");
            //2. 권한이 없는 경우 권한을 설정하는 메시지를 띄운다.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);
        }
        getMyLocation();
    }
    public void getMyLocation(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            for(String permission:permission_list){
                if(checkSelfPermission(permission)==PackageManager.PERMISSION_DENIED){
                    return;
                }
            }
        }
        //이전에 측정했었던 값을 가져오고 - 새롭게 측정하는데 시간이 많이 걸릴 수 있으므로
        Location gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(gps_loc!=null){
            setMyLocation(gps_loc);
        }else{
            if(network_loc!=null){
                setMyLocation(network_loc);
            }
        }
        Log.d("myloc","==================================");
        //현재 측정한 값도 가져오고
        MylocationListener locationListener = new MylocationListener();
        //현재 활성화되어 있는 Provider를 체크
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    3000,10,locationListener);
        }else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    3000,10,locationListener);
        }
    }
    public void setMyLocation(Location myLocation){
        Log.d("myloc","위도:"+myLocation.getLatitude());
        Log.d("myloc","경도:"+myLocation.getLongitude());
        locationVO = new LocationVO(myLocation.getLatitude(), myLocation.getLongitude());
    }

    //현재 위치가 변경되거나 Provider에 변화가 있을 때 반응할 수 있도록 설정
    class MylocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            //현재 위도 경도가 변경되면 호출되는 메소드
            setMyLocation(location);
            locationManager.removeUpdates(this);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1000 && grantResults.length>0){//권한의 성공 설정에 대한 결과가 있다는 의미
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                permission_state = true;
            }else{
                printToast("권한 설정을 하지 않았으므로 기능을 사용할 수 없습니다.");
            }
        }
    }

    public void printToast(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }
    public LocationVO getLocationVO(){
        return locationVO;
    }
}
