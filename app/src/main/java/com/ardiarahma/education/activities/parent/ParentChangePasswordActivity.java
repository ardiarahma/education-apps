package com.ardiarahma.education.activities.parent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.models.responses.ResponsePassword;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentChangePasswordActivity extends AppCompatActivity{

    EditText etoldPassword, etpassword, etpassword_confirmation;
    ImageButton toolbar_password;
    Button save_pass;
    CheckBox check_pass;

    User user = PreferencesConfig.getInstance(this).getUser();
    Token auth = PreferencesConfig.getInstance(ParentChangePasswordActivity.this).getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_edit_password);

        etoldPassword = findViewById(R.id.old_pass);
        etpassword = findViewById(R.id.new_pass);
        etpassword_confirmation = findViewById(R.id.confirm_pass);
        check_pass = findViewById(R.id.check_pass);
        toolbar_password = findViewById(R.id.toolbar_password);
        save_pass = findViewById(R.id.simpan_password);

        //===================== BACK BUTTON ====================//
        toolbar_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //==================== SAVE PASSWORD ====================//
        save_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ParentChangePasswordActivity.this);
                alertDialog.setTitle("Konfirmasi");
                alertDialog.setMessage("Anda yakin ingin mengubah password akun Anda?");
                alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String token = "Bearer " + auth.getToken();
                        int user_id = user.getId();

                        String oldPassword = etoldPassword.getText().toString().trim();
                        String password = etpassword.getText().toString().trim();
                        String password_confirmation = etpassword_confirmation.getText().toString().trim();

                        if (oldPassword.isEmpty()) {
                            etoldPassword.setError("Password lama harus diisi");
                            etoldPassword.requestFocus();
                            return;
                        }

                        if (password.isEmpty()) {
                            etpassword.setError("Password baru harus diisi");
                            etpassword.requestFocus();
                            return;
                        }

                        if (!password_confirmation.equals(password)){
                            etpassword_confirmation.setError("Password tidak sama. Mohon isi dengan password yang sesuai");
                        }

                        if (password_confirmation.isEmpty()) {
                            etpassword_confirmation.setError("Password harus diisi");
                            etpassword_confirmation.requestFocus();
                            return;
                        }

                        Call<ResponsePassword> call = RetrofitClient
                                .getInstance()
                                .getApi()
                                .change_pass_ortu(user_id, token, oldPassword, password, password_confirmation);
                        call.enqueue(new Callback<ResponsePassword>() {
                            @Override
                            public void onResponse(Call<ResponsePassword> call, Response<ResponsePassword> response) {
                                ResponsePassword responsePassword = response.body();
                                if (response.isSuccessful()){
                                    if (responsePassword.getStatus().equals("success")){
                                        Log.i("debug", "onResponse : SUCCESSFUL");
                                        Toast.makeText(ParentChangePasswordActivity.this,
                                                "Password berhasil diganti", Toast.LENGTH_LONG).show();
                                        onBackPressed();
                                    }else {
                                        Log.i("debug", "onResponse : FAILED");
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponsePassword> call, Throwable t) {
                                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                                Toast.makeText(ParentChangePasswordActivity.this,
                                        "Kesalahan terjadi. Silakan coba beberapa saat lagi.",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        });

    }
}
