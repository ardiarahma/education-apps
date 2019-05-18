package com.ardiarahma.education.activities.child;

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
import com.ardiarahma.education.adapters.BanksoalViewPagerAdapter;
import com.ardiarahma.education.fragments.FragmentBanksoalSubject7;
import com.ardiarahma.education.fragments.FragmentBanksoalSubject8;
import com.ardiarahma.education.fragments.FragmentBanksoalSubject9;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.models.responses.ResponseLog;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BanksoalActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager banksoalViewPager;
    private BanksoalViewPagerAdapter adapter;
    private TextView tvUserid, tvFitur;

    User user = PreferencesConfig.getInstance(this).getUser();
    Token auth = PreferencesConfig.getInstance(this).getToken();

    String token = "Bearer " + auth.getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banksoal);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_banksoal);
        banksoalViewPager = (ViewPager) findViewById(R.id.viewPager_class);
        adapter = new BanksoalViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentBanksoalSubject7(), "VII");
        adapter.AddFragment(new FragmentBanksoalSubject8(), "VIII");
        adapter.AddFragment(new FragmentBanksoalSubject9(), "IX");

        banksoalViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(banksoalViewPager);

        tvUserid = findViewById(R.id.userid);
        tvFitur = findViewById(R.id.fitur);

        tvUserid.setText(String.valueOf(user.getId()));
        tvFitur.setText("Bank Soal");

        ImageButton toolbar_banksoal = (ImageButton) findViewById(R.id.toolbar_banksoal);
        toolbar_banksoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BanksoalActivity.this, EbookActivity.MainActivity.class);
                startActivity(intent);
            }
        });
    }

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
                .log_task(token, user_id, fitur);

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
                Toast.makeText(BanksoalActivity.this, "Kesalahan terjadi. Silakan coba beberapa saat lagi.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
