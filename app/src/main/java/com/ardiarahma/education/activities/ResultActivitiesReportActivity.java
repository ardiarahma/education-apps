package com.ardiarahma.education.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.educationapplication.R;
import com.ardiarahma.educationapplication.adapters.RV_LogActivityReportAdapter;
import com.ardiarahma.educationapplication.models.Log_detail;
import com.ardiarahma.educationapplication.models.Token;
import com.ardiarahma.educationapplication.models.response.ResponseLogActivityReport;
import com.ardiarahma.educationapplication.network.PreferencesConfig;
import com.ardiarahma.educationapplication.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivitiesReportActivity extends AppCompatActivity {

    private RecyclerView rv_actlogs;
    private RV_LogActivityReportAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<Log_detail> log_details;
//    private ArrayList<Siswa> siswas;

    Token auth = PreferencesConfig.getInstance(ResultActivitiesReportActivity.this).getToken();
    String token = "Bearer " + auth.getToken();
    ProgressDialog loading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_activities_report);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        rv_actlogs = findViewById(R.id.rv_actlogs);
        adapter = new RV_LogActivityReportAdapter(ResultActivitiesReportActivity.this, log_details);

        loading = ProgressDialog.show(ResultActivitiesReportActivity.this, null, "Please wait...",true, false);

        Intent intent = getIntent();
        int id = intent.getIntExtra("childId", 0);
        String name = intent.getStringExtra("childName");

        TextView title_toolbar = (TextView) findViewById(R.id.toolbar_title);
        title_toolbar.setText(name);

//        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                log();
//            }
//        });


        Call<ResponseLogActivityReport> call = RetrofitClient
                .getInstance()
                .getApi()
                .log_report(token, id);

        call.enqueue(new Callback<ResponseLogActivityReport>() {
            @Override
            public void onResponse(Call<ResponseLogActivityReport> call, Response<ResponseLogActivityReport> response) {
                loading.dismiss();
                ResponseLogActivityReport responseLogActivityReport = response.body();
                Log.d("TAG", "Response" + response.body());
                if (response.isSuccessful()){
                    if (responseLogActivityReport.getStatus().equals("success")){
                        Log.i("debug", "onResponse : SUCCESSFUL");
                        loading.dismiss();
                        log_details = responseLogActivityReport.getLog_detail();
                        adapter = new RV_LogActivityReportAdapter(ResultActivitiesReportActivity.this, log_details);
                        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        rv_actlogs.setLayoutManager(linearLayout);
                        rv_actlogs.setHasFixedSize(true);
                        rv_actlogs.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
//                        swipeRefreshLayout.setRefreshing(false);
                    }else {
                        Log.i("debug", "onResponse: FAILED");
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Gagal dalam mengambil data laporan", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogActivityReport> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Kesalahan terjadi. Silakan coba beberapa saat lagi.", Toast.LENGTH_LONG).show();
            }
        });

        ImageButton toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

//    public void log(){
//
//    }
}
