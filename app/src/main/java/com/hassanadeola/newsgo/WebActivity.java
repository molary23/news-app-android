package com.hassanadeola.newsgo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hassanadeola.newsgo.utils.Functions;

import java.util.Objects;

public class WebActivity extends AppCompatActivity {
    WebView webView;
    FloatingActionButton fab_share;

    public String url = "https://www.lucipost.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.webView);
        fab_share = findViewById(R.id.fab_share);

        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            url = extra.getString("link");
            webView.loadUrl(url);
        }

        Log.i("url", url);

        fab_share.setOnClickListener((View view) -> sharePost(url));

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

    public void sharePost(String url) {
        Intent shareIntent = Functions.sharePost(url);
        startActivity(shareIntent);
    }
}