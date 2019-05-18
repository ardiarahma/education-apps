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
import com.ardiarahma.education.models.EbookShelves;

import java.util.ArrayList;

/**
 * Created by Windows 10 on 1/16/2019.
 */

public class RV_EbookShelvesAdapter extends RecyclerView.Adapter<RV_EbookShelvesAdapter.ViewHolder> {

    private ArrayList<EbookShelves> ebookShelves;
    private Context context;

    public RV_EbookShelvesAdapter(ArrayList<EbookShelves> ebookShelves, Context context) {
        this.ebookShelves = ebookShelves;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ebook_shelves, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final EbookShelves ebookShelf = ebookShelves.get(position);

        holder.ebook_title.setText(ebookShelf.getTitle());
        holder.ebook_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ebookShelf.getUrl()));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ebookShelves.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ebook_title;
        public CardView ebook_link;
        public ImageView ebook_image;

        public ViewHolder(View itemView) {
            super(itemView);

            ebook_title = (TextView) itemView.findViewById(R.id.book_title);
            ebook_link = (CardView) itemView.findViewById(R.id.cardBook);
//            ebook_image = itemView.findViewById(R.id.book_thumbnail);
        }
    }

}
