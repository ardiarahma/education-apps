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
import com.ardiarahma.education.adapters.RecyclerViewGameArcadeAdapter;
import com.ardiarahma.education.models.GameArcade;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGameArcade extends Fragment {

    private List<GameArcade> arcadeList;
    //ImageButton arcadeImg;


    public FragmentGameArcade() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game_arcade, container, false);
        RecyclerView rv = (RecyclerView) v.findViewById(R.id.arcade_recyclerview);
        RecyclerViewGameArcadeAdapter adapter = new RecyclerViewGameArcadeAdapter(getContext(), arcadeList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arcadeList = new ArrayList<>();
        arcadeList.add(new GameArcade("Asteroid Garden", "https://gimbot.co.id/game/d/14/asteroid-garden", R.drawable.game_arcade_asteroid_garden));
        arcadeList.add(new GameArcade("Club Magnon", "https://gimbot.co.id/game/d/6/club-magnon", R.drawable.game_arcade_club_magnon));
        arcadeList.add(new GameArcade("Dagger Master", "https://gimbot.co.id/game/d/11/dagger-master", R.drawable.game_arcade_dagger_master));
        arcadeList.add(new GameArcade("Island Dodge", "https://gimbot.co.id/game/d/4/island-dodge", R.drawable.game_arcade_island_dodge));
        arcadeList.add(new GameArcade("oHamster", "https://gimbot.co.id/game/d/7/ohamster", R.drawable.game_arcade_o_hamster));
        arcadeList.add(new GameArcade("Wire Hoop", "https://gimbot.co.id/game/d/33/wire-hoop", R.drawable.game_arcade_wire_hoop));

        //arcadeImg = (ImageButton) getView().findViewsWithText(arcadeList, "Asteroid Garden", R.drawable.game_arcade_asteroid_garden);


    }


}
