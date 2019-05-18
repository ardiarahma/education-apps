package com.ardiarahma.education.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ardiarahma.education.R;
import com.ardiarahma.education.activities.LoginActivity;
import com.ardiarahma.education.networks.PreferencesConfig;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogout extends Fragment {

    Button ya, tidak;
//    ProgressDialog loading;

    public FragmentLogout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_logout, container, false);

        ya = (Button) v.findViewById(R.id.YA);
        tidak = (Button) v.findViewById(R.id.TIDAK);


        ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesConfig.getInstance(getContext()).clear();
//                loading = ProgressDialog.show(getActivity(), null, "Mohon tunggu sebentar...",true, false);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loading.dismiss();
                Intent intent = new Intent(getActivity(), FragmentHome.class);
                startActivity(intent);
            }
        });

        return v;
    }

}
