package com.hassanadeola.newsgo;

import static com.hassanadeola.newsgo.utils.Functions.createAlertDialog;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceDataStore;

import com.hassanadeola.newsgo.Models.FBQueries;
import com.hassanadeola.newsgo.Models.SQLQueries;

public class DataStore extends PreferenceDataStore {
    SharedPreferences sharedPreferences;
    String uuid = null;

    FBQueries fbQueries;

    String sqlId = null;
    Context context;
    SharedPreferences.Editor editor;

    public DataStore(Context context) {
        this.context = context;
    }

    @Override
    public void putBoolean(String key, @Nullable boolean value) {
        sharedPreferences = context.getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        uuid = sharedPreferences.getString("uuid", "");

        SQLQueries newQuery = new SQLQueries(context);
        sqlId = newQuery.getUUID();
        fbQueries = new FBQueries(context, uuid);

        boolean isActive = value;
        if (key.equalsIgnoreCase("pref_push_val")) {
            saveNotify(isActive, "pushNotify");
        } else if (key.equalsIgnoreCase("pref_email_val")) {
            saveNotify(isActive, "emailNotify");
        }


    }

    @Override
    public void putString(String key, @Nullable String value) {
        if (key.equalsIgnoreCase("pref_theme_val")) {
            sharedPreferences = context.getSharedPreferences("userPreference", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("theme", value);
            editor.commit();
        }
    }

    @Override
    @Nullable
    public String getString(String key, @Nullable String defValue) {
        // Retrieve the value
        return null;
    }

    public void saveNotify(boolean isActive, String name) {
        if (uuid != null || sqlId != null) {
            fbQueries.updateSettings(isActive, name);
        } else {
            AlertDialog.Builder builder = createAlertDialog(context, "Not Logged In",
                    "You can only change this settings when logged in");
            builder.show();
        }

    }
}
