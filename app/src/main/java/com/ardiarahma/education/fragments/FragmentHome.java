package com.ardiarahma.education.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ardiarahma.education.R;
import com.ardiarahma.education.activities.child.BanksoalActivity;
import com.ardiarahma.education.activities.child.EbookActivity;
import com.ardiarahma.education.activities.child.GameActivity;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.networks.PreferencesConfig;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {

    ImageButton game, ebook, banksoal;

    User user = PreferencesConfig.getInstance(getActivity()).getUser();


    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Beranda");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //=================== manggil username ==================//
        TextView wm_result = (TextView) view.findViewById(R.id.wm_result);
        wm_result.setText(user.getUsername());

        //=============== Click button from fragment home to GameActivity ===========//
        game = view.findViewById(R.id.game);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);
            }
        });

        ebook = view.findViewById(R.id.ebook);
        ebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EbookActivity.class);
                startActivity(intent);
            }
        });

        banksoal = view.findViewById(R.id.banksoal);
        banksoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BanksoalActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

//    public loadGame(){
        
//    }


}
