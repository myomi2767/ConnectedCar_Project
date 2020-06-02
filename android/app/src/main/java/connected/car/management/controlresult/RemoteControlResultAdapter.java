package connected.car.management.controlresult;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import connected.car.management.R;

public class RemoteControlResultAdapter extends RecyclerView.Adapter<RemoteControlResultAdapter.ViewHolder>{
    Context context;
    int row_res_id;
    List<ControlResultVO> data;

    public RemoteControlResultAdapter(Context context, int row_res_id, List<ControlResultVO> data) {
        this.context = context;
        this.row_res_id = row_res_id;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(row_res_id,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("result","onBindViewHolder"+position);
        TextView control_date = holder.control_date;
        TextView control_code = holder.control_code;
        TextView control_result = holder.control_result;

        control_date.setText(data.get(position).getControl_date().substring(5));
        control_code.setText(data.get(position).getControl_code());
        control_result.setText(data.get(position).getControl_result());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView control_date;
        TextView control_code;
        TextView control_result;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            control_date = itemView.findViewById(R.id.control_date);
            control_code = itemView.findViewById(R.id.control_code);
            control_result = itemView.findViewById(R.id.control_result);
        }
    }
}
