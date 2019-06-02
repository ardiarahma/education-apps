package com.ardiarahma.education.activities.parent;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.models.responses.ResponseLogin;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentProfileUpdateActivity extends AppCompatActivity {

    ImageButton toolbar_profile;
    EditText etName, etEmail, etUsername;
    Button save_update;
    Context context = this;

    User user = PreferencesConfig.getInstance(ParentProfileUpdateActivity.this).getUser();
    Token auth = PreferencesConfig.getInstance(ParentProfileUpdateActivity.this).getToken();
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile_update);

        //=================== BACK BUTTON ==================//
        toolbar_profile = findViewById(R.id.toolbar_profile);
        toolbar_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        etName = findViewById(R.id.namalengkapOrtu);
        etEmail = findViewById(R.id.emailOrtu);
        etUsername = findViewById(R.id.usernameOrtu);

        String name = user.getNama();
        etName.setText(name, TextView.BufferType.EDITABLE);
        String email = user.getEmail();
        etEmail.setText(email, TextView.BufferType.EDITABLE);
        String username = user.getUsername();
        etUsername.setText(username, TextView.BufferType.EDITABLE);

        save_update = findViewById(R.id.simpan_profile);
        save_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Konfirmasi");
                alertDialog.setMessage("Anda yakin ingin mengubah data diri Anda?");
                alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loading = ProgressDialog.show(context, null, "Tunggu sesaat...", true, false);
                        updateOrtu();
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        });
    }

    public void updateOrtu(){
        String token = "Bearer " + auth.getToken();
        int user_id = user.getId();

        String name = etName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (name.isEmpty()) {
            loading.dismiss();
            etName.setError("Nama harus diisi");
            etName.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            loading.dismiss();
            etUsername.setError("Username harus diisi");
            etUsername.requestFocus();
            return;
        }

        if (username.length() < 4) {
            loading.dismiss();
            etUsername.setError("Username harus berisi minimal 4 karakter");
            etUsername.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            loading.dismiss();
            etEmail.setError("Email harus diisi");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loading.dismiss();
            etName.setError("Masukkan email yang valid");
            etName.requestFocus();
            return;
        }

        Call<ResponseLogin> call = RetrofitClient
                .getInstance()
                .getApi()
                .updateOrtu(token, user_id, name, email, username);

        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                ResponseLogin responseLogin = response.body();
                if (response.isSuccessful()){
                    if (responseLogin.getStatus().equals("success")){
                        Log.i("debug", "onResponse : SUCCESSFUL");
                        loading.dismiss();
                        PreferencesConfig.getInstance(context).saveUser(responseLogin.getUser());
                        Toast.makeText(context, "Profil berhasil diubah", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }else {
                        Log.i("debug", "onResponse : FAILED");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                Toast.makeText(context, "Kesalahan terjadi. Silakan coba beberapa saat lagi.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
