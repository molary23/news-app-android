package com.hassanadeola.newsgo.utils;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

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
}
