package com.oblique.appsearch.search.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefClass {

    Context context;

    public SharedPrefClass(Context mContext){

        context = mContext;
    }

//    public void setDefaults(String expiry, long value) {
//        SharedPreferences preferences = context.getSharedPreferences("myFile", Context.MODE_PRIVATE);
//
////         SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putLong(expiry, value);
//        editor.apply();
//
//    }
//
//    public boolean getDefaults(String expiry) {
//        SharedPreferences preferences = context.getSharedPreferences("myFile", Context.MODE_PRIVATE);
//
//        Date currentTime = Calendar.getInstance().getTime();
//        long time = currentTime.getTime();
//
//        long expiryTime = preferences.getLong(expiry, 0);
//
////        Log.v("MainActivity", "hahaha "+ preferences.getLong(expiry, 0));
//
//         if(expiryTime > time){
//
//             return true;
//         }
//         else
//             return false;
//
//    }

    public void putTheme(String key, boolean value){

        SharedPreferences darktheme = context.getSharedPreferences("Theme", Context.MODE_PRIVATE);

        SharedPreferences.Editor themeEditor = darktheme.edit();

        themeEditor.putBoolean(key, value);
        themeEditor.apply();
    }

    public boolean isDarkTheme(String key){
        SharedPreferences darktheme = context.getSharedPreferences("Theme", Context.MODE_PRIVATE);

        return darktheme.getBoolean(key, false);

    }
}
