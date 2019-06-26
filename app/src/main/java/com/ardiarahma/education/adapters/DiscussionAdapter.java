package com.ardiarahma.education.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.activities.parent.ParentDetailsChildActivity;
import com.ardiarahma.education.activities.parent.ParentEditChildActivity;
import com.ardiarahma.education.models.Siswa;
import com.ardiarahma.education.models.Task;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.responses.ResponseDelete;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Windows 10 on 2/9/2019.
 */

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.ViewHolder> {

    private List<Task> tasks;
    Context context;

    public DiscussionAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public DiscussionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discussion, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final DiscussionAdapter.ViewHolder holder, final int position) {
        holder.question.setText(tasks.get(position).getSoal());
        holder.kunjaw.setText(tasks.get(position).getJawaban());
        holder.discussion.setText(tasks.get(position).getPembahasan());

        Intent intent = Intent.getIntent();

        };

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
//        int user_id;
//        int id;
//        int kelas;
        String ans;
        ArrayList<String> answersArray = new ArrayList<String>();
        private TextView question, your_ans, kunjaw, discussion;

        Token auth = PreferencesConfig.getInstance(context).getToken();
        final String token = "Bearer " + auth.getToken();

        public ViewHolder(final View view){
            super(view);
            question = itemView.findViewById(R.id.que);
            your_ans = itemView.findViewById(R.id.ur_ans);
            kunjaw = itemView.findViewById(R.id.kunjaw);
            discussion = itemView.findViewById(R.id.discussion);

        }
    }




}
