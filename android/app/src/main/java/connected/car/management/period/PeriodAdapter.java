package connected.car.management.period;

import android.content.Context;
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

public class PeriodAdapter extends RecyclerView.Adapter<PeriodAdapter.ViewHolder>{
    Context context;
    int row_res_id;
    List<MyexpendVO> data;


    public PeriodAdapter(Context context, int row_res_id, List<MyexpendVO> data) {
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
        //Log.d("===","onBindViewHolder"+position);
        TextView txt_expend_type = holder.expend_type;
        TextView txt_expend_max = holder.expend_term;
        TextView txt_expend_kind = holder.expend_kind;
        ProgressBar bar_km = holder.progressBar;


        //현재 주행거리 값 받아와야 하는데 잘 모르겠음..
        int currentDistance = 25000;

        txt_expend_kind.setText(data.get(position).getExpend_kind());
        txt_expend_type.setText(data.get(position).getExpend_type());
        txt_expend_max.setText(data.get(position).getExpend_term());
        bar_km.setProgress((currentDistance-Integer.parseInt(data.get(position).getMy_expend_km()))*100/Integer.parseInt(data.get(position).getExpend_term()));
        //(현재총주행거리-최근부품교체주행거리)*100/expend_kind 하면 프로그레스바에 나온다.

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView expend_kind;
        TextView expend_type;
        TextView expend_term;
        ProgressBar progressBar;
        Button expend_recommend;
        Button expend_change;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expend_kind = itemView.findViewById(R.id.text_kind);
            expend_type = itemView.findViewById(R.id.text_type);
            expend_term = itemView.findViewById(R.id.text_max);
            progressBar = itemView.findViewById(R.id.progress_bar);
            expend_change = itemView.findViewById(R.id.button_exchange);
            expend_recommend = itemView.findViewById(R.id.button_recommend);

            //부품교체 버튼 누르면 인텐트가 나옵니다.
           expend_change.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {


               }
           });

        }
    }
}
