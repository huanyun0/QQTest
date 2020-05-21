package com.example.qqtest.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserUtil {
    public final String KEY_QQ = "qq";
    public final String KEY_NAME = "name";
    public final String KEY_IMAGE = "image";
    public final String KEY_MESSAGE = "message";

    private SharedPreferences preferences;

    public UserUtil(Context context) {
        final String SP_NAME = "QQTest.xml";
        context = context.getApplicationContext();
        preferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String def) {
        return preferences.getString(key, def);
    }

    public void clearAll() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
