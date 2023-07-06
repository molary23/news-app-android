package com.hassanadeola.newsgo.interfaces;

import com.hassanadeola.newsgo.Models.NewsHeadlines;

public interface SelectListener {
    void onNewsClicked(NewsHeadlines headlines);
    void onNewsShared(String url);
    void onNewsBookmarked(NewsHeadlines headlines);
}
