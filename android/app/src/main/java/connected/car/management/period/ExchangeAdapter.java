package connected.car.management.period;

import android.app.Activity;
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

    //private final String ex_id;
    //private final int drive_distance;
    Context context;
    int row_res_id;
    List<ExpendableVO> data;

    public ExchangeAdapter(Context context, int row_res_id, List<ExpendableVO> data) {
        this.context = context;
        this.row_res_id = row_res_id;
        this.data = data;
        //this.ex_id = ex_id;
        //this.drive_distance = drive_distance;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(row_res_id,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //Log.d("===","onBindViewHolder"+position);
        TextView txt_exchange_id = holder.expend_id;
        TextView txt_exchange_code = holder.expend_code;
        TextView txt_exchange_name = holder.expend_type;
        Button btn_exchange_select = holder.select_expend;

        txt_exchange_id.setText(data.get(position).getExpend_id());
        txt_exchange_code.setText(data.get(position).getExpend_code());
        txt_exchange_name.setText(data.get(position).getExpend_name());
        btn_exchange_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               TextView input_expend_name= ((Activity)context).findViewById(R.id.text_selectExpendable);
               input_expend_name.setText(data.get(position).getExpend_name());
               TextView input_expend_id = ((Activity)context).findViewById(R.id.text_hidden_expend_id);
               input_expend_id.setText(data.get(position).getExpend_id());
               /*TextView input_drive_distance =((Activity)context).findViewById(R.id.text_hidden_drive_distance);
               input_drive_distance.setText(drive_distance);*/

            }
        });

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
