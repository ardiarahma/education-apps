package com.ardiarahma.education.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.educationapplication.R;
import com.ardiarahma.educationapplication.adapters.RV_EbookShelvesAdapter;
import com.ardiarahma.educationapplication.models.EbookShelves;
import com.ardiarahma.educationapplication.models.Token;
import com.ardiarahma.educationapplication.models.response.ResponseEbook;
import com.ardiarahma.educationapplication.network.PreferencesConfig;
import com.ardiarahma.educationapplication.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EbookShelvesActivity extends AppCompatActivity {

    private RecyclerView rv_subject;
    private RV_EbookShelvesAdapter adapter;

    private ArrayList<EbookShelves> ebookShelves;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook_shelves);

        rv_subject = findViewById(R.id.rv_subject);
        adapter = new RV_EbookShelvesAdapter(ebookShelves, EbookShelvesActivity.this);

        loading = ProgressDialog.show(EbookShelvesActivity.this, null, "Please wait...",true, false);

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
                        rv_subject.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                        rv_subject.setHasFixedSize(true);
                        rv_subject.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }else{
                        Log.i("debug", "onResponse: FAILED");
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Gagal dalam mengambil data buku", Toast.LENGTH_LONG).show();
                    }
                }
            }

            //buat get image : http://10.0.2.2/testing-adi/sim-api/public/images/818564468.jpg

            @Override
            public void onFailure(Call<ResponseEbook> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Kesalahan terjadi. Silakan coba beberapa saat lagi.", Toast.LENGTH_LONG).show();
            }
        });




//        ebookShelves = new ArrayList<>();
//        ebookShelves.add(new EbookShelves("Ilmu Cocoklogi untuk Siswa SMP Kelas 7", "https://google.com", R.drawable.default_tab_layout));
//        ebookShelves.add(new EbookShelves("Ilmu Ternak Lele untuk Siswa SMP Kelas 7", "https://google.com", R.drawable.default_tab_layout));
//        ebookShelves.add(new EbookShelves("Pendidikan Ternak Lele untuk Siswa SMP Kelas 7", "https://google.com", R.drawable.default_tab_layout));
//        ebookShelves.add(new EbookShelves("Ilmu Menghilang untuk Siswa SMP Kelas 7", "https://google.com", R.drawable.default_tab_layout));
//        ebookShelves.add(new EbookShelves("Ilmu Terbakar untuk Siswa SMP Kelas 7", "https://google.com", R.drawable.default_tab_layout));
//        ebookShelves.add(new EbookShelves("Ilmu Ternak Cupang untuk Siswa SMP Kelas 7", "https://google.com", R.drawable.default_tab_layout));
//        ebookShelves.add(new EbookShelves("Ilmu Cocoklogi untuk Siswa SMP Kelas 7", "https://google.com", R.drawable.default_tab_layout));
//        ebookShelves.add(new EbookShelves("Ilmu Cocoklogi untuk Siswa SMP Kelas 7", "https://google.com", R.drawable.default_tab_layout));
//        ebookShelves.add(new EbookShelves("Ilmu Cocoklogi untuk Siswa SMP Kelas 7", "https://google.com", R.drawable.default_tab_layout));


//        adapter = new RV_EbookShelvesAdapter(ebookShelves, this);
//        rv_subject.setAdapter(adapter);


        //================================ back to activity sebelumnya =================================================//
        ImageButton toolbar_ebook = (ImageButton) findViewById(R.id.toolbar_shelves);
        toolbar_ebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EbookShelvesActivity.this, EbookActivity.class);
                startActivity(intent);
            }
        });
    }




}
