package com.hassanadeola.newsgo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hassanadeola.newsgo.Models.NewsHeadlines;
import com.hassanadeola.newsgo.Models.SQLQueries;
import com.hassanadeola.newsgo.utils.Functions;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {
    NewsHeadlines headlines;

    TextView text_title, text_description, text_content, text_time, text_author;
    ImageView img;

    FloatingActionButton fab_options, fab_browser, fab_share, fab_bookmark;

    boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        headlines = (NewsHeadlines) getIntent().getSerializableExtra("data");

        text_author = findViewById(R.id.text_detail_author);
        text_title = findViewById(R.id.text_detail_title);
        text_content = findViewById(R.id.text_detail_content);
        text_time = findViewById(R.id.text_detail_time);
        text_description = findViewById(R.id.text_detail_description);

        img = findViewById(R.id.text_detail_img);

        fab_browser = findViewById(R.id.fab_browser);
        fab_options = findViewById(R.id.fab_options);
        fab_share = findViewById(R.id.fab_share);
        fab_bookmark = findViewById(R.id.fab_bookmark);


        text_title.setText(headlines.getTitle());
        text_author.setText(headlines.getAuthor());
        text_time.setText(headlines.getPublishedAt());
        text_description.setText(headlines.getDescription());
        text_content.setText(headlines.getContent());
        Picasso.get().load(headlines.getUrlToImage()).into(img);
        img.setContentDescription(headlines.getTitle());

        fab_options.setOnClickListener((View view) -> showFabs());

        fab_share.setOnClickListener((View view) -> sharePost(headlines.getUrl()));

        fab_browser.setOnClickListener((View view) -> {
            startActivity(new Intent(DetailsActivity.this, WebActivity.class).putExtra("link", headlines.getUrl()));
        });
        
        fab_bookmark.setOnClickListener((View view) -> bookmarkPost(headlines));


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showFabs() {
        isVisible = !isVisible;
        if (isVisible) {
            fab_browser.setVisibility(View.VISIBLE);
            fab_share.setVisibility(View.VISIBLE);
            fab_bookmark.setVisibility(View.VISIBLE);
        } else {
            fab_browser.setVisibility(View.GONE);
            fab_share.setVisibility(View.GONE);
            fab_bookmark.setVisibility(View.GONE);

        }

    }

    public void sharePost(String url) {
        Intent shareIntent = Functions.sharePost(url);
        startActivity(shareIntent);
    }

    public void bookmarkPost(NewsHeadlines headlines) {
        SQLQueries newQuery = new SQLQueries(this);
        boolean added = newQuery.addBookmark(headlines.getTitle(), headlines.getSource().getName(), headlines.getUrl(), headlines.getUrlToImage());
        String message = "Post has been already bookmarked";

        if (added) {
            message = "Post bookmarked";
        }
        Toast.makeText(DetailsActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}