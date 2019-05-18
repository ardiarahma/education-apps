package com.ardiarahma.education.activities.child;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.educationapplication.R;
import com.ardiarahma.educationapplication.adapters.RV_BanksoalShelvesAdapter;
import com.ardiarahma.educationapplication.models.BanksoalShelves;
import com.ardiarahma.educationapplication.models.Token;
import com.ardiarahma.educationapplication.models.response.ResponseBanksoal;
import com.ardiarahma.educationapplication.network.PreferencesConfig;
import com.ardiarahma.educationapplication.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BanksoalShelvesActivity extends AppCompatActivity {

    private RecyclerView rv_banksoal;
    private RecyclerView.Adapter adapter;

    private ArrayList<BanksoalShelves> banksoalShelves;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banksoal_shelves);

        rv_banksoal = findViewById(R.id.rv_banksoal);
        rv_banksoal.setHasFixedSize(true);
        rv_banksoal.setLayoutManager(new LinearLayoutManager(this));

        loading = ProgressDialog.show(BanksoalShelvesActivity.this, null, "Please wait...",true, false);

        Intent intent = getIntent();
        int subjectsId = intent.getIntExtra("subjectsId", 0);
        int classes = intent.getIntExtra("classes", 0);
        String title = intent.getStringExtra("title");

        TextView title_toolbar = findViewById(R.id.title_toolbar);
        title_toolbar.setText(title);

        Token auth = PreferencesConfig.getInstance(BanksoalShelvesActivity.this).getToken();
        String token = "Bearer " + auth.getToken();

        Call<ResponseBanksoal> call = RetrofitClient
                .getInstance()
                .getApi()
                .subsoal(token, subjectsId, classes);

        call.enqueue(new Callback<ResponseBanksoal>() {
            @Override
            public void onResponse(Call<ResponseBanksoal> call, Response<ResponseBanksoal> response) {
                loading.dismiss();
                ResponseBanksoal responseBanksoal = response.body();
                Log.d("TAG", "Response " + response.body());
                if (response.isSuccessful()){
                    if (responseBanksoal.getStatus().equals("success")){
                        Log.i("debug", "onResponse : SUCCESSFUL");
                        loading.dismiss();
                        banksoalShelves = responseBanksoal.getBanksoalShelves();
                        adapter = new RV_BanksoalShelvesAdapter(banksoalShelves, BanksoalShelvesActivity.this);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(BanksoalShelvesActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_banksoal.setLayoutManager(layoutManager);
                        rv_banksoal.setHasFixedSize(true);
                        rv_banksoal.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else {
                        Log.i("debug", "onResponse: FAILED");
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Gagal dalam mengambil data buku", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBanksoal> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                Toast.makeText(BanksoalShelvesActivity.this, "Kesalahan terjadi. Silakan coba beberapa saat lagi.", Toast.LENGTH_LONG).show();
            }
        });

//        banksoalShelves = new ArrayList<>();
////
//        banksoalShelves.add(new BanksoalShelves("Ilmu Cocoklogi untuk Siswa SMP Kelas 7"));
//        banksoalShelves.add(new BanksoalShelves("Ilmu Ternak Lele untuk Siswa SMP Kelas 7"));
//        banksoalShelves.add(new BanksoalShelves("Pendidikan Ternak Lele untuk Siswa SMP Kelas 7"));
//        banksoalShelves.add(new BanksoalShelves("Ilmu Menghilang untuk Siswa SMP Kelas 7"));
//        banksoalShelves.add(new BanksoalShelves("Ilmu Terbakar untuk Siswa SMP Kelas 7"));
//        banksoalShelves.add(new BanksoalShelves("Ilmu Ternak Cupang untuk Siswa SMP Kelas 7"));
//        banksoalShelves.add(new BanksoalShelves("Ilmu Cocoklogi untuk Siswa SMP Kelas 7"));
//        banksoalShelves.add(new BanksoalShelves("Ilmu Cocoklogi untuk Siswa SMP Kelas 7"));
//        banksoalShelves.add(new BanksoalShelves("Ilmu Cocoklogi untuk Siswa SMP Kelas 7"));
//
//        adapter = new RV_BanksoalShelvesAdapter(banksoalShelves, this);
//        rv_banksoal.setAdapter(adapter);
//
        ImageButton toolbar_banksoal_shelves = (ImageButton) findViewById(R.id.toolbar_banksoal_shelves);
        toolbar_banksoal_shelves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BanksoalShelvesActivity.this, BanksoalActivity.class);
                startActivity(intent);
            }
        });
    }




}
