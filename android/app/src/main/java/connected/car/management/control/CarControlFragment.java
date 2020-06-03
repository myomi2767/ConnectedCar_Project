package connected.car.management.control;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import connected.car.management.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class CarControlFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();
    String[] titleList = {"원격제어","차량상태"};
    CarRemoteControlFragment control;
    CarRemoteStatusFragment status;
    String car_id;
    /*public CarControlFragment(){
    }*/
    public CarControlFragment(String car_id) {
        // Required empty public constructor
        this.car_id = car_id;
        Log.d("msg",this.car_id+"::::"+car_id);
        control = new CarRemoteControlFragment(car_id);
        status = new CarRemoteStatusFragment(car_id, control);
        fragmentArrayList.add(control);
        fragmentArrayList.add(status);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_control, container, false);
        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.pager);
        FragAdapter adapter = new FragAdapter(getChildFragmentManager(),fragmentArrayList.size());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager, true);
        return view;
    }
    class FragAdapter extends FragmentPagerAdapter {
        public FragAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }
        @Override
        public Fragment getItem(int position) {
            Log.d("test", "title의 포지션 : " + position);
            return fragmentArrayList.get(position);
        }
        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList[position];
        }
    }

    public CarRemoteControlFragment getCarRemoteControlFragment(){
        return control;
    }
}