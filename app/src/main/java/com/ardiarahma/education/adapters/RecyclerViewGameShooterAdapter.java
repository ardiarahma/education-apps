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

import com.ardiarahma.educationapplication.R;
import com.ardiarahma.educationapplication.models.GameShooter;

import java.util.List;

/**
 * Created by Windows 10 on 1/3/2019.
 */

public class RecyclerViewGameShooterAdapter extends RecyclerView.Adapter<RecyclerViewGameShooterAdapter.MyViewHolder> {

    Context mContext;
    List<GameShooter> mData;

    public RecyclerViewGameShooterAdapter(Context mContext, List<GameShooter> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public RecyclerViewGameShooterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_game, parent, false);
        RecyclerViewGameShooterAdapter.MyViewHolder vHolder = new RecyclerViewGameShooterAdapter.MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewGameShooterAdapter.MyViewHolder holder, final int position) {
        holder.game_title.setText(mData.get(position).getName());
        holder.game_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.shooter_cardview.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mData.get(position).getLink()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CardView shooter_cardview;
        TextView game_title;
        ImageView game_thumbnail;
        public MyViewHolder(View itemView) {
            super(itemView);
            shooter_cardview = (CardView) itemView.findViewById(R.id.gameCard);
            game_title = (TextView) itemView.findViewById(R.id.gameTitle);
            game_thumbnail = (ImageView) itemView.findViewById(R.id.gameImg);
        }
    }
}
