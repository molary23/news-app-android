package com.hassanadeola.newsgo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.hassanadeola.newsgo.Models.NewsApiResponse;
import com.hassanadeola.newsgo.Models.NewsHeadlines;
import com.hassanadeola.newsgo.Models.SQLQueries;
import com.hassanadeola.newsgo.Request.RequestManager;
import com.hassanadeola.newsgo.interfaces.OnFetchDataListener;
import com.hassanadeola.newsgo.interfaces.SelectListener;
import com.hassanadeola.newsgo.utils.Functions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {
    RecyclerView recyclerView;
    CustomAdapter adapter;
    FrameLayout progressBar;

    MaterialButton btn_general, btn_sport, btn_business, btn_entertainment,
            btn_health, btn_science, btn_technology;
    SearchView searchView;

    LinearLayout rootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        btn_general = findViewById(R.id.btn_general);
        btn_sport = findViewById(R.id.btn_sport);
        btn_health = findViewById(R.id.btn_health);
        btn_business = findViewById(R.id.btn_business);
        btn_entertainment = findViewById(R.id.btn_entertainment);
        btn_science = findViewById(R.id.btn_science);
        btn_technology = findViewById(R.id.btn_technology);
        searchView = findViewById(R.id.searchView);
        rootView = findViewById(R.id.rootView);


        btn_general.setOnClickListener(this);
        btn_sport.setOnClickListener(this);
        btn_health.setOnClickListener(this);
        btn_business.setOnClickListener(this);
        btn_entertainment.setOnClickListener(this);
        btn_science.setOnClickListener(this);
        btn_technology.setOnClickListener(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            RequestManager requestManager = new RequestManager(MainActivity.this);

            @Override
            public boolean onQueryTextSubmit(String s) {
                toggleDisable(false);
                requestManager.getNewsHeadlines(listener, "general", s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    requestManager.getNewsHeadlines(listener, "general", null);
                    return true;
                }
                return false;
            }
        });


        toggleDisable(false);

        RequestManager requestManager = new RequestManager(this);
        requestManager.getNewsHeadlines(listener, "general", null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_bookmarks) {
            navToPage(BookmarkActivity.class);

        } else if (item.getItemId() == R.id.menu_settings) {
            navToPage(SettingsActivity.class);
        }
        return true;
    }

    public void navToPage(Class activityClass) {
        Intent startIntent = new Intent(MainActivity.this, activityClass);
        startActivity(startIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        searchView.setQuery("", false);
        rootView.requestFocus();
    }

    private final OnFetchDataListener<NewsApiResponse> listener =
            new OnFetchDataListener<NewsApiResponse>() {
                @Override
                public void onFetchData(List<NewsHeadlines> list, String message) {
                    if (list.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Data not available",
                                Toast.LENGTH_SHORT).show();
                    } else {

                        showNews(list);
                        toggleDisable(true);

                    }
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(MainActivity.this, "Error Occurred",
                            Toast.LENGTH_SHORT).show();
                }
            };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, list, this);
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
    public void onNewsClicked(NewsHeadlines headlines) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class)
                .putExtra("data", headlines));
    }

    @Override
    public void onNewsShared(String url) {
        Intent shareIntent = Functions.sharePost(url);
        startActivity(shareIntent);
    }

    @Override
    public void onNewsBookmarked(NewsHeadlines headlines) {
        SQLQueries newQuery = new SQLQueries(this);
        boolean added = newQuery.addBookmark(headlines.getTitle(), headlines.getSource().getName(),
                headlines.getUrl(), headlines.getUrlToImage());
        String message = "Post has been already bookmarked";

        if (added) {
            message = "Post bookmarked";
        }
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View view) {
        resetColor();
        MaterialButton button = (MaterialButton) view;
        button.setBackgroundColor(Color.WHITE);
        button.setTextColor(Color.parseColor("#0197F6"));
        button.setIconTintResource(R.color.primary);

        String category = button.getText().toString();
        toggleDisable(false);
        RequestManager requestManager = new RequestManager(this);
        requestManager.getNewsHeadlines(listener, category, null);
    }


    public void resetColor() {
        MaterialButton[] btnArray = {btn_general, btn_sport, btn_health, btn_business,
                btn_entertainment, btn_science, btn_technology};
        for (MaterialButton btn : btnArray) {
            btn.setBackgroundColor(Color.parseColor("#0197F6"));
            btn.setTextColor(Color.WHITE);
            btn.setIconTintResource(R.color.white);
        }
    }
}