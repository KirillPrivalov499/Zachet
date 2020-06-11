package com.example.gorun.NewStory.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gorun.NewStory.models.MyActivityModel;
import com.example.gorun.R;

import java.text.DecimalFormat;
import java.util.List;


public class RVActivitiesAdapter extends RecyclerView.Adapter<RVActivitiesAdapter.ViewHolder> {

    private List<MyActivityModel> mData;
    private LayoutInflater mInflater;
    private static DecimalFormat df2 = new DecimalFormat("#.00");
    private int hour;
    private int minute;


    public RVActivitiesAdapter(Context context, List<MyActivityModel> data, String typeActivity) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyActivityModel model = getItem(position);
        hour =  (int)(Integer.parseInt(model.getMovingTime()) / 60);
        minute = Integer.parseInt(model.getMovingTime()) % 60;
        holder.tvName.setText(model.getName());
        holder.tvDistance.setText(model.getDistance());
        holder.tvType.setText(model.getType());
        holder.tvMovingTime.setText(hour + " "+ minute);
        holder.tvStartDate.setText(model.getStartDate());
        holder.tvAverageSpeed.setText(String.valueOf(df2.format(Double.parseDouble(model.getAverageSpeed()) * 1.61)));
        holder.tvAverageCadence.setText(model.getAverageCadence());
        if(model.getAverageHeartrate()!=null)
          holder.tvAverageHeartrate.setText(model.getAverageHeartrate());
        holder.tvClimb.setText(String.valueOf(df2.format( Double.parseDouble(model.getEvelHigh()) - Double.parseDouble(model.getEvelLow()))));


    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    MyActivityModel getItem(int id) {
        return mData.get(id);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName;
        TextView tvDistance;
        TextView tvMovingTime;
        TextView tvType;
        TextView tvStartDate;
        TextView tvAverageSpeed;
        TextView tvAverageCadence;
        TextView tvAverageHeartrate;
        TextView tvClimb;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);
            tvMovingTime = (TextView) itemView.findViewById(R.id.tv_moving_time);
            tvType = (TextView) itemView.findViewById(R.id.tv_type);
            tvStartDate = (TextView) itemView.findViewById(R.id.tv_start_date);
            tvAverageSpeed = (TextView) itemView.findViewById(R.id.tv_average_speed);
            tvAverageCadence = (TextView) itemView.findViewById(R.id.tv_average_cadence);
            tvAverageHeartrate = (TextView) itemView.findViewById(R.id.tv_heartrate);
            tvClimb = (TextView) itemView.findViewById(R.id.tv_elev);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
        }
    }


}
