package com.ardiarahma.education.activities.parent;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.activities.LoginActivity;
import com.ardiarahma.education.models.District;
import com.ardiarahma.education.models.Province;
import com.ardiarahma.education.models.Regency;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.responses.ResponseDistrict;
import com.ardiarahma.education.models.responses.ResponseProvince;
import com.ardiarahma.education.models.responses.ResponseRegency;
import com.ardiarahma.education.models.responses.ResponseUpdateAnak;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentEditChildActivity extends AppCompatActivity {

    EditText etNama, etEmail, etUsername, etSekolah;
    Spinner spGender, spProvince, spCity, spDistrict, spClass;
    Context context;
    Button bPassword, bUpdateAnak;
    TextView prov, city, kecamatan, user_id;
    ProgressDialog loading;

    ArrayList<Province> provinces;
    ArrayAdapter<Province> adapterProvinces;

    ArrayList<Regency> regencies;
    ArrayAdapter<Regency> adapterRegencies;

    ArrayList<District> districts;
    ArrayAdapter<District> adapterDistrict;

    Token auth = PreferencesConfig.getInstance(this).getToken();
    String token = "Bearer " + auth.getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_child_edit);
        context = this;
        etNama = findViewById(R.id.namaSiswa);
        etEmail = findViewById(R.id.emailSiswa);
        etUsername = findViewById(R.id.usernameSiswa);
        bPassword = findViewById(R.id.passwordSiswa);
        spGender = findViewById(R.id.spinner_gender);
        spProvince = findViewById(R.id.spinner_province);
        spCity = findViewById(R.id.spinner_city);
        spDistrict = findViewById(R.id.spinner_district);
        etSekolah = findViewById(R.id.namaSekolah);
        spClass = findViewById(R.id.spinner_class);
        prov = findViewById(R.id.prov);
        city = findViewById(R.id.city);
        kecamatan = findViewById(R.id.district);
        user_id = findViewById(R.id.id_anak);

        Intent intent = getIntent();
        int id = intent.getIntExtra("childId", 0);
        String name = intent.getStringExtra("childName");
        String email = intent.getStringExtra("childEmail");
        String username = intent.getStringExtra("childUsername");
        String school = intent.getStringExtra("childSchool");

        etNama.setText(name, TextView.BufferType.EDITABLE);
        user_id.setText(String.valueOf(id));
        etEmail.setText(email, TextView.BufferType.EDITABLE);
        etUsername.setText(username, TextView.BufferType.EDITABLE);
        etSekolah.setText(school, TextView.BufferType.EDITABLE);

        //=================== REGISTER BUTTON ==================//
        bUpdateAnak = findViewById(R.id.edit_anak_btn);
        bUpdateAnak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Konfirmasi");
                alertDialog.setMessage("Anda yakin ingin mengubah data diri anak?");
                alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loading = ProgressDialog.show(context, null,
                                "Tunggu sesaat...", true, false);
                        editAnak();
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.show();

            }
        });

        //=================== PASSWORD BUTTON ==================//
        bPassword = findViewById(R.id.passwordSiswa);
        bPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ParentEditChildActivity.this, ParentChangePasswordChildActivity.class);
                startActivity(intent1);
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
        loading = ProgressDialog.show(context, null, "Tunggu sesaat..", true, false);
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
                        loading.dismiss();
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
                loading.dismiss();
                Toast.makeText(context, "Gagal mengambil data provinsi", Toast.LENGTH_LONG).show();
                Log.d("TAG", "Response" + t.toString());
            }
        });
    }

    public void editAnak(){
        String name = etNama.getText().toString().trim();
        int id = Integer.valueOf(user_id.getText().toString());
        String email = etEmail.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String jenis_kelamin = spGender.getSelectedItem().toString().trim();
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

        if (school.isEmpty()){
            loading.dismiss();
            etSekolah.setError("Nama sekolah harus diisi");
            etSekolah.requestFocus();
            return;
        }

        Call<ResponseUpdateAnak> call = RetrofitClient
                .getInstance()
                .getApi()
                .editAnak(token, id, name, email, username, jenis_kelamin,
                        province_id, regency_id, district_id, school, classes, "application/json");
        call.enqueue(new Callback<ResponseUpdateAnak>() {
            @Override
            public void onResponse(Call<ResponseUpdateAnak> call, Response<ResponseUpdateAnak> response) {
                ResponseUpdateAnak responseUpdateAnak = response.body();
                loading.dismiss();
                if (response.isSuccessful()){
                    if (responseUpdateAnak.getStatus().equals("success")){
                        Log.i("debug", "onResponse : SUCCESS");
                        Toast.makeText(context, "Data anak berhasil diubah", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }else {
                        Log.i("debug", "onResponse: FAILED");
                        Toast.makeText(context, "Gagal mengubah data anak", Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        JSONObject jsonObjectError = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, jsonObjectError.getString("message"), Toast.LENGTH_LONG).show();
                        Log.i("debug", jsonObjectError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseUpdateAnak> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                Toast.makeText(context, "Kesalahan terjadi. Silakan coba beberapa saat lagi.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
