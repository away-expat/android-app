package com.away_expat.away.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    private static final String TOKEN = "token";

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setToken(Context context, String token) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(TOKEN, token);
        editor.apply();
    }

    public static String getToken(Context context) {
        return getPreferences(context).getString(TOKEN, null);
    }
}