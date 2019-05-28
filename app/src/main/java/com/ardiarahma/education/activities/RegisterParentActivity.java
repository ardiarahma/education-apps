package com.ardiarahma.education.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.models.responses.ResponseRegisterOrtu;
import com.ardiarahma.education.networks.RetrofitClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterParentActivity extends AppCompatActivity {

    TextInputEditText etNama, etUsername, etEmail, etPassword;
    Context mContext;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_parent);

        etNama = findViewById(R.id.namaOrtu);
        etUsername = findViewById(R.id.usernameOrtu);
        etEmail = findViewById(R.id.emailOrtu);
        etPassword = findViewById(R.id.passwordOrtu);

        CheckBox cbPassword = findViewById(R.id.check_pass);
        cbPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        mContext = this;

        Button btnDaftarOrtu = findViewById(R.id.daftar_ortu_btn);
        btnDaftarOrtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Tunggu sesaat...", true, false);
                createOrangtua();
            }
        });

    }


    public void createOrangtua() {
        String name = etNama.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty()) {
            loading.dismiss();
            etNama.setError("Nama harus diisi");
            etNama.requestFocus();
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
            etNama.setError("Masukkan email yang valid");
            etNama.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            loading.dismiss();
            etPassword.setError("Password harus diisi");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            loading.dismiss();
            etPassword.setError("Password harus berisi minimal 8 karakter");
            etPassword.requestFocus();
            return;
        }

        Call<ResponseRegisterOrtu> call = RetrofitClient
                .getInstance()
                .getApi()
                .createOrangtua(name, username, email, password);

        call.enqueue(new Callback<ResponseRegisterOrtu>() {
            @Override
            public void onResponse(Call<ResponseRegisterOrtu> call, Response<ResponseRegisterOrtu> response) {
                ResponseRegisterOrtu responseRegisterOrtu = response.body();
                if (response.isSuccessful()){
                    if (responseRegisterOrtu.getStatus().equals("success")){
                        Log.i("debug", "onResponse: SUCCESSFUL");
                        loading.dismiss();
                        Toast.makeText(mContext, responseRegisterOrtu.getStatus(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterParentActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        Log.i("debug", "onResponse: FAILED");
                        loading.dismiss();
                        Toast.makeText(mContext, responseRegisterOrtu.getStatus(), Toast.LENGTH_LONG).show();
                    }
                }else {
                    loading.dismiss();
                    try {
                        JSONObject jsonObjectError = new JSONObject(response.errorBody().string());
                        Toast.makeText(mContext, jsonObjectError.getString("message"), Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseRegisterOrtu> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                Toast.makeText(mContext, "Kesalahan terjadi. Silakan coba beberapa saat lagi.", Toast.LENGTH_LONG).show();
            }
        });

    }
}
