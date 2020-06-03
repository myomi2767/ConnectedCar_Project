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

public class PeriodAdapter extends RecyclerView.Adapter<PeriodAdapter.ViewHolder>{
    private static final int FLAG_ACTIVITY_NEW_TASK = 0;
    private int drive_distance;
    private String car_model_name;
    Context context;
    int row_res_id;
    List<MyexpendVO> data;

    public PeriodAdapter(Context context, int row_res_id, List<MyexpendVO> data, int drive_distance, String car_model_name) {
        this.context = context;
        this.row_res_id = row_res_id;
        this.data = data;
        this.drive_distance = drive_distance;
        this.car_model_name = car_model_name;
        Log.d("===","periodAdapter 생성자의 car_model_name:"+car_model_name);

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
        TextView txt_expend_id = holder.expend_id;
        TextView txt_expend_type = holder.expend_type;
        TextView txt_expend_max = holder.expend_term;
        TextView txt_expend_kind = holder.expend_kind;
        ProgressBar bar_km = holder.progressBar;

        Button btn_expend_change = holder.expend_change;
        Button btn_expend_recommend = holder.expend_recommend;

       // txt_expend_id.setText(data.get(position).getExpend_id());//여기서 이슈가 생기고있는데 expend_id를 못받아오고있는 것 같다.
        txt_expend_kind.setText(data.get(position).getExpend_kind());
        txt_expend_type.setText(data.get(position).getExpend_type());
        txt_expend_max.setText(data.get(position).getExpend_term());
        bar_km.setProgress((drive_distance-Integer.parseInt(data.get(position).getMy_expend_km()))*100/Integer.parseInt(data.get(position).getExpend_term()));
        //(현재총주행거리-최근부품교체주행거리)*100/expend_kind 하면 프로그레스바에 나온다.
        //Log.d("===","periodAdapter에서 setText부분-> drive_distance:"+drive_distance);

        btn_expend_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context vcontext =  v.getContext();

                Intent intent;
                intent = new Intent(v.getContext(), PeriodExchangeActivity.class);

                //intent.putExtra("expend_id",data.get(position).getExpend_id());
                intent.putExtra("expend_kind", data.get(position).getExpend_kind());
                intent.putExtra("expend_type",data.get(position).getExpend_type());
                intent.putExtra("expend_term",data.get(position).getExpend_term());
                intent.putExtra("drive_distance",drive_distance);
                intent.putExtra("car_id", data.get(position).getCar_id());
                intent.putExtra("my_expend_no",data.get(position).getMy_expend_no());
                intent.putExtra("car_model_name",car_model_name);

               // Log.d("===","부품교체페이지로 넘어가는 my_expend_no"+data.get(position).getMy_expend_no());
                //Log.d("===","부품교체페이지로 넘어가는 car_id"+data.get(position).getCar_id());

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView expend_id;
        TextView expend_kind;
        TextView expend_type;
        TextView expend_term;
        ProgressBar progressBar;
        Button expend_recommend;
        Button expend_change;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expend_id = itemView.findViewById(R.id.text_exchange_id);
            expend_kind = itemView.findViewById(R.id.text_kind);
            expend_type = itemView.findViewById(R.id.text_type);
            expend_term = itemView.findViewById(R.id.text_max);
            progressBar = itemView.findViewById(R.id.progress_bar);
            expend_change = itemView.findViewById(R.id.button_exchange);
            expend_recommend = itemView.findViewById(R.id.button_recommend);

          /*  //부품교체 버튼 누르면 인텐트가 나옵니다.
           expend_change.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   Context context =  v.getContext();

                   Intent intent;
                   intent = new Intent(context, PeriodExchangeActivity.class);






               }
           });*/

        }
    }
}
