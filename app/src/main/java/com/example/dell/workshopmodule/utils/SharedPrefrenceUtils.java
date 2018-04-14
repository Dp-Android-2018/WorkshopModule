package com.example.dell.workshopmodule.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by DELL on 04/03/2018.
 */

public class SharedPrefrenceUtils {
    Context context;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    public SharedPrefrenceUtils(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(ConfigurationFile.SharedPrefConstants.SHARED_PREF_NAME, context.MODE_PRIVATE);
        editor = context.getSharedPreferences(ConfigurationFile.SharedPrefConstants.SHARED_PREF_NAME, context.MODE_PRIVATE).edit();
    }


    public void addStringToSharedPrederances(String title, String value) {

            editor.putString(title, value);
            editor.commit();
    }

    public String getStringFromSharedPrederances(String title) {
        String value = "";
        value = prefs.getString(title, "");
        return value;
    }

    public void addIntegerToSharedPrederances(String title, int value) {

        editor.putInt(title, value);
        editor.commit();
    }

    public int getIntegerFromSharedPrederances(String title) {
        int value = 0;
        value = prefs.getInt(title, 0);
        return value;
    }

    public void addBooleanToSharedPrederances(String title, Boolean value) {

        editor.putBoolean(title, value);
        editor.commit();
    }

    public Boolean getBooleanFromSharedPrederances(String title) {
        Boolean value = false;
        value = prefs.getBoolean(title, false);
        return value;
    }

    public void saveObjectToSharedPreferences(String savedObjectName, Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        editor.putString(savedObjectName, json);
        editor.commit();
    }

    public Object getSavedObject(String savedObjectName, Class objectClass) {
        Gson gson = new Gson();
        String json = prefs.getString(savedObjectName, "");
        return gson.fromJson(json, objectClass);
    }

    public void clearToken()
    {   editor.clear();
        editor.apply();
    }
}
