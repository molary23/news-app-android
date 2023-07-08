package com.hassanadeola.newsgo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hassanadeola.newsgo.Models.Bookmark;
import com.hassanadeola.newsgo.Models.SQLQueries;
import com.hassanadeola.newsgo.interfaces.BookmarkListener;
import com.hassanadeola.newsgo.utils.Functions;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity implements BookmarkListener {
    RecyclerView recyclerView;
    BookmarkAdapter adapter;

    SearchView searchView;
    FrameLayout progressBar;
    LinearLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Functions.changeTheme(this);

        setContentView(R.layout.activity_bookmark);

        progressBar = findViewById(R.id.progressBar);
        searchView = findViewById(R.id.searchView);
        rootView = findViewById(R.id.rootView);

        showNews(getPosts(null));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                // toggleDisable(false);
                showNews(getPosts(s));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    // toggleDisable(false);
                    showNews(getPosts(null));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        searchView.setQuery("", false);
        rootView.requestFocus();
    }


    public List<Bookmark> getPosts(String q) {
        List<Bookmark> postList = new ArrayList<>();
        SQLQueries newQueries = new SQLQueries(this);
        postList = newQueries.getBookmarkedPosts(q);
        return postList;
    }

    private void showNews(List<Bookmark> list) {
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new BookmarkAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    public void toggleDisable(boolean status) {
        progressBar.setVisibility(status ? View.INVISIBLE : View.VISIBLE);
        if (status) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void openInBrowser(String url) {
        startActivity(new Intent(BookmarkActivity.this, WebActivity.class)
                .putExtra("link", url));
    }

    @Override
    public void shareBookmarked(String url) {
        Intent shareIntent = Functions.sharePost(url);
        startActivity(shareIntent);
    }

    @Override
    public void removeBookmarked(String postId) {
        Log.d("Tags", postId);
        toggleDisable(false);
        SQLQueries newQueries = new SQLQueries(this);
        boolean deleted = newQueries.removeBookmarked(postId);
        if (deleted) {
            toggleDisable(true);
            Toast.makeText(BookmarkActivity.this, "Post removed from Bookmark",
                    Toast.LENGTH_SHORT).show();
            showNews(getPosts(null));
        }

    }
}