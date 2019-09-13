package com.example.citrusappstudio.jiedemoapp;

import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class SettingsActivity extends PreferenceActivity {

    public static final String KEY_PREF_NUM_COLUMNS = "no_Of_Columns";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content,new SettingsFragment()).commit();
    }
}
