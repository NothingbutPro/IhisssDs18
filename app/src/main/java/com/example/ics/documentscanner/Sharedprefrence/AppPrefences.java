package com.example.ics.documentscanner.Sharedprefrence;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hvantage on 9/20/2017.
 */

public class AppPrefences {

    public static final String SHARED_PREFERENCE_NAME = "INSURENCE";
    public static final String CUSTOMERID = "customerId";
    public static final String PANCODE = "pancode";

    public static String getCustomerid(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(CUSTOMERID, "");
    }
    public static void setCustomerid(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CUSTOMERID, value);
        editor.commit();
    }



    public static String getPancode(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(PANCODE, "");
    }
    public static void setPancode(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PANCODE, value);
        editor.commit();
    }



}