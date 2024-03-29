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
import com.ardiarahma.education.models.Articles;
import com.ardiarahma.education.models.ArticlesResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows 10 on 2/28/2019.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder>{

    Context context;
    List<Articles> articles;
    ArticlesResult totalResults;

    public ArticlesAdapter(Context context, ArrayList<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ArticlesAdapter.ViewHolder holder, final int position) {
        holder.news_title.setText(articles.get(position).getTitle());
        holder.news_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articles.get(position).getUrl()));
                context.startActivity(intent);
            }
        });
        Picasso.with(context).load(articles.get(position).getUrlToImage()).into(holder.news_thumbnail);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView news_title;
        private CardView news_cardview;
        private ImageView news_thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);

            news_title = (TextView) itemView.findViewById(R.id.title_item_news);
            news_cardview = (CardView) itemView.findViewById(R.id.news);
            news_thumbnail = itemView.findViewById(R.id.news_thumbnail);
        }
    }
}
