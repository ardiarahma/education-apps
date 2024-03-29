package com.ardiarahma.education.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ardiarahma.education.R;
import com.ardiarahma.education.activities.child.BanksoalShelvesActivity;
import com.ardiarahma.education.models.BanksoalSubject;

import java.util.List;

/**
 * Created by Windows 10 on 1/21/2019.
 */

public class RV_BanksoalSubject8Adapter extends RecyclerView.Adapter<RV_BanksoalSubject8Adapter.ViewHolder> {

    Context context;
    int classes = 8;
    List<BanksoalSubject> banksoalSubjects;

    public RV_BanksoalSubject8Adapter(Context context, List<BanksoalSubject> banksoalSubjects) {
        this.context = context;
        this.banksoalSubjects = banksoalSubjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_subject, parent, false);
        ViewHolder vHolder = new ViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.subject_img.setImageResource(banksoalSubjects.get(position).getThumbnail());
        holder.subject_title.setText(banksoalSubjects.get(position).getName());
        holder.subject_id = banksoalSubjects.get(position).getId();
        holder.title_subject = banksoalSubjects.get(position).getName();
        holder.subject_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BanksoalShelvesActivity.class);
                //ambil parameter subjects_id sm class nya
                intent.putExtra("subjectsId", holder.subject_id);
                intent.putExtra("classes", classes);
                intent.putExtra("title", holder.title_subject);
                //intent datanya
                v.getContext().startActivity(intent);
            }
        });

        if (holder.title_subject.equals("Agama")){
            holder.title_bg.setBackgroundResource(R.drawable.card_red);
        }else if(holder.title_subject.equals("Bahasa Indonesia")){
            holder.title_bg.setBackgroundResource(R.drawable.card_magenta);
        }else if (holder.title_subject.equals("Bahasa Inggris")){
            holder.title_bg.setBackgroundResource(R.drawable.card_pink);
        }else if(holder.title_subject.equals("Ilmu Pengetahuan Sosial")){
            holder.title_bg.setBackgroundResource(R.drawable.card_purple);
        }else if (holder.title_subject.equals("Ilmu Pengetahuan Alam")){
            holder.title_bg.setBackgroundResource(R.drawable.card_navy);
        }else if(holder.title_subject.equals("Matematika")){
            holder.title_bg.setBackgroundResource(R.drawable.card_turqoise);
        }else if (holder.title_subject.equals("Pendidikan Kewarganegaraan")){
            holder.title_bg.setBackgroundResource(R.drawable.card_green);
        }else if (holder.title_subject.equals("Pendidikan Olahraga")){
            holder.title_bg.setBackgroundResource(R.drawable.card_olive);
        }else if(holder.title_subject.equals("Prakarya")){
            holder.title_bg.setBackgroundResource(R.drawable.card_yellow);
        }else if (holder.title_subject.equals("Seni Budaya")){
            holder.title_bg.setBackgroundResource(R.drawable.card_orange);
        }
    }

    @Override
    public int getItemCount() {
        return banksoalSubjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        int subject_id;
        String title_subject;
        ImageView subject_img;
        TextView subject_title;
        CardView subject_item;
        ImageView title_bg;

        public ViewHolder(View itemView) {
            super(itemView);
            subject_img = (ImageView) itemView.findViewById(R.id.ic_mapel);
            subject_title = (TextView) itemView.findViewById(R.id.title_mapel);
            subject_item = (CardView) itemView.findViewById(R.id.item_mapel);
            title_bg = itemView.findViewById(R.id.title_bg);

        }
    }
}
