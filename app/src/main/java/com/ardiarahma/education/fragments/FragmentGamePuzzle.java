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
import com.ardiarahma.education.adapters.RecyclerViewGamePuzzleAdapter;
import com.ardiarahma.education.models.GamePuzzle;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGamePuzzle extends Fragment {

    private List<GamePuzzle> puzzleList;


    public FragmentGamePuzzle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game_puzzle, container, false);
        RecyclerView rv = (RecyclerView) v.findViewById(R.id.puzzle_recyclerview);
        RecyclerViewGamePuzzleAdapter adapter= new RecyclerViewGamePuzzleAdapter(getContext(), puzzleList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getActivity(),2));

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        puzzleList = new ArrayList<>();
        puzzleList.add(new GamePuzzle("Burger Stack", "https://gimbot.co.id/game/d/21/burger-stack", R.drawable.game_puzzle_burger_stack));
    }

}
