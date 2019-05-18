package com.ardiarahma.education.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.adapters.RV_BanksoalSoalAdapter;
import com.ardiarahma.education.models.BanksoalSoal;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.models.responses.ResponseTask;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BanksoalTestActivity extends AppCompatActivity {

    private RecyclerView rv_soal;
    private RecyclerView.Adapter adapter;
    private ArrayList<BanksoalSoal> banksoalSoals;

    CardView soal_card;
    TextView soal_title;
    RadioGroup jawaban_group;
    RadioButton optionA, optionB, optionC, optionD, optionE;
    ImageView soal_img;

    TextView task_title, toolbar_title;

    ProgressDialog loading;
    User user = PreferencesConfig.getInstance(this).getUser();
    Token auth = PreferencesConfig.getInstance(this).getToken();
    String token = "Bearer " + auth.getToken();

//    Intent intent = getIntent();
//    String judul = intent.getStringExtra("task_title");
    String judul = "ini judul";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banksoal_test);

        rv_soal = findViewById(R.id.rv_pertanyaan);
        adapter = new RV_BanksoalSoalAdapter(banksoalSoals, this);

        task_title = findViewById(R.id.title_soal);



//        Intent intent = getIntent();
//        String judul = intent.getStringExtra("task_title");
//        task_title.setText(judul);
//        toolbar_title.setText(judul);
//
//        ImageButton toolbar_banksoal_shelves = (ImageButton) findViewById(R.id.toolbar_banksoal_shelves);
//        toolbar_banksoal_shelves.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(BanksoalTestActivity.this, BanksoalShelvesActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        startTask();
        task();
    }

    public void startTask(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(judul);
        alertDialog.setMessage("Mulai sekarang?");
        alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });
        alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                task();
            }
        });
    }

    public void task(){
        loading = ProgressDialog.show(this, null, "Please wait...",true, false);

        Intent intent = getIntent();
        int task_id = intent.getIntExtra("task_id", 0);
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
                        banksoalSoals = responseTask.getBanksoalSoals();
                        adapter = new RV_BanksoalSoalAdapter(banksoalSoals, BanksoalTestActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BanksoalTestActivity.this, LinearLayoutManager.VERTICAL,false);
                        rv_soal.setAdapter(adapter);
                        rv_soal.setHasFixedSize(true);
                        rv_soal.setLayoutManager(linearLayoutManager);
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
                Toast.makeText(BanksoalTestActivity.this, "Kesalahan terjadi. Silakan coba beberapa saat lagi.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
