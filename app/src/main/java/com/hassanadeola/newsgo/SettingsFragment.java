package com.hassanadeola.newsgo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.hassanadeola.newsgo.Models.FBQueries;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {
    ListPreference themes;
    SwitchPreference push_notify, email_notify;

    SharedPreferences sharedPreferences;

    String token;

    FBQueries fbQueries;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        sharedPreferences = requireActivity()
                .getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        fbQueries = new FBQueries(getActivity(), token);


        push_notify = findPreference("pref_push_val");
        email_notify = findPreference("pref_email_val");
        themes = findPreference("pref_theme_val");

        email_notify.setOnPreferenceChangeListener(this);
        push_notify.setOnPreferenceChangeListener(this);
        themes.setOnPreferenceChangeListener(this);


    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String preferenceKey = preference.getKey();
        boolean isActive;
        if (preferenceKey.equalsIgnoreCase("pref_push_val")) {
            isActive = (Boolean) newValue;
            // fbQueries.updateSettings(isActive, "pushNotify");
            Toast.makeText(requireActivity(), "Settings updated", Toast.LENGTH_LONG).show();
        } else if (preferenceKey.equalsIgnoreCase("pref_email_val")) {
            isActive = (Boolean) newValue;
            //   fbQueries.updateSettings(isActive, "emailNotify");
            Toast.makeText(requireActivity(), "Settings updated 1", Toast.LENGTH_LONG).show();
        } else {
            // Add Theme to shared preferences
        }
        return true;
    }

}
