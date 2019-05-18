package com.ardiarahma.education.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ardiarahma.education.R;
import com.ardiarahma.education.activities.ParentChangePasswordActivity;
import com.ardiarahma.education.activities.ParentProfileUpdateActivity;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.networks.PreferencesConfig;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentParentProfile extends Fragment {

    TextView namalengkap, emailortu, usernameortu;
    Button pass_change, update_profile;

    User user = PreferencesConfig.getInstance(getContext()).getUser();

    ProgressDialog loading;

    public FragmentParentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_parent_profile, container, false);

        namalengkap = v.findViewById(R.id.namalengkap);

        loading = ProgressDialog.show(getActivity(), null, "Mohon tunggu sebentar...",true, false);

        emailortu = v.findViewById(R.id.emailOrtu);
        usernameortu = v.findViewById(R.id.usernameOrtu);

        namalengkap.setText(user.getNama());
        emailortu.setText(user.getEmail());
        usernameortu.setText(user.getUsername());

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

}
