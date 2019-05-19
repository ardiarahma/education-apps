package com.ardiarahma.education.activities.parent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.responses.ResponseDelete;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import retrofit2.Callback;
import retrofit2.Response;

public class ParentDeleteChildActivity extends AppCompatActivity {

    Button ya, tidak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_child_delete);

        ya = findViewById(R.id.YA_hapus);
        tidak = findViewById(R.id.TIDAK_hapus);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("childId", 0);

        Token auth = PreferencesConfig.getInstance(this).getToken();
        final String token = "Bearer " + auth.getToken();

        ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofit2.Call<ResponseDelete> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .deleteanak(token, id);

                call.enqueue(new Callback<ResponseDelete>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseDelete> call, Response<ResponseDelete> response) {
                        ResponseDelete responseDelete = response.body();
                        Log.d("TAG", "Response " + response.body());
                        if (response.isSuccessful()){
                            if (responseDelete.getStatus().equals("success")){
                                Log.i("debug", "Response success");
                                Toast.makeText(ParentDeleteChildActivity.this, responseDelete.getMessage(), Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }
                        } else {
                            Log.i("debug", "FAILED");
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseDelete> call, Throwable t) {
                        Toast.makeText(ParentDeleteChildActivity.this, "Gagal menghapus akun", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}
