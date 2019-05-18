package com.ardiarahma.education.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ardiarahma.educationapplication.R;
import com.ardiarahma.educationapplication.models.LogStudy;

import java.util.List;

/**
 * Created by Windows 10 on 5/12/2019.
 */

public class RV_LogStudyReportAdapter extends RecyclerView.Adapter<RV_LogStudyReportAdapter.ViewHolder>{

    Context context;
    List<LogStudy> logStudies;

    public RV_LogStudyReportAdapter(Context context, List<LogStudy> logStudies) {
        this.context = context;
        this.logStudies = logStudies;
    }

    @Override
    public RV_LogStudyReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_log_study, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RV_LogStudyReportAdapter.ViewHolder holder, int position) {
        holder.title.setText(logStudies.get(position).getTitle());
        holder.score.setText(logStudies.get(position).getScore());

    }

    @Override
    public int getItemCount() {
       return logStudies.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, score;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.log_details);
            score = itemView.findViewById(R.id.score);
        }
    }
}
