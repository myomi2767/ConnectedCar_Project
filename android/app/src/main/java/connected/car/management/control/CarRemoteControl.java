package connected.car.management.control;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import connected.car.management.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarRemoteControl extends Fragment {
    ToggleButton powerOff;
    public CarRemoteControl() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car_remote_control, container, false);
        powerOff = view.findViewById(R.id.powerOff);
        powerOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(powerOff.isChecked()){
                    powerOff.setBackgroundDrawable(getResources().getDrawable(R.drawable.poweron));
                }else{
                    powerOff.setBackgroundDrawable(getResources().getDrawable(R.drawable.poweroff));
                }

            }
        });
        return view;
    }
}
