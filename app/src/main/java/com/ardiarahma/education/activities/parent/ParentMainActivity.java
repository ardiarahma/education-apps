package com.ardiarahma.education.activities.parent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.educationapplication.R;
import com.ardiarahma.educationapplication.fragments.FragmentParentActivityReport;
import com.ardiarahma.educationapplication.fragments.FragmentParentChild;
import com.ardiarahma.educationapplication.fragments.FragmentParentHome;
import com.ardiarahma.educationapplication.fragments.FragmentParentLogout;
import com.ardiarahma.educationapplication.fragments.FragmentParentProfile;
import com.ardiarahma.educationapplication.fragments.FragmentParentStudyReport;
import com.ardiarahma.educationapplication.models.User;
import com.ardiarahma.educationapplication.network.PreferencesConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_parent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_parent);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_parent);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_parent);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        User user = PreferencesConfig.getInstance(this).getUser();
        TextView header_name = (TextView) headerView.findViewById(R.id.name_header);
        TextView header_email = (TextView) headerView.findViewById(R.id.email_header);
        header_name.setText(user.getNama());
        header_email.setText(user.getEmail());

        displaySelectedScreen(R.id.nav_home_parent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_parent);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public void displaySelectedScreen(int id){

        Fragment fragment = null;

        switch (id){
            case R.id.nav_home_parent:
                fragment = new FragmentParentHome();
                break;
            case R.id.nav_profile_parent:
                fragment = new FragmentParentProfile();
                break;
            case R.id.nav_create_user:
                fragment = new FragmentParentChild();
                break;
            case R.id.nav_activity_report:
                fragment = new FragmentParentActivityReport();
                break;
            case R.id.nav_study_report:
                fragment = new FragmentParentStudyReport();
                break;
            case R.id.logout_parent:
                fragment = new FragmentParentLogout();
                break;
        }

        if (fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.screen_area_parent, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_parent);
        drawer.closeDrawer(GravityCompat.START);

    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedScreen(id);

        return true;
    }

    public static class ResultActivitiesReportActivity extends AppCompatActivity {

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

    public static class ResultStudiesReportActivity extends AppCompatActivity {

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
}
