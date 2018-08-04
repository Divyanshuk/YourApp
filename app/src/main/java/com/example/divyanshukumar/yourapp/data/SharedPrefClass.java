package com.example.divyanshukumar.yourapp.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefClass {

    Context context;

    public SharedPrefClass(Context mContext){

        context = mContext;
    }

    public void setDefaults(String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences("myFile", Context.MODE_PRIVATE);

//         SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();

    }

    public boolean getDefaults(String key) {
        SharedPreferences preferences = context.getSharedPreferences("myFile", Context.MODE_PRIVATE);

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, false);
    }
}
