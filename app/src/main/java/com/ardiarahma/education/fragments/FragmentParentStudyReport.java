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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.adapters.LogStudyAdapter;
import com.ardiarahma.education.models.Siswa;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.models.responses.ResponseCheckUser;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;

public class FragmentParentStudyReport extends Fragment {

    ArrayList<Siswa> siswas;
    LogStudyAdapter adapter;
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    LinearLayout noData, fillData;
    SwipeRefreshLayout swipeRefreshLayout;


    ProgressDialog loading;
    User user = PreferencesConfig.getInstance(getActivity()).getUser();
    Token auth = PreferencesConfig.getInstance(getActivity()).getToken();

    public FragmentParentStudyReport(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_parent_report, container, false);
        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);
        rv = v.findViewById(R.id.rv_listAnak);
        adapter = new LogStudyAdapter(getContext(), siswas);
        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listAnak();
            }
        });
        noData = v.findViewById(R.id.layoutEmpty);
        fillData = v.findViewById(R.id.layoutFill);
        return v;
    }

    public void onResume() {
        super.onResume();
        listAnak();
    }

    public void onViewCreated(View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Laporan Hasil Belajar Anak");
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
                swipeRefreshLayout.setRefreshing(false);
                ResponseCheckUser responseCheckUser = response.body();
                Log.d("TAG", "Response" + response.body());
                if (response.isSuccessful()){
                    if (responseCheckUser.getStatus().equals("success")){
                        Log.i("debug", "onResponse : SUCCESSFUL");
                        siswas = responseCheckUser.getAnak();
                        adapter = new LogStudyAdapter(getContext(), siswas);
                        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rv.setAdapter(adapter);
                        rv.setHasFixedSize(true);
                        rv.setLayoutManager(layoutManager);
                        adapter.notifyDataSetChanged();
                    }else {
                        Log.i("debug", "onResponse : FAILED");
                        swipeRefreshLayout.setRefreshing(false);

                    }
                    checklist();
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

    public void checklist(){
        if (layoutManager.getItemCount() == 0){
            fillData.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }else{
            noData.setVisibility(View.GONE);
            fillData.setVisibility(View.VISIBLE);
        }
    }
}
