package com.hassanadeola.newsgo;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookmarkViewHolder extends RecyclerView.ViewHolder {

    TextView post_title, post_source;
    ImageView post_image;
    ImageButton btn_open, btn_share, btn_remove;

    public BookmarkViewHolder(@NonNull View itemView) {
        super(itemView);

        post_title = itemView.findViewById(R.id.post_title);
        post_source = itemView.findViewById(R.id.post_source);
        post_image = itemView.findViewById(R.id.post_image);
        btn_open = itemView.findViewById(R.id.btn_open);
        btn_remove = itemView.findViewById(R.id.btn_remove);
        btn_share = itemView.findViewById(R.id.btn_share);
    }
}
