package com.ardiarahma.education.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ardiarahma.education.R;
import com.ardiarahma.education.activities.child.TaskActivity;
import com.ardiarahma.education.models.BanksoalShelves;
import com.ardiarahma.education.networks.PreferencesConfig;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 1/16/2019.
 */

public class RV_BanksoalShelvesAdapter extends RecyclerView.Adapter<RV_BanksoalShelvesAdapter.ViewHolder> {

    private ArrayList<BanksoalShelves> banksoalShelves;
    private Context context;

    public RV_BanksoalShelvesAdapter(ArrayList<BanksoalShelves> banksoalShelves, Context context) {
        this.banksoalShelves = banksoalShelves;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banksoal_shelves, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final BanksoalShelves banksoalShelf = banksoalShelves.get(position);

        holder.task_id = banksoalShelves.get(position).getTask_id();
        holder.task_class = banksoalShelves.get(position).getClasses();
        holder.banksoal_title.setText(banksoalShelf.getName());
        holder.task_title = banksoalShelves.get(position).getName();
        holder.banksoal_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesConfig.getInstance(context).saveTaskVar(banksoalShelf);
                Intent intent = new Intent(context, TaskActivity.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return banksoalShelves.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        int task_id;
        int task_class;
        public TextView banksoal_title;
        public CardView banksoal_item;
        String task_title;

        public ViewHolder(View itemView) {
            super(itemView);

            banksoal_title = (TextView) itemView.findViewById(R.id.title_soal);
            banksoal_item = (CardView) itemView.findViewById(R.id.cardTest);
        }
    }

}
