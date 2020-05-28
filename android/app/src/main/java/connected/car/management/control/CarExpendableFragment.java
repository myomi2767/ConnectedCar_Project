package connected.car.management.control;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import connected.car.management.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarExpendableFragment extends Fragment {

    public CarExpendableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_expendablet, container, false);
    }
}
