package com.hassanadeola.newsgo.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class SQLQueries {
    private SQLiteDatabase db = null;

    private Cursor cursor;

    public SQLQueries(Context context) {
        String USER_DB = "UserDB";
        db = context.openOrCreateDatabase(USER_DB, Context.MODE_PRIVATE, null);
    }

    public boolean addBookmark(String postTitle, String postSource, String postUrl, String postImage) {
        boolean isBookmarked = isBookmarked(new String[]{postTitle, postSource});
        if (!isBookmarked) {

            db.execSQL("INSERT INTO bookmarks (postTitle, postSource,postUrl, postImage) " +
                    "Values ( \"" + postTitle + "\",\"" + postSource + "\", \"" + postUrl + "\", \"" + postImage + "\");");
            return true;
        } else {
            return false;
        }

    }

    public List<Bookmark> getBookmarkedPosts(String query) {
        if (query == null) {
            cursor = db.rawQuery("SELECT * FROM " +
                    "bookmarks ORDER BY bookId DESC", null);
        } else {
            String[] where = {"%" + query + "%", "%" + query + "%"};
            cursor = db.rawQuery("SELECT * FROM " +
                    "bookmarks WHERE postSource LIKE ? OR postTitle LIKE ? ORDER BY bookId DESC", where);
        }


        List<Bookmark> bookmarkList = new ArrayList<>();
        if (cursor.getCount() > 0) // if there are no records
        {
            while (cursor.moveToNext()) {
                bookmarkList.add(new Bookmark(cursor.getString(0), cursor.getString(3),
                        cursor.getString(1), cursor.getString(2), cursor.getString(4)));
            }
        }
        return bookmarkList;
    }

    public boolean isBookmarked(String[] postDetails) {
        boolean isBookmarked = false;
        cursor = db.rawQuery("SELECT * FROM bookmarks WHERE postTitle = ? AND postSource = ?", postDetails);

        if (cursor != null) // if there is a table
        {
            if (cursor.getCount() > 0) // if there are no records
            {
                isBookmarked = true;
            }
        }
        return isBookmarked;
    }

    public boolean removeBookmarked(String bookId) {
        String[] where = {bookId};
        return db.delete("bookmarks", "bookId = ?", where) > 0;
    }

    public void addUser(String username, String email, String uuid) {
        db.execSQL("INSERT INTO users (username, email, uuid) Values ( \"" + username + "\", \"" + email + "\", \"" + uuid + "\");");
    }

    public String getUUID() {
        String uuid = null;
        cursor = db.rawQuery("SELECT uuid FROM users", null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                uuid = cursor.getString(0);
            }
        }
        return uuid;
    }

    public String getEmail() {
        String email = null;
        cursor = db.rawQuery("SELECT email FROM users", null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                email = cursor.getString(0);
            }
        }
        return email;
    }
}