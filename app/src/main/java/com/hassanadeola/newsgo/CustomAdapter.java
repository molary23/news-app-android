package com.hassanadeola.newsgo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hassanadeola.newsgo.Models.NewsHeadlines;
import com.hassanadeola.newsgo.interfaces.SelectListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<NewsHeadlines> headlines;
    private SelectListener listener;

    public CustomAdapter(Context context, List<NewsHeadlines> headlines, SelectListener listener) {
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.headlines_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.post_title.setText(headlines.get(position).getTitle());
        holder.post_source.setText(headlines.get(position).getSource().getName());

        if (headlines.get(position).getUrlToImage() != null) {
            Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.post_image);
        }
        holder.post_image.setContentDescription(headlines.get(position).getTitle());

        holder.btn_open.setOnClickListener((View v) -> listener.onNewsClicked(headlines.get(position)));
        holder.btn_share.setOnClickListener((View v) -> listener.onNewsShared(headlines.get(position).getUrl()));
        holder.btn_bookmark.setOnClickListener((View v) -> listener.onNewsBookmarked(headlines.get(position)));

    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }

}
