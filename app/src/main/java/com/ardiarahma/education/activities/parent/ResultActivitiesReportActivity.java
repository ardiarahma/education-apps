package com.ardiarahma.education.activities.parent;

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

import com.ardiarahma.education.R;
import com.ardiarahma.education.adapters.RV_LogActivityReportAdapter;
import com.ardiarahma.education.models.Log_detail;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.responses.ResponseLogActivityReport;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Windows 10 on 5/19/2019.
 */

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
        setContentView(R.layout.activity_result_report);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        rv_actlogs = findViewById(R.id.rv_actlogs);
        adapter = new RV_LogActivityReportAdapter(ResultActivitiesReportActivity.this, log_details);
        loading = ProgressDialog.show(ResultActivitiesReportActivity.this, null,
                "Please wait...",true, true);
        Intent intent = getIntent();
        String name = intent.getStringExtra("childName");
        TextView title_toolbar = (TextView) findViewById(R.id.toolbar_title);
        title_toolbar.setText(name);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                log();
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

    @Override
    protected void onResume() {
        super.onResume();
        log();
    }

    public void log(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("childId", 0);
        Call<ResponseLogActivityReport> call = RetrofitClient
                .getInstance()
                .getApi()
                .log_report(token, id);
        call.enqueue(new Callback<ResponseLogActivityReport>() {
            @Override
            public void onResponse(Call<ResponseLogActivityReport> call, Response<ResponseLogActivityReport> response) {
                loading.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                ResponseLogActivityReport responseLogActivityReport = response.body();
                Log.d("TAG", "Response" + response.body());
                if (response.isSuccessful()){
                    if (responseLogActivityReport.getStatus().equals("success")){
                        Log.i("debug", "onResponse : SUCCESSFUL");
                        loading.dismiss();
                        log_details = responseLogActivityReport.getLog_detail();
                        adapter = new RV_LogActivityReportAdapter(ResultActivitiesReportActivity.this, log_details);
                        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext(),
                                LinearLayoutManager.VERTICAL, false);
                        rv_actlogs.setLayoutManager(linearLayout);
                        rv_actlogs.setHasFixedSize(true);
                        rv_actlogs.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else {
                        Log.i("debug", "onResponse: FAILED");
                        loading.dismiss();
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(), "Gagal dalam mengambil data laporan",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseLogActivityReport> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(), "Kesalahan terjadi. Silakan coba beberapa saat lagi.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
