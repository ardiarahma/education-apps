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
import com.ardiarahma.education.adapters.RecyclerViewGameClassicAdapter;
import com.ardiarahma.education.models.GameClassic;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGameClassic extends Fragment {

    private List<GameClassic> classicList;


    public FragmentGameClassic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game_classic, container, false);
        RecyclerView rv = (RecyclerView) v.findViewById(R.id.classic_recyclerview);
        RecyclerViewGameClassicAdapter adapter = new RecyclerViewGameClassicAdapter(getContext(), classicList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getActivity(),2));

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        classicList = new ArrayList<>();
        classicList.add(new GameClassic("Snake", "https://gimbot.co.id/game/d/22/snake", R.drawable.game_classic_snake));
    }

}
