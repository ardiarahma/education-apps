package com.ardiarahma.education.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.activities.child.MainActivity;
import com.ardiarahma.education.activities.parent.ForgetPasswordActivity;
import com.ardiarahma.education.activities.parent.ParentMainActivity;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.models.responses.ResponseLogin;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView daftar;
    EditText tilEmail, tilPassword;
    Context mContext;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilEmail = findViewById(R.id.email);
        tilPassword = findViewById(R.id.password);

        daftar = findViewById(R.id.daftar);
//        SpannableString content = new SpannableString("Daftar disini!");
//        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        CheckBox cbPassword = findViewById(R.id.check_pass);
        cbPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tilPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    tilPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        mContext = this;

        final Button btnMasuk = findViewById(R.id.masuk);
        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Tunggu sesaat...", true, false);
                loginUser();
            }
        });

        tilPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && event.getKeyCode() ==  KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)){
                    Log.i("TAG", "enter pressed");
                    btnMasuk.performClick();
                }
                return false;
            }
        });
    }

    public void loginUser(){
        final String email = tilEmail.getText().toString().trim();
        String password = tilPassword.getText().toString().trim();

        final String type = "Orang Tua";

        if (email.isEmpty()) {
            loading.dismiss();
            tilEmail.setError("Email harus diisi");
            tilEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loading.dismiss();
            tilEmail.setError("Masukkan email yang benar");
            tilEmail.requestFocus();
            return;
        }

//        if (email.length() < 4) {
//            loading.dismiss();
//            tilUsername.setError("Username harus berisi minimal 4 karakter");
//            tilUsername.requestFocus();
//            return;
//        }

        if (password.isEmpty()) {
            loading.dismiss();
            tilPassword.setError("Password harus diisi");
            tilPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            loading.dismiss();
            tilPassword.setError("Password harus berisi minimal 6 karakter");
            tilPassword.requestFocus();
            return;
        }

        Call<ResponseLogin> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(email, password);

        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                ResponseLogin loginResponse = response.body();
                if (response.isSuccessful()){
                    if (loginResponse.getStatus().equals("success")){
                        Log.i("debug", "onResponse: SUCCESSFUL");
                        loading.dismiss();

                        //multi-level login
                        if (loginResponse.getUser().getType().equals("Orang tua")){
                            PreferencesConfig.getInstance(LoginActivity.this).saveUser(loginResponse.getUser());
                            PreferencesConfig.getInstance(LoginActivity.this).saveToken(loginResponse.getToken());
                            Intent intent = new Intent(LoginActivity.this, ParentMainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else {
                            PreferencesConfig.getInstance(LoginActivity.this).saveUser(loginResponse.getUser());
                            PreferencesConfig.getInstance(LoginActivity.this).saveToken(loginResponse.getToken());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }else {
                        Log.i("debug", "onResponse: FAILED");
                        loading.dismiss();
                        Toast.makeText(mContext, loginResponse.getStatus(), Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                Toast.makeText(mContext, "Kesalahan terjadi. Silakan coba beberapa saat lagi.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onStart(){
        super.onStart();
        if (PreferencesConfig.getInstance(this).isLoggedIn()){
            User user = PreferencesConfig.getInstance(this).getUser();
            //ngecek usernya
            if (user.getType().equals("Orang tua")){

                Intent intent = new Intent(this, ParentMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        }
    }

    public void daftar(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterParentActivity.class);
        startActivity(intent);
    }
}
