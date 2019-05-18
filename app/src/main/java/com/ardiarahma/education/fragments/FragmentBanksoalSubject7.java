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
import com.ardiarahma.education.adapters.RV_BanksoalSubject7Adapter;
import com.ardiarahma.education.models.BanksoalSubject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBanksoalSubject7 extends Fragment {

    List<BanksoalSubject> banksoal_subjects;


    public FragmentBanksoalSubject7() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_banksoal_subject, container, false);
        RecyclerView rv = (RecyclerView) v.findViewById(R.id.subject_recyclerview_soal);
        RV_BanksoalSubject7Adapter adapter = new RV_BanksoalSubject7Adapter(getContext(), banksoal_subjects);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(linearLayoutManager);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        banksoal_subjects = new ArrayList<>();
        banksoal_subjects.add(new BanksoalSubject(2, "Agama", R.drawable.subject_religion));
        banksoal_subjects.add(new BanksoalSubject(6, "Bahasa Indonesia", R.drawable.subject_indonesian));
        banksoal_subjects.add(new BanksoalSubject(7, "Bahasa Inggris", R.drawable.subject_english));
        banksoal_subjects.add(new BanksoalSubject(8, "Ilmu Pengetahuan Sosial", R.drawable.subject_social));
        banksoal_subjects.add(new BanksoalSubject(4, "Ilmu Pengetahuan Alam", R.drawable.subject_chemistry));
        banksoal_subjects.add(new BanksoalSubject(1, "Matematika", R.drawable.subject_math));
        banksoal_subjects.add(new BanksoalSubject(3, "Pendidikan Kewarganegaraan", R.drawable.subject_citizenship));
        banksoal_subjects.add(new BanksoalSubject(5, "Pendidikan Olahraga", R.drawable.subject_sport));
        banksoal_subjects.add(new BanksoalSubject(9, "Prakarya", R.drawable.subject_prakarya));
        banksoal_subjects.add(new BanksoalSubject(10, "Seni Budaya", R.drawable.subject_art_and_culture));
    }
}
