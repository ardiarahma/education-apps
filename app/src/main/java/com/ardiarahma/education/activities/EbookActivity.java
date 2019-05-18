package com.ardiarahma.education.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
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
}
