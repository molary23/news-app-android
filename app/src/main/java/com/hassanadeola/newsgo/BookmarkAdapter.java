package com.hassanadeola.newsgo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hassanadeola.newsgo.Models.Bookmark;
import com.hassanadeola.newsgo.interfaces.BookmarkListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkViewHolder> {
    private Context context;
    private List<Bookmark> bookmarks;
    private BookmarkListener listener;

    public BookmarkAdapter(Context context, List<Bookmark> bookmarks, BookmarkListener listener) {
        this.context = context;
        this.bookmarks = bookmarks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookmarkViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.headlines_bookmark_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        holder.post_title.setText(bookmarks.get(position).getPostTitle());
        holder.post_source.setText(bookmarks.get(position).getPostSource());

        if (bookmarks.get(position).getPostUrl() != null) {
            Picasso.get().load(bookmarks.get(position).getPostImage()).into(holder.post_image);
        }
        holder.post_image.setContentDescription(bookmarks.get(position).getPostTitle());

        holder.btn_open.setOnClickListener((View v) -> listener.openInBrowser(bookmarks.get(position).getPostUrl()));
        holder.btn_share.setOnClickListener((View v) -> listener.shareBookmarked(bookmarks.get(position).getPostUrl()));
        holder.btn_remove.setOnClickListener((View v) -> listener.removeBookmarked(bookmarks.get(position).getBookId()));

    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }
}
