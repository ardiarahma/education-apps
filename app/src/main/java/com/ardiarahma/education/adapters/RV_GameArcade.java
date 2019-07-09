package com.ardiarahma.education.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ardiarahma.education.R;
import com.ardiarahma.education.models.GameArcade;
import com.ardiarahma.education.models.Games;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Windows 10 on 1/3/2019.
 */

public class RV_GameArcade extends RecyclerView.Adapter<RV_GameArcade.MyViewHolder> {

    Context mContext;
    List<Games> games;
    int gamecategories = 1;

    public RV_GameArcade(Context mContext, List<Games> games) {
        this.mContext = mContext;
        this.games = games;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_game, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.game_title.setText(games.get(position).getName());
        Picasso.with(mContext).load(games.get(position).getImg()).into(holder.game_thumbnail);
        holder.arcade_cardview.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(games.get(position).getUrl()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CardView arcade_cardview;
        TextView game_title;
        ImageView game_thumbnail;
        public MyViewHolder(View itemView) {
            super(itemView);

            arcade_cardview = (CardView) itemView.findViewById(R.id.gameCard);
            game_title = (TextView) itemView.findViewById(R.id.gameTitle);
            game_thumbnail = (ImageView) itemView.findViewById(R.id.gameImg);
        }
    }
}
