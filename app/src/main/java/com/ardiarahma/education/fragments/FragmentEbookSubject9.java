package com.ardiarahma.education.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ardiarahma.education.R;
import com.ardiarahma.education.adapters.RV_EbookSubject9Adapter;
import com.ardiarahma.education.models.EbookSubject;

import java.util.ArrayList;
import java.util.List;

public class FragmentEbookSubject9 extends Fragment {

    List<EbookSubject> ebook_subjects;

    public FragmentEbookSubject9() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_ebook_subject, container, false);
        final RecyclerView rv = (RecyclerView) v.findViewById(R.id.subject_recyclerview);
        RV_EbookSubject9Adapter adapter = new RV_EbookSubject9Adapter(getContext(), ebook_subjects);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(linearLayoutManager);

//        String token = "Bearer ";
//
//        Call<ResponseSubjects> call = RetrofitClient
//                .getInstance()
//                .getApi()
//                .subjects(token);
//
//        call.enqueue(new Callback<ResponseSubjects>() {
//            @Override
//            public void onResponse(Call<ResponseSubjects> call, Response<ResponseSubjects> response) {
//                ResponseSubjects responseSubjects = response.body();
//                if (response.isSuccessful()){
//                    if (responseSubjects.getStatus().equals("success")){
//                        ebook_subjects = new ArrayList<>(Arrays.asList(responseSubjects.getResult()));
//                        RV_EbookSubject7Adapter adapter = new RV_EbookSubject7Adapter(getContext(), ebook_subjects);
//                        adapter.notifyDataSetChanged();
//                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//                        rv.setAdapter(adapter);
//                        rv.setHasFixedSize(true);
//                        rv.setLayoutManager(linearLayoutManager);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseSubjects> call, Throwable t) {
//                Log.d("Error", t.getMessage());
//            }
//        });

        return v;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        ebook_subjects = new ArrayList<>();
        ebook_subjects.add(new EbookSubject(2,"Agama", R.drawable.subject_religion));
        ebook_subjects.add(new EbookSubject(6,"Bahasa Indonesia", R.drawable.subject_indonesian));
        ebook_subjects.add(new EbookSubject(7,"Bahasa Inggris", R.drawable.subject_english));
        ebook_subjects.add(new EbookSubject(8,"Ilmu Pengetahuan Sosial", R.drawable.subject_social));
        ebook_subjects.add(new EbookSubject(4,"Ilmu Pengetahuan Alam", R.drawable.subject_chemistry));
        ebook_subjects.add(new EbookSubject(1,"Matematika", R.drawable.subject_math));
        ebook_subjects.add(new EbookSubject(3,"Pendidikan Kewarganegaraan", R.drawable.subject_citizenship));
        ebook_subjects.add(new EbookSubject(5,"Pendidikan Olahraga", R.drawable.subject_sport));
        ebook_subjects.add(new EbookSubject(9,"Prakarya", R.drawable.subject_prakarya));
        ebook_subjects.add(new EbookSubject(10,"Seni Budaya", R.drawable.subject_art_and_culture));




//        list_mapel = (ListView) findViewById(R.id.list_mapel7);
//        EbookSubjectAdapter7 adapter = new EbookSubjectAdapter7(this, R.layout.item_subject, subjects);
//        list_mapel.setAdapter(adapter);
//        list_mapel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //sama kayak recyclerviewgame.java, ngambil url dari uri parse
//                Intent intent = new Intent(FragmentEbookSubject7.this, EbookShelvesActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        ImageButton toolbar_ebook = (ImageButton) findViewById(R.id.toolbar_ebook);
//        toolbar_ebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(FragmentEbookSubject7.this, EbookActivity.class);
//                startActivity(intent);
//            }
//        });
//
    }
}
