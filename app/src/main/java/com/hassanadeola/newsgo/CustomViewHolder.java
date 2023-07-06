package com.hassanadeola.newsgo;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView post_title, post_source;
    ImageView post_image;
    ImageButton btn_open, btn_bookmark, btn_share;

    CardView cardView;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        post_title = itemView.findViewById(R.id.post_title);
        post_source = itemView.findViewById(R.id.post_source);
        post_image = itemView.findViewById(R.id.post_image);
        btn_open = itemView.findViewById(R.id.btn_open);
        btn_share = itemView.findViewById(R.id.btn_share);
        btn_bookmark = itemView.findViewById(R.id.btn_bookmark);
        cardView = itemView.findViewById(R.id.cardView);
    }


}
