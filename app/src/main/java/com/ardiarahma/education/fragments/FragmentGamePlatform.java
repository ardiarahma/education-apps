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
import com.ardiarahma.education.adapters.RecyclerViewGamePlatformAdapter;
import com.ardiarahma.education.models.GamePlatform;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGamePlatform extends Fragment {

    private List<GamePlatform> platformList;


    public FragmentGamePlatform() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game_platform, container, false);
        RecyclerView rv = (RecyclerView) v.findViewById(R.id.platform_recyclerview);
        RecyclerViewGamePlatformAdapter adapter = new RecyclerViewGamePlatformAdapter(getContext(), platformList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getActivity(),2));

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        platformList = new ArrayList<>();
        platformList.add(new GamePlatform("Chicken Dodge", "https://gimbot.co.id/game/d/24/chicken-dodge", R.drawable.game_platform_chicken_dodge));
    }

}
