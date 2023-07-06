package com.hassanadeola.newsgo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private SQLiteDatabase db = null;
    private final String USER_DB = "UserDB";

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        db = this.openOrCreateDatabase(USER_DB, MODE_PRIVATE, null);

        Runnable runnable = new Runnable() {
            public void run() {
                dbSetup();
            }
        };

        Handler handler = new android.os.Handler();
        handler.postDelayed(runnable, 5000);

        //  handler.removeCallbacks(runnable);

      /*  new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        dbSetup();
                    }
                }, 5000);*/


    }

    public void dbSetup() {


        ////////////////
        // tblThing ///
        db.execSQL("CREATE TABLE IF NOT EXISTS users" +
                " (userId INTEGER PRIMARY KEY AUTOINCREMENT," +
                " token VARCHAR," +
                " username VARCHAR," +
                " email VARCHAR," +
                " createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);");
        db.execSQL("CREATE TABLE IF NOT EXISTS bookmarks" +
                " (bookId INTEGER PRIMARY KEY AUTOINCREMENT," +
                " postSource VARCHAR," +
                " postUrl VARCHAR," +
                " postTitle VARCHAR," +
                " postImage VARCHAR," +
                " createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);");


        cursor = db.rawQuery("SELECT username FROM users ORDER BY userId DESC", null);

        if (cursor != null) // if there is a table
        {
            if (cursor.getCount() <= 0) // if there are no records
            {

                navToPage(LoginActivity.class);
            } else {
                navToPage(MainActivity.class);
            }
        }
    }

    public void navToPage(Class activityClass) {
        Intent startIntent = new Intent(SplashScreenActivity.this, activityClass);
        startActivity(startIntent);
    }
}