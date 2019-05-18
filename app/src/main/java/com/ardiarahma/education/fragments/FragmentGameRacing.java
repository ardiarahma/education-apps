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
import com.ardiarahma.education.adapters.RecyclerViewGameRacingAdapter;
import com.ardiarahma.education.models.GameRacing;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGameRacing extends Fragment {

    private List<GameRacing> racingList;


    public FragmentGameRacing() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game_racing, container, false);
        RecyclerView rv = (RecyclerView) v.findViewById(R.id.racing_recyclerview);
        RecyclerViewGameRacingAdapter adapter = new RecyclerViewGameRacingAdapter(getContext(), racingList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getActivity(),2));

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        racingList = new ArrayList<>();
        racingList.add(new GameRacing("Double Car", "https://gimbot.co.id/game/d/32/double-car", R.drawable.game_racing_club_double_car));
        racingList.add(new GameRacing("Lap Track", "https://gimbot.co.id/game/d/27/lap-track", R.drawable.game_racing_lap_track));
    }

}
