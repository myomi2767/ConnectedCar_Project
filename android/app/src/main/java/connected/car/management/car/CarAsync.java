package connected.car.management.car;

import android.content.Context;
import android.os.AsyncTask;

public class CarAsync extends AsyncTask<CarVO, Void, Void> {
    Context context;

    public CarAsync(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(CarVO... carVos) {
        return null;
    }
}
