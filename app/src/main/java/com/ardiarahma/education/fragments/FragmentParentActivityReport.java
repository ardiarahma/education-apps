package com.ardiarahma.education.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ardiarahma.education.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentParentActivityReport extends Fragment {


    public FragmentParentActivityReport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent_activity_report, container, false);
    }

}
