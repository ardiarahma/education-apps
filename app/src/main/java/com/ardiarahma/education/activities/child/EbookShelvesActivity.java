package com.ardiarahma.education.activities.child;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.adapters.RV_EbookShelvesAdapter;
import com.ardiarahma.education.models.EbookShelves;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.responses.ResponseEbook;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EbookShelvesActivity extends AppCompatActivity {

    private RecyclerView rv_subject;
    private RV_EbookShelvesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    LinearLayout noData, fillData;
    private ArrayList<EbookShelves> ebookShelves;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook_shelves);
        noData = findViewById(R.id.layoutEmpty);
        fillData = findViewById(R.id.layoutFill);
        rv_subject = findViewById(R.id.rv_subject);
        adapter = new RV_EbookShelvesAdapter(ebookShelves, EbookShelvesActivity.this);
        loading = ProgressDialog.show(EbookShelvesActivity.this, null, "Please wait...",
                true, true);
        Intent intent = getIntent();
        int subjectsId = intent.getIntExtra("subjectsId", 0);
        int classes = intent.getIntExtra("classes", 0);
        String title = intent.getStringExtra("title");
        TextView title_toolbar = (TextView) findViewById(R.id.toolbar_title);
        title_toolbar.setText(title);
        Token auth = PreferencesConfig.getInstance(EbookShelvesActivity.this).getToken();
        String token = "Bearer " + auth.getToken();
        Call<ResponseEbook> call = RetrofitClient
                .getInstance()
                .getApi()
                .books(token, subjectsId, classes);
        call.enqueue(new Callback<ResponseEbook>() {
            @Override
            public void onResponse(Call<ResponseEbook> call, Response<ResponseEbook> response) {
                loading.dismiss();
                ResponseEbook responseEbook = response.body();
                Log.d("TAG", "Response" + response.body());
                if (response.isSuccessful()){
                    if (responseEbook.getStatus().equals("success")){
                        Log.i("debug", "onResponse: SUCCESSFUL");
                        loading.dismiss();
                        ebookShelves = responseEbook.getEbookShelves();
                        adapter = new RV_EbookShelvesAdapter(ebookShelves, EbookShelvesActivity.this);
                        layoutManager = new LinearLayoutManager(
                                EbookShelvesActivity.this, LinearLayoutManager.VERTICAL,false);
                        rv_subject.setLayoutManager(layoutManager);
                        rv_subject.setHasFixedSize(true);
                        rv_subject.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        Log.i("debug", "onResponse: FAILED");
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Gagal dalam mengambil data buku",
                                Toast.LENGTH_LONG).show();
                    }
                    checklist();
                }
            }
            @Override
            public void onFailure(Call<ResponseEbook> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Kesalahan terjadi. Silakan coba beberapa saat lagi.",
                        Toast.LENGTH_LONG).show();
            }
        });

        ImageButton toolbar_ebook = (ImageButton) findViewById(R.id.toolbar_shelves);
        toolbar_ebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EbookShelvesActivity.this, EbookActivity.class);
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
