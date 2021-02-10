package com.hache.appentrepatas.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static  final String APP_SETTING_FILE = "APP_SETTING";

    private SharedPreferencesManager() {}

    private static SharedPreferences getSharedPreferences()  {
        return MyApp.getContext().getSharedPreferences(APP_SETTING_FILE, Context.MODE_PRIVATE);
    }

    public static void setSomeStringValue(String dataLabel, String dataValue) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(dataLabel, dataValue);
        editor.commit();
    }

    public static String getSomeStringValue(String dataLabel) {
        return  getSharedPreferences().getString(dataLabel, null);
    }

    public static void setSomeIntValue(String dataLabel, int dataValue) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(dataLabel, dataValue);
        editor.commit();
    }

    public static int getSomeIntValue(String dataLabel) {
        return  getSharedPreferences().getInt(dataLabel, 0);
    }

    public static void remove(String dataLabel) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove(dataLabel);
        editor.commit();
    }
}
