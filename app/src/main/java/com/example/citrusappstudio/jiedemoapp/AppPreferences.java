package com.example.citrusappstudio.jiedemoapp;

import android.content.Context;

public class AppPreferences {

    private static final String KEY_PREF_NUM_COLUMNS = "no_of_columns";
    private static final int DEFAULT_NUM_COLUMNS = 4;

    private static final String KEY_PREF_WEATHER_LOCATION = "weather_location";
    private static final String DEFAULT_WEATHER_LOCATION = "1600 Amphitheatre Parkway, Mountain View, CA 94043";

    public static int getNumColumns(Context context){
        return android.support.v7.preference.PreferenceManager
                .getDefaultSharedPreferences(context).getInt(KEY_PREF_NUM_COLUMNS, DEFAULT_NUM_COLUMNS);
    }
    public static String getWeatherLocation(Context context){
        return android.support.v7.preference.PreferenceManager
                .getDefaultSharedPreferences(context).getString(KEY_PREF_WEATHER_LOCATION, DEFAULT_WEATHER_LOCATION);
    }
}