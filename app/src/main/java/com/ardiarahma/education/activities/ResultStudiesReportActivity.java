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
import com.ardiarahma.educationapplication.adapters.RV_LogStudyReportAdapter;
import com.ardiarahma.educationapplication.models.LogStudy;
import com.ardiarahma.educationapplication.models.Token;
import com.ardiarahma.educationapplication.models.response.ResponseLogStudyReport;
import com.ardiarahma.educationapplication.network.PreferencesConfig;
import com.ardiarahma.educationapplication.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultStudiesReportActivity extends AppCompatActivity {

    private RecyclerView rv_actlogs;
    private RV_LogStudyReportAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;


    private ArrayList<LogStudy> logStudies;
//    private ArrayList<Siswa> siswas;

    ProgressDialog loading;

    Token auth = PreferencesConfig.getInstance(ResultStudiesReportActivity.this).getToken();
    String token = "Bearer " + auth.getToken();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_activities_report);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        rv_actlogs = findViewById(R.id.rv_actlogs);
        adapter = new RV_LogStudyReportAdapter(ResultStudiesReportActivity.this, logStudies);

        loading = ProgressDialog.show(ResultStudiesReportActivity.this, null, "Please wait...",true, false);

        Intent intent = getIntent();
        int id = intent.getIntExtra("childId", 0);
        String name = intent.getStringExtra("childName");

        TextView title_toolbar = (TextView) findViewById(R.id.toolbar_title);
        title_toolbar.setText(name);

        ImageButton toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Call<ResponseLogStudyReport> call = RetrofitClient
                .getInstance()
                .getApi()
                .log_study_report(token, id);

        call.enqueue(new Callback<ResponseLogStudyReport>() {
            @Override
            public void onResponse(Call<ResponseLogStudyReport> call, Response<ResponseLogStudyReport> response) {
                loading.dismiss();
                ResponseLogStudyReport responseLogStudyReport = response.body();
                Log.d("TAG", "Response" + response.body());
                if (response.isSuccessful()){
                    if (responseLogStudyReport.getStatus().equals("success")){
                        Log.i("debug", "onResponse : SUCCESSFUL");
                        loading.dismiss();
                        logStudies = responseLogStudyReport.getLogStudy();
                        adapter = new RV_LogStudyReportAdapter(ResultStudiesReportActivity.this, logStudies);
                        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        rv_actlogs.setLayoutManager(linearLayout);
                        rv_actlogs.setHasFixedSize(true);
                        rv_actlogs.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else {
                        Log.i("debug", "onResponse: FAILED");
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Gagal dalam mengambil data laporan", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogStudyReport> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Kesalahan terjadi. Silakan coba beberapa saat lagi.", Toast.LENGTH_LONG).show();
            }
        });

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                log();
//            }
//        });

    }

//    public void log(){
//
//    }
}
