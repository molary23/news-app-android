package com.hassanadeola.newsgo;

import static com.hassanadeola.newsgo.utils.Functions.instantThemeChange;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.hassanadeola.newsgo.Models.FBQueries;
import com.hassanadeola.newsgo.Models.SQLQueries;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    ListPreference themes;
    SwitchPreference push_notify, email_notify;

    SharedPreferences sharedPreferences;

    String uuid = null, theme = null;

    FBQueries fbQueries;

    String sqlId = null;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        sharedPreferences = requireActivity()
                .getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        uuid = sharedPreferences.getString("token", "");
        theme = sharedPreferences.getString("theme", "");

        SQLQueries newQuery = new SQLQueries(requireActivity());
        sqlId = newQuery.getUUID();

        fbQueries = new FBQueries(getActivity(), uuid);

        themes = findPreference("pref_theme_val");

        if (theme != null) {
            themes.setValue(theme);
            themes.setSummary(theme);

        }

        if (sqlId != null) {
            push_notify = findPreference("pref_push_val");
            email_notify = findPreference("pref_email_val");
            email_notify.setVisible(true);
            push_notify.setVisible(true);
        }


        DataStore dataStore = new DataStore(requireActivity());
        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.setPreferenceDataStore(dataStore);

        themes.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String preferenceKey = preference.getKey(),
                themeValue = String.valueOf(newValue);
        if (preferenceKey.equalsIgnoreCase("pref_theme_val")) {
            themes.setSummary(themeValue);
            instantThemeChange(themeValue);
        }
        return true;
    }
}
