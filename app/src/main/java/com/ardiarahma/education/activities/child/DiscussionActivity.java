package com.ardiarahma.education.activities.child;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.adapters.DiscussionAdapter;
import com.ardiarahma.education.models.Task;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.responses.ResponseTask;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DiscussionActivity extends AppCompatActivity {

    private ArrayList<Task> tasks;
    private DiscussionAdapter adapter;
    RecyclerView rv;

    ProgressDialog loading;
    Token auth = PreferencesConfig.getInstance(this).getToken();
    String token = "Bearer " + auth.getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        rv = findViewById(R.id.rv_discussion);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DiscussionAdapter(DiscussionActivity.this, tasks);

        loading = ProgressDialog.show(this, null, "Please wait...",true, false);

        Intent intent = getIntent();
        final int task_id = intent.getIntExtra("task_id", 0);
        int classes = intent.getIntExtra("task_class", 0);

        Call<ResponseTask> call = RetrofitClient
                .getInstance()
                .getApi()
                .taskmaster_task(token, task_id, classes);

        call.enqueue(new Callback<ResponseTask>() {
            @Override
            public void onResponse(Call<ResponseTask> call, Response<ResponseTask> response) {
                loading.dismiss();
                ResponseTask responseTask = response.body();
                Log.d("TAG", "Response " + response.body());
                if (response.isSuccessful()){
                    if (responseTask.getStatus().equals("success")){
                        Log.i("debug", "onResponse : SUCCESSFUL");
                        tasks = responseTask.getTasks();
                        adapter = new DiscussionAdapter(DiscussionActivity.this, tasks);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(
                                DiscussionActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv.setLayoutManager(layoutManager);
                        rv.setHasFixedSize(true);
                        rv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else {
                        Log.i("debug", "onResponse : FAILED");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseTask> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                Toast.makeText(DiscussionActivity.this, "Kesalahan terjadi.", Toast.LENGTH_LONG).show();
            }
        });

    }

}
