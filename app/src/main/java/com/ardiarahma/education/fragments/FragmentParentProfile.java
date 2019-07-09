package com.ardiarahma.education.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.activities.parent.ParentChangePasswordActivity;
import com.ardiarahma.education.activities.parent.ParentProfileUpdateActivity;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.models.responses.ResponseBanksoal;
import com.ardiarahma.education.models.responses.ResponseCheckUser;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentParentProfile extends Fragment {

    TextView namalengkap, emailortu, usernameortu;
    Button pass_change, update_profile;
    User user = PreferencesConfig.getInstance(getContext()).getUser();
    Token auth = PreferencesConfig.getInstance(getContext()).getToken();
    String token = "Bearer " + auth.getToken();
    ProgressDialog loading;
    int user_id = user.getId();

    public FragmentParentProfile() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_parent_profile, container, false);
        namalengkap = v.findViewById(R.id.namalengkap);
        emailortu = v.findViewById(R.id.emailOrtu);
        usernameortu = v.findViewById(R.id.usernameOrtu);
        loading = ProgressDialog.show(getActivity(), null, "Mohon tunggu sebentar...",
                true, false);
        refresh();
        loading.dismiss();
        pass_change = v.findViewById(R.id.pass_change);
        pass_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ParentChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        update_profile = v.findViewById(R.id.update_profile);
        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ParentProfileUpdateActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }


    public void onViewCreated(View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profil");
    }

    public void refresh(){
        Call<ResponseCheckUser> call = RetrofitClient
                .getInstance()
                .getApi()
                .ortu_details(token, user_id);

        call.enqueue(new Callback<ResponseCheckUser>() {
            @Override
            public void onResponse(Call<ResponseCheckUser> call, Response<ResponseCheckUser> response) {
                loading.dismiss();
                ResponseCheckUser responseCheckUser = response.body();
                Log.d("TAG", "Response " + response.body());
                if (response.isSuccessful()){
                    if (responseCheckUser.getStatus().equals("success")){
                        String nama = responseCheckUser.getUser().getNama();
                        String email = responseCheckUser.getUser().getEmail();
                        String username = responseCheckUser.getUser().getUsername();
                        namalengkap.setText(nama);
                        emailortu.setText(email);
                        usernameortu.setText(username);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCheckUser> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                Toast.makeText(getContext(), "Kesalahan terjadi. Silakan coba beberapa saat lagi.",
                        Toast.LENGTH_LONG).show();
            }
        });
//        namalengkap.setText(user.getNama());
//        emailortu.setText(user.getEmail());
//        usernameortu.setText(user.getUsername());
    }


}
