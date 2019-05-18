package com.ardiarahma.education.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ardiarahma.education.R;
import com.ardiarahma.education.adapters.RecyclerViewGameShooterAdapter;
import com.ardiarahma.education.models.GameShooter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGameShooter extends Fragment {

    private List<GameShooter> shooterList;

    public FragmentGameShooter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game_shooter, container, false);
        RecyclerView rv = (RecyclerView) v.findViewById(R.id.shooter_recyclerview);
        RecyclerViewGameShooterAdapter adapter = new RecyclerViewGameShooterAdapter(getContext(), shooterList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getActivity(),2));

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shooterList= new ArrayList<>();
        shooterList.add(new GameShooter("Planet Conquerors", "https://gimbot.co.id/game/d/user_header_ortu_1/planet-conquerors", R.drawable.game_shooter_planet_conquerors));
        shooterList.add(new GameShooter("Small Football", "https://gimbot.co.id/game/d/1/small-football", R.drawable.game_shooter_small_football));
    }

}
