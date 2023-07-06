package com.hassanadeola.newsgo.interfaces;

public interface BookmarkListener {
    void openInBrowser(String url);

    void shareBookmarked(String url);

    void removeBookmarked(String postId);
}
