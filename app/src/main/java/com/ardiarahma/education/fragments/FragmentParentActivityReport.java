package com.ardiarahma.education.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.adapters.LogActivityAdapter;
import com.ardiarahma.education.models.Siswa;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.models.responses.ResponseCheckUser;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;

public class FragmentParentActivityReport extends Fragment {

    ArrayList<Siswa> siswas;
    LogActivityAdapter adapter;
    RecyclerView rv;
    SwipeRefreshLayout swipeRefreshLayout;

    ProgressDialog loading;
    User user = PreferencesConfig.getInstance(getActivity()).getUser();
    Token auth = PreferencesConfig.getInstance(getActivity()).getToken();

    public FragmentParentActivityReport(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_parent_activity_report, container, false);

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);

        rv = v.findViewById(R.id.rv_listAnak);
        adapter = new LogActivityAdapter(getContext(), siswas);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listAnak();
            }
        });

        return v;
    }

    public void onResume() {
        super.onResume();
        listAnak();
    }

    public void onViewCreated(View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Laporan Kegiatan Anak");
    }

    public void listAnak(){
        loading = ProgressDialog.show(getActivity(), null, "Please wait...",true, false);


        String token = "Bearer " + auth.getToken();
        int user_id = user.getId();


        retrofit2.Call<ResponseCheckUser> call = RetrofitClient
                .getInstance()
                .getApi()
                .readAnak(token, user_id);

        call.enqueue(new Callback<ResponseCheckUser>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseCheckUser> call, Response<ResponseCheckUser> response) {
                loading.dismiss();
                ResponseCheckUser responseCheckUser = response.body();
                Log.d("TAG", "Response" + response.body());
                if (response.isSuccessful()){
                    if (responseCheckUser.getStatus().equals("success")){
                        Log.i("debug", "onResponse : SUCCESSFUL");
                        siswas = responseCheckUser.getAnak();
                        adapter = new LogActivityAdapter(getContext(), siswas);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rv.setAdapter(adapter);
                        rv.setHasFixedSize(true);
                        rv.setLayoutManager(linearLayoutManager);
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }else {
                        Log.i("debug", "onResponse : FAILED");
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseCheckUser> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                Toast.makeText(getContext(), "Kesalahan terjadi. Silakan coba beberapa saat lagi.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
