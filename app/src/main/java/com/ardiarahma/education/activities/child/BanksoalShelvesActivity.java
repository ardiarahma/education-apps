package com.ardiarahma.education.activities.child;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.adapters.RV_BanksoalShelvesAdapter;
import com.ardiarahma.education.models.BanksoalShelves;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.responses.ResponseBanksoal;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BanksoalShelvesActivity extends AppCompatActivity {

    private RecyclerView rv_banksoal;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    LinearLayout noData, fillData;

    private ArrayList<BanksoalShelves> banksoalShelves;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banksoal_shelves);

        noData = findViewById(R.id.layoutEmpty);
        fillData = findViewById(R.id.layoutFill);
        rv_banksoal = findViewById(R.id.rv_banksoal);
        rv_banksoal.setHasFixedSize(true);
        rv_banksoal.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        loading = ProgressDialog.show(BanksoalShelvesActivity.this, null, "Please wait...",true, true);

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
                        adapter = new RV_BanksoalShelvesAdapter(banksoalShelves,
                                BanksoalShelvesActivity.this);
                        layoutManager = new LinearLayoutManager(
                                BanksoalShelvesActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_banksoal.setLayoutManager(layoutManager);
                        rv_banksoal.setHasFixedSize(true);
                        rv_banksoal.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else {
                        Log.i("debug", "onResponse: FAILED");
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Gagal dalam mengambil data buku",
                                Toast.LENGTH_LONG).show();
                    }
                    checklist();
                }
            }
            @Override
            public void onFailure(Call<ResponseBanksoal> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                Toast.makeText(BanksoalShelvesActivity.this, "Kesalahan terjadi. Silakan coba beberapa saat lagi.",
                        Toast.LENGTH_LONG).show();
            }
        });

        ImageButton toolbar_banksoal_shelves = (ImageButton) findViewById(R.id.toolbar_banksoal_shelves);
        toolbar_banksoal_shelves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BanksoalShelvesActivity.this, BanksoalActivity.class);
                startActivity(intent);
            }
        });
    }

    public void checklist(){
        if (layoutManager.getItemCount() == 0){
            fillData.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }else{
            noData.setVisibility(View.GONE);
            fillData.setVisibility(View.VISIBLE);
        }
    }

}
