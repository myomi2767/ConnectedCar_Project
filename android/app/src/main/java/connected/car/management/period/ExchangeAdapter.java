package connected.car.management.period;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import connected.car.management.R;

public class ExchangeAdapter extends RecyclerView.Adapter<ExchangeAdapter.ViewHolder>{

    Context context;
    int row_res_id;
    List<ExpendableVO> data;


    public ExchangeAdapter(Context context, int row_res_id, List<ExpendableVO> data) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //Log.d("===","onBindViewHolder"+position);
        TextView txt_exchange_id = holder.expend_id;
        TextView txt_exchange_code = holder.expend_code;
        TextView txt_exchange_type = holder.expend_type;
        Button btn_exchange_select = holder.select_expend;

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView expend_id; //visibility = "gone" 처리 되어 눈에 보이지는 않습니다.
        TextView expend_code;
        TextView expend_type;
        Button select_expend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expend_id = itemView.findViewById(R.id.text_exchange_id);
            expend_code = itemView.findViewById(R.id.text_exchange_code);
            expend_type = itemView.findViewById(R.id.text_exchange_type);
            select_expend = itemView.findViewById(R.id.button_selectexpendable);


        }
    }
}
