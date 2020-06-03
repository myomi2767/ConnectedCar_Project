package connected.car.management.control;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import connected.car.management.R;
import connected.car.management.application.MyApplication;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarInfoFragment extends Fragment {

    TextView info_id;
    TextView info_year;
    TextView info_model;
    TextView info_km;

    public CarInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_car_info, container, false);

        info_id = view.findViewById(R.id.text_info_id);
        info_year = view.findViewById(R.id.text_info_year);
        info_model = view.findViewById(R.id.text_info_car_model_name);
        info_km = view.findViewById(R.id.text_info_km);

        info_id.setText(MyApplication.CarInfo.getCar_id());
        info_year.setText(MyApplication.CarInfo.getCar_year());
        info_model.setText(MyApplication.CarInfo.getCar_model_name());
        info_km.setText(MyApplication.CarInfo.getDrive_distance()+"");



        return view;

    }
}
