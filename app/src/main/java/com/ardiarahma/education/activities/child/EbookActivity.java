package com.ardiarahma.education.activities.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.activities.LoginActivity;
import com.ardiarahma.education.adapters.EbookViewPagerAdapter;
import com.ardiarahma.education.fragments.FragmentEbookSubject7;
import com.ardiarahma.education.fragments.FragmentEbookSubject8;
import com.ardiarahma.education.fragments.FragmentEbookSubject9;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.models.responses.ResponseLog;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EbookActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager ebookViewPager;
    private EbookViewPagerAdapter adapter;
    private TextView tvUserid, tvFitur;

    User user = PreferencesConfig.getInstance(this).getUser();
    Token auth = PreferencesConfig.getInstance(this).getToken();

    String token = "Bearer " + auth.getToken();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_ebook);
        ebookViewPager = (ViewPager) findViewById(R.id.viewPager_class);
        adapter = new EbookViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentEbookSubject7(), "VII");
        adapter.AddFragment(new FragmentEbookSubject8(), "VIII");
        adapter.AddFragment(new FragmentEbookSubject9(), "IX");

        ebookViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(ebookViewPager);

        tvUserid = findViewById(R.id.userid);
        tvFitur = findViewById(R.id.fitur);

        tvUserid.setText(String.valueOf(user.getId()));
        tvFitur.setText("Ebook");

        ImageButton toolbar_ebook = (ImageButton) findViewById(R.id.toolbar_ebook);
        toolbar_ebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EbookActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        log();
    }

    public void log(){
        int user_id = Integer.valueOf(tvUserid.getText().toString());
        String fitur = tvFitur.getText().toString().trim();


        Call<ResponseLog> call = RetrofitClient
                .getInstance()
                .getApi()
                .log_ebook(token, user_id, fitur);

        call.enqueue(new Callback<ResponseLog>() {
            @Override
            public void onResponse(Call<ResponseLog> call, Response<ResponseLog> response) {
                ResponseLog responseLog = response.body();
                Log.d("TAG", "Response " + response.body());
                if (response.isSuccessful()){
                    if (responseLog.getStatus().equals("success")){
                        Log.i("debug", "onResponse : SUCCESS");
                        Log.d("TAG", "Response " + responseLog.getResult());
                        Log.d("TAG", "Response " + responseLog.getResult().getMenu().getFitur());
                        Log.d("TAG", "Response " + responseLog.getResult().getMenu().getUser_id());
                    }else{
                        Log.i("debug", "onResponse : FAILED");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLog> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                Toast.makeText(EbookActivity.this, "Kesalahan terjadi. Silakan coba beberapa saat lagi.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static class MainActivity extends AppCompatActivity
            implements NavigationView.OnNavigationItemSelectedListener {

        protected DrawerLayout drawer;
        private FirebaseAnalytics mFirebaseAnalytics;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View headerView = navigationView.getHeaderView(0);

            User user = PreferencesConfig.getInstance(this).getUser();
            TextView header_name = (TextView) headerView.findViewById(R.id.name_header);
            TextView header_email = (TextView) headerView.findViewById(R.id.email_header);
            header_name.setText(user.getNama());
            header_email.setText(user.getEmail());

            displaySelectedScreen(R.id.nav_home);


        }

        @Override
        public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }


        public void displaySelectedScreen(int id){

            Fragment fragment = null;

            switch (id){
                case R.id.nav_home:
                    fragment = new FragmentHome();
                    break;
                case R.id.nav_profile:
                    fragment = new FragmentProfile();
                    break;
                case R.id.logout:
                    fragment = new FragmentLogout();
                    break;
            }

            if (fragment != null){
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.screen_area, fragment);
                ft.commit();
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        public void onStart(){
            super.onStart();
            if (!PreferencesConfig.getInstance(this).isLoggedIn()){
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }
}
