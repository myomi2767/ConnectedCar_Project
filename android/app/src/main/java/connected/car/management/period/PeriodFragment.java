package connected.car.management.period;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import connected.car.management.R;
import connected.car.management.control.MainActivity;
import connected.car.management.member.LoginAsync;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeriodFragment extends Fragment {
    PeriodAdapter myadapter;
    LinearLayoutManager manager;
    RecyclerView list;
    List<TermVO> termlist = new ArrayList<TermVO>();

    private String car_id;
    private String car_fuel_type;


    public PeriodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_period, container, false);
        list = view.findViewById(R.id.rlist);

        car_id = ((MainActivity)getActivity()).main_car_id; //액티비티로부터 가져온 로그인된 car_id



        //new PeriodAsync(this).execute(car_id);
        //방법이 2개 있따.
        // 1. carid와 회원

        return view;
    }

}
