package com.hassanadeola.newsgo.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

public class Functions {


    public static Intent sharePost(String content) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        return shareIntent;
    }

    public static AlertDialog.Builder createAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }

    public static Intent navToPage(Context context, Class<?> activityClass) {
        return new Intent(context, activityClass);
    }

    public static void changeTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userPreference",
                Context.MODE_PRIVATE);

        String theme = sharedPreferences.getString("theme", "");

        if (theme == null || theme.isEmpty() || theme.equalsIgnoreCase("light")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (theme.equalsIgnoreCase("dark")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

    public static void instantThemeChange(String theme) {
        if (theme.equalsIgnoreCase("light")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (theme.equalsIgnoreCase("dark")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        }
    }
}
