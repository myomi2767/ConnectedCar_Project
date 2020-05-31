package connected.car.management.period;

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

public class PeriodAdapter extends RecyclerView.Adapter<PeriodAdapter.ViewHolder>{
    Context context;
    int row_res_id;
    List<TermVO> data;

    public PeriodAdapter(Context context, int row_res_id, List<TermVO> data) {
        this.context = context;
        this.row_res_id = row_res_id;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(row_res_id,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("===","onBindViewHolder"+position);
        TextView txt_expend_type = holder.expend_type;
        TextView txt_expend_max = holder.expend_term;


        txt_expend_type.setText(data.get(position).getExpend_item());
        txt_expend_max.setText(data.get(position).getExpend_term());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView expend_type;
        TextView expend_term;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expend_type = itemView.findViewById(R.id.text_bar);
            expend_term = itemView.findViewById(R.id.text_max);

        }
    }
}