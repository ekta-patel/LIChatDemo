package com.example.chatdemo.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.chatdemo.R;

public class AppSharedPrefManager {

    private static SharedPreferences PREFERENCE;

    public static void initInstance(Context context) {
        if (PREFERENCE == null) {
            PREFERENCE = context.getSharedPreferences(
                    context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        }
    }

    public static String getToken(String key) {
        return PREFERENCE.getString(key, "");
    }

    public static void setToken(String key, String value) {
        SharedPreferences.Editor editor = PREFERENCE.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void clear() {
        PREFERENCE.edit().clear().apply();
    }
}
