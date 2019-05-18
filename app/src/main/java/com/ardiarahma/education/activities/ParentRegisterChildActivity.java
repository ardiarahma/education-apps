package com.ardiarahma.education.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.models.District;
import com.ardiarahma.education.models.Province;
import com.ardiarahma.education.models.Regency;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.models.responses.ResponseDistrict;
import com.ardiarahma.education.models.responses.ResponseProvince;
import com.ardiarahma.education.models.responses.ResponseRegency;
import com.ardiarahma.education.models.responses.ResponseRegisterAnak;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentRegisterChildActivity extends AppCompatActivity {

    EditText etNama, etEmail, etUsername, etPassword, etSekolah;
    Spinner spGender, spProvince, spCity, spDistrict, spClass;
    TextView prov, city, kecamatan;
    Context context;
    ProgressDialog loading;

    ArrayList<Province> provinces;
    ArrayAdapter<Province> adapterProvinces;

    ArrayList<Regency> regencies;
    ArrayAdapter<Regency> adapterRegencies;

    ArrayList<District> districts;
    ArrayAdapter<District> adapterDistrict;

    User user = PreferencesConfig.getInstance(this).getUser();
    Token auth = PreferencesConfig.getInstance(this).getToken();
    String token = "Bearer " + auth.getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_child);

        context = this;
        etNama = findViewById(R.id.namaSiswa);
        etEmail = findViewById(R.id.emailSiswa);
        etUsername = findViewById(R.id.usernameSiswa);
        etPassword = findViewById(R.id.passwordSiswa);
        spGender = findViewById(R.id.spinner_gender);
        spProvince = findViewById(R.id.spinner_province);
        spCity = findViewById(R.id.spinner_city);
        spDistrict = findViewById(R.id.spinner_kecamatan);
        etSekolah = findViewById(R.id.namaSekolah);
        spClass = findViewById(R.id.spinner_class);
        prov = findViewById(R.id.prov);
        city = findViewById(R.id.city);
        kecamatan = findViewById(R.id.district);

        //=================== CHECK PASSWORD ===================//
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

        //=================== REGISTER BUTTON ==================//
        Button btnDaftarAnak = findViewById(R.id.daftar_anak_btn);
        btnDaftarAnak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(context, null, "Tunggu sesaat...", true, false);
                createAnak();
            }
        });


        //=================== BACK BUTTON ==================//
        ImageButton toolbar_regis = (ImageButton) findViewById(R.id.toolbar_regis);
        toolbar_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //=================== SPINNER ===================//
        //Gender
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spGender.setAdapter(adapter);
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = String.valueOf(parent.getItemIdAtPosition(position)).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Class
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.kelas, R.layout.support_simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spClass.setAdapter(adapter1);
        spClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = String.valueOf(parent.getItemAtPosition(position)).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Province
        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {
                Province provinces = (Province) parent.getSelectedItem();
                int province_id = provinces.getId();

                prov.setText(String.valueOf(province_id));

                //ambil data kota
                Call<ResponseRegency> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .regency(token, province_id);

                call.enqueue(new Callback<ResponseRegency>() {
                    @Override
                    public void onResponse(Call<ResponseRegency> call, Response<ResponseRegency> response) {
                        if (response.isSuccessful()){
                            ResponseRegency responseRegency = response.body();
                            if (responseRegency.getStatus().equals("success")){
                                regencies = responseRegency.getRegency();
                                adapterRegencies = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, regencies);
                                adapterRegencies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spCity.setAdapter(adapterRegencies);

                                spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        Regency regency = (Regency) parent.getSelectedItem();
                                        int regency_id = regency.getId();

                                        city.setText(String.valueOf(regency_id));

                                        //ambil data kecamatan
                                        Call<ResponseDistrict> call2 = RetrofitClient
                                                .getInstance()
                                                .getApi()
                                                .district(token, regency_id);

                                        call2.enqueue(new Callback<ResponseDistrict>() {
                                            @Override
                                            public void onResponse(Call<ResponseDistrict> call, Response<ResponseDistrict> response) {
                                                if (response.isSuccessful()){
                                                    ResponseDistrict responseDistrict = response.body();
                                                    if (responseDistrict.getStatus().equals("success")){
                                                        districts = responseDistrict.getDistricts();
                                                        adapterDistrict = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, districts);
                                                        adapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                        spDistrict.setAdapter(adapterDistrict);

                                                        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                            @Override
                                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                District district = (District) parent.getSelectedItem();
                                                                int district_id = district.getId();
                                                                kecamatan.setText(String.valueOf(district_id));
                                                            }

                                                            @Override
                                                            public void onNothingSelected(AdapterView<?> parent) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseDistrict> call, Throwable t) {
                                                Toast.makeText(context, "Gagal mengambil data kecamatan", Toast.LENGTH_LONG).show();
                                                Log.d("TAG", "Response" + t.toString());
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseRegency> call, Throwable t) {
                        Toast.makeText(context, "Gagal mengambil data kota", Toast.LENGTH_LONG).show();
                        Log.d("TAG", "Response" + t.toString());
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        loading = ProgressDialog.show(context, null, "Tunggu sesaat..", true, false);
        loadProvince();
    }

    //============ mengambil array provinces ==========//
    private void loadProvince() {
        Call<ResponseProvince> call = RetrofitClient
                .getInstance()
                .getApi()
                .province(token);

        call.enqueue(new Callback<ResponseProvince>() {
            @Override
            public void onResponse(Call<ResponseProvince> call, Response<ResponseProvince> response) {
                if (response.isSuccessful()){
                    ResponseProvince responseProvince = response.body();
                    if (responseProvince.getStatus().equals("success")){
//                        loading.dismiss();
                        provinces = responseProvince.getProvince();
                        Log.d("TAG", "Response" + responseProvince.getProvince());
                        adapterProvinces = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, provinces);
                        adapterProvinces.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spProvince.setAdapter(adapterProvinces);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseProvince> call, Throwable t) {
//                loading.dismiss();
                Toast.makeText(context, "Gagal mengambil data provinsi", Toast.LENGTH_LONG).show();
                Log.d("TAG", "Response" + t.toString());
            }
        });
    }

    public void createAnak(){
        String name = etNama.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String jenis_kelamin = spGender.getSelectedItem().toString().trim();
        String password = etPassword.getText().toString().trim();
        int orangtua_id = user.getId_user();
        int province_id = Integer.parseInt(prov.getText().toString());
        int regency_id = Integer.parseInt(city.getText().toString());
        int district_id = Integer.parseInt(kecamatan.getText().toString());
        String school = etSekolah.getText().toString().trim();
        int classes = Integer.valueOf(spClass.getSelectedItem().toString());

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

        if (school.isEmpty()){
            loading.dismiss();
            etSekolah.setError("Nama sekolah harus diisi");
            etSekolah.requestFocus();
            return;
        }

        Call<ResponseRegisterAnak> call = RetrofitClient
                .getInstance().getApi()
                .createAnak(token, name, email, username, password, jenis_kelamin, orangtua_id, province_id, regency_id, district_id, school, classes, "application/json");

        call.enqueue(new Callback<ResponseRegisterAnak>() {
            @Override
            public void onResponse(Call<ResponseRegisterAnak> call, Response<ResponseRegisterAnak> response) {
                ResponseRegisterAnak responseRegisterAnak = response.body();
                if (response.isSuccessful()){
                    Log.d("TAG", "Response " + response.body());
                    if (responseRegisterAnak.getStatus().equals("success")){
                        Log.i("debug", "onResponse : SUCCESS");
                        loading.dismiss();
                        Toast.makeText(context, "Akun anak berhasil dibuat", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }else {
                        Log.i("debug", "onResponse: FAILED");
                        loading.dismiss();
                        Toast.makeText(context, responseRegisterAnak.getStatus(), Toast.LENGTH_LONG).show();
                    }
                }else {
                    loading.dismiss();
                    try {
                        JSONObject jsonObjectError = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, jsonObjectError.getString("message"), Toast.LENGTH_LONG).show();
                        Log.i("debug", jsonObjectError.getString("message"));
                    }catch (Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseRegisterAnak> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                Toast.makeText(context, "Kesalahan terjadi. Silakan coba beberapa saat lagi.", Toast.LENGTH_LONG).show();
            }
        });


    }
}
