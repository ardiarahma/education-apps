package com.ardiarahma.education.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ardiarahma.education.R;
import com.ardiarahma.education.adapters.RV_GameArcade;
import com.ardiarahma.education.adapters.RecyclerViewGameArcadeAdapter;
import com.ardiarahma.education.models.GameArcade;
import com.ardiarahma.education.models.Games;
import com.ardiarahma.education.models.Log;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.responses.ResponseGames;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGameArcade extends Fragment {

    private List<GameArcade> arcadeList;
    RecyclerViewGameArcadeAdapter adapter;
    RecyclerView rv;

    public FragmentGameArcade() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_arcade, container, false);
        rv = v.findViewById(R.id.arcade_recyclerview);
        adapter = new RecyclerViewGameArcadeAdapter(getContext(), arcadeList);
        rv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
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
    }
}
