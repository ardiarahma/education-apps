package com.ardiarahma.education.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ardiarahma.education.R;
import com.ardiarahma.education.models.Log_detail;

import java.util.List;

/**
 * Created by Windows 10 on 5/12/2019.
 */

public class RV_LogActivityReportAdapter extends RecyclerView.Adapter<RV_LogActivityReportAdapter.ViewHolder>{

    Context context;
    List<Log_detail> log_details;

    public RV_LogActivityReportAdapter(Context context, List<Log_detail> log_details) {
        this.context = context;
        this.log_details = log_details;
    }

    @Override
    public RV_LogActivityReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_logs, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RV_LogActivityReportAdapter.ViewHolder holder, int position) {
        holder.feature_name = log_details.get(position).getFitur();
        holder.fitur.setText(log_details.get(position).getFitur());
        holder.waktu.setText(log_details.get(position).getCreated_at());

        if (holder.feature_name.equals("Mini Games")){
            holder.thumbnail.setImageDrawable(context.getResources().getDrawable(R.drawable.game));
        }else if(holder.feature_name.equals("Ebook")){
            holder.thumbnail.setImageDrawable(context.getResources().getDrawable(R.drawable.ebook));
        }else if (holder.feature_name.equals("Bank Soal")){
            holder.thumbnail.setImageDrawable(context.getResources().getDrawable(R.drawable.task));
        }
    }

    @Override
    public int getItemCount() {
       return log_details.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        String feature_name;
        TextView fitur, waktu;
        ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);

//            namaAnak = itemView.findViewById(R.id.log_nama_anak);
            fitur = itemView.findViewById(R.id.log_details);
            waktu = itemView.findViewById(R.id.date);
            thumbnail = itemView.findViewById(R.id.logs_thumbnail);
        }
    }
}
