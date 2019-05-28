package com.ardiarahma.education.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ardiarahma.education.R;
import com.ardiarahma.education.models.Answers;
import com.ardiarahma.education.models.BanksoalSoal;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 1/21/2019.
 */

public class RV_BanksoalSoalAdapter extends RecyclerView.Adapter<RV_BanksoalSoalAdapter.ViewHolder> {

    Context context;
    ArrayList<BanksoalSoal> banksoalSoals;

    private RadioButton lastChecked = null;

    public RV_BanksoalSoalAdapter(ArrayList<BanksoalSoal> banksoalSoals, Context context) {
        this.banksoalSoals = banksoalSoals;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_soal, parent, false);
        ViewHolder vHolder = new ViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.soal_title.setText(banksoalSoals.get(position).getSoal());


        
//        holder.jawaban_group = banksoalSoals.get(position).getTaskAnswers();
//        holder.jawaban_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton checked_rb = (RadioButton) group.findViewById(checkedId);
//                if (lastChecked != null){
//                    lastChecked.setChecked(false);
//                }
////                if (checkedId == R.id.option_A && lastChecked != null){
////                    lastChecked.setChecked(false);
////                }else if (checkedId == R.id.option_B && lastChecked != null){
////                    lastChecked.setChecked(false);
////                }else if (checkedId == R.id.option_C && lastChecked != null){
////                    lastChecked.setChecked(false);
////                }else if (checkedId == R.id.option_D && lastChecked != null){
////                    lastChecked.setChecked(false);
////                }else if (checkedId == R.id.option_E && lastChecked != null){
////                    lastChecked.setChecked(false);
////                }
//
//                //store the clicked radiobutton
//                lastChecked = checked_rb;
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return banksoalSoals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CardView soal_card;
        TextView soal_title;
//        final RadioGroup jawaban_group;
//        RadioButton optionA, optionB, optionC, optionD, optionE;

        public ViewHolder(View itemView) {
            super(itemView);
            soal_card = (CardView) itemView.findViewById(R.id.cardSoal);
            soal_title = (TextView) itemView.findViewById(R.id.soal);
//            jawaban_group = (RadioGroup) itemView.findViewById(R.id.radiogroup_soal);
//            optionA = (RadioButton) itemView.findViewById(R.id.option_A);
//            optionB = (RadioButton) itemView.findViewById(R.id.option_B);
//            optionC = (RadioButton) itemView.findViewById(R.id.option_C);
//            optionD = (RadioButton) itemView.findViewById(R.id.option_D);
//            optionE = (RadioButton) itemView.findViewById(R.id.option_E);
        }
    }
}
