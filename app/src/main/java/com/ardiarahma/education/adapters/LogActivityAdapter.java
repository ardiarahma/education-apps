package com.ardiarahma.education.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ardiarahma.education.R;
import com.ardiarahma.education.activities.parent.ParentMainActivity;
import com.ardiarahma.education.activities.parent.ResultActivitiesReportActivity;
import com.ardiarahma.education.models.Siswa;

import java.util.List;

/**
 * Created by Windows 10 on 2/9/2019.
 */

public class LogActivityAdapter extends RecyclerView.Adapter<LogActivityAdapter.ViewHolder> {

    private List<Siswa> siswas;
    Context context;

    public LogActivityAdapter(Context context, List<Siswa> siswas) {
        this.context = context;
        this.siswas = siswas;
    }

    @Override
    public LogActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log_anak, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final LogActivityAdapter.ViewHolder holder, final int position) {
        holder.namaanak.setText(siswas.get(position).getName());
        holder.user_id = siswas.get(position).getUser_id();
        holder.id = siswas.get(position).getId();
        holder.nama = siswas.get(position).getName();
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResultActivitiesReportActivity.class);
                intent.putExtra("childId", holder.id);
                intent.putExtra("user_id", holder.user_id);
                intent.putExtra("childName", holder.nama);
                context.startActivity(intent);
            }
        });
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ParentDeleteChildActivity.class);
//                intent.putExtra("childId", holder.id);
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return siswas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        int user_id;
        int id;
        String nama;
        private TextView namaanak;
        private CardView details;

        public ViewHolder(final View view){
            super(view);
            namaanak = itemView.findViewById(R.id.namalengkapanak);
            details = itemView.findViewById(R.id.item_anak);

        }

//
    }




}
