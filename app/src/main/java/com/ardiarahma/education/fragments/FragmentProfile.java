package com.ardiarahma.education.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ardiarahma.education.R;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.models.responses.ResponseStudent;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {

    TextView namalengkap, emailanak, username, school_name, kelas;

    User user = PreferencesConfig.getInstance(getContext()).getUser();
    Token auth = PreferencesConfig.getInstance(getContext()).getToken();

    ProgressDialog loading;


    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        namalengkap = v.findViewById(R.id.namalengkap);
        emailanak = v.findViewById(R.id.emailSiswa);
        username = v.findViewById(R.id.usernameSiswa);
        school_name = v.findViewById(R.id.sekolahSiswa);
        kelas = v.findViewById(R.id.kelasSiswa);

        loading = ProgressDialog.show(getActivity(), null, "Mohon tunggu sebentar...",true, false);

        namalengkap.setText(user.getNama());
        emailanak.setText(user.getEmail());
        username.setText(user.getUsername());

        String token = "Bearer " + auth.getToken();
        int user_id = user.getId();


        retrofit2.Call<ResponseStudent> call = RetrofitClient
                .getInstance()
                .getApi()
                .profile_siswa(token, user_id);

        call.enqueue(new Callback<ResponseStudent>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseStudent> call, Response<ResponseStudent> response) {
                ResponseStudent responseStudent = response.body();
                Log.d("TAG", "Response " + response.body());
                if (response.isSuccessful()){
                    Log.i("debug", "onResponse : SUCCESS");
                    if (responseStudent.getStatus().equals("success")){
                        Log.d("TAG", "Response " + responseStudent.getResult());
                        Log.d("TAG", "Response " + responseStudent.getResult().getSchool_name());
                        Log.d("TAG", "Response " + responseStudent.getResult().getSchool_name());
                        Log.d("TAG", "Response " + responseStudent.getResult().getClasses());


                        String sekolah = responseStudent.getResult().getSchool_name();
                        int classes = responseStudent.getResult().getClasses();
                        school_name.setText(sekolah);
                        kelas.setText(String.valueOf(classes));
                        loading.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseStudent> call, Throwable t) {
                loading.dismiss();
                Log.d("TAG", "Response" + t.toString());
            }
        });

        return v;
    }

    public void onViewCreated(View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Profil");

    }

}
