package com.ardiarahma.education.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.activities.parent.ParentRegisterChildActivity;
import com.ardiarahma.education.adapters.ArticlesAdapter;
import com.ardiarahma.education.adapters.SiswaAdapter;
import com.ardiarahma.education.models.Articles;
import com.ardiarahma.education.models.Siswa;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.models.responses.ResponseCheckUser;
import com.ardiarahma.education.models.responses.ResponseNews;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentParentHome extends Fragment {

//    ArrayList<Articles> articles;
//    ArticlesAdapter adapter;
    ArrayList<Siswa> siswas;
    SiswaAdapter adapter;
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    LinearLayout noData, fillData;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog loading;
    User user = PreferencesConfig.getInstance(getActivity()).getUser();
    Token auth = PreferencesConfig.getInstance(getActivity()).getToken();
    String token = "Bearer " + auth.getToken();

    public FragmentParentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_parent_home, container, false);
        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);
        rv = v.findViewById(R.id.rv_listAnak);
        adapter = new SiswaAdapter(getContext(), siswas);
        loading = ProgressDialog.show(getActivity(), null, "Tunggu sebentar ya!",
                true, false);
        noData = v.findViewById(R.id.layoutEmpty);
        fillData = v.findViewById(R.id.layoutFill);
        TextView wm_result = (TextView) v.findViewById(R.id.wm_result);
        wm_result.setText(user.getUsername());
        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                loadNews();
                listAnak();
            }
        });
        Button btnCreateAnak = v.findViewById(R.id.tambahAnak);
        btnCreateAnak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ParentRegisterChildActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        listAnak();
//        loadNews();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Beranda");

    }

    public void listAnak(){
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
                        adapter = new SiswaAdapter(getContext(), siswas);
                        layoutManager = new LinearLayoutManager(getContext(),
                                LinearLayoutManager.VERTICAL, false);
                        rv.setAdapter(adapter);
                        rv.setHasFixedSize(true);
                        rv.setLayoutManager(layoutManager);
                        adapter.notifyDataSetChanged();
//                        swipeRefreshLayout.setRefreshing(false);
                    }else {
                        Log.i("debug", "onResponse : FAILED");
                    }
                    checklist();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseCheckUser> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "Kesalahan terjadi. Silakan coba beberapa saat lagi.",
                        Toast.LENGTH_LONG).show();
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

//    public void loadNews(){
//        Call<ResponseNews> call = RetrofitClient
//                .getInstance()
//                .getApi()
//                .getJSON(token);
//        call.enqueue(new Callback<ResponseNews>() {
//            @Override
//            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
//                loading.dismiss();
//                swipeRefreshLayout.setRefreshing(false);
//                ResponseNews responseNews = response.body();
//                if (response.isSuccessful()){
//                    if (responseNews.getStatus().equals("success")){
//                        Log.d("TAG", "Response" + response.body());
//                        Log.i("debug", "onResponse : SUCCESSFUL");
//                        articles = responseNews.getResult().getArticles();
//                        adapter = new ArticlesAdapter(getContext(), articles);
//                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
//                                LinearLayoutManager.VERTICAL, false);
//                        rv.setAdapter(adapter);
//                        rv.setHasFixedSize(true);
//                        rv.setLayoutManager(linearLayoutManager);
//                        adapter.notifyDataSetChanged();
//                    }else {
//                        Log.i("debug", "onResponse : FAILED");
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseNews> call, Throwable t) {
//                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
//                loading.dismiss();
//                swipeRefreshLayout.setRefreshing(false);
//                Toast.makeText(getContext(), "Kesalahan terjadi. Silakan coba beberapa saat lagi.",
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}
