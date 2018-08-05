package com.example.divyanshukumar.yourapp;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.divyanshukumar.yourapp.data.AppContract.AppEntry;
import com.example.divyanshukumar.yourapp.data.AppDbHelper;
import com.example.divyanshukumar.yourapp.data.SharedPrefClass;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    static private NotificationManager notificationManager;


//    private AdView mAdView;

    AdRequest adRequest;

    Context thisContext = this;

    RecyclerView list;

    LinearLayoutManager mLayoutManager;

    MyAdapter adapter;

    SearchView searchView;

    private boolean isChecked = false;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem checkable = menu.findItem(R.id.darkThemeButton);
        checkable.setChecked(isChecked);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.app_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false);

        searchView.requestFocus();

        searchView.setQueryHint("Search YourApp...");

        ImageView searchIcon = (ImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(R.drawable.ic_face_cross);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                try {
                    String ApplicationPackageName = adapter.mDataset.get(0);

                    Intent intent = thisContext.getPackageManager().getLaunchIntentForPackage(ApplicationPackageName);
                    if (intent != null) {

                        thisContext.startActivity(intent);

                    } else {

                        Toast.makeText(thisContext, ApplicationPackageName + " Error, Please Try Again.", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){

                    Toast.makeText(MainActivity.this, "can not open",Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                // filter recycler view when text is changed
                adapter.getFilter().filter(query);

                return false;
            }
        });
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.settingsButton:
                startNotification();

                break;

            case R.id.darkThemeButton:
            {
                if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
                    item.setChecked(true);
                }

                if(item.isChecked()){
                    item.setChecked(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                    /**
                     * write to shared preference
                     */
                    SharedPrefClass sharedPrefClass = new SharedPrefClass(getApplicationContext());
                    sharedPrefClass.putTheme("DarkTheme", false);

                    restartApp();

                }
                else{
                        item.setChecked(true);

                    if(new SharedPrefClass(getApplicationContext()).getDefaults("seenAd")) {
                        Toast.makeText(this, "Turned on dark theme", Toast.LENGTH_LONG).show();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        /**
                         * write to shared preference
                         */
                        SharedPrefClass sharedPrefClass = new SharedPrefClass(getApplicationContext());
                        sharedPrefClass.putTheme("DarkTheme", true);

                        restartApp();
                    }

                    else{
                        Intent intent = new Intent(MainActivity.this, AdPopUp.class);
                        startActivity(intent);
                    }
                }


            }


        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        startNotification();

        /**Dark theme implementation STARTS HERE*/

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES & new SharedPrefClass(getApplicationContext()).isDarkTheme("DarkTheme")){
            setTheme(R.style.dark_theme);
        }
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**Dark theme implementation ENDS HERE*/


//
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

/**
 * Toolbar commands
 */
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /**
         * Check whether Database is empty or not and lauch the particular Asynktask object
         *
         * if there is data update the data
         *
         * if not , load the data and show progress bar
         */
        if(new installedApps(this).isThereAnyData()) {

            LoadData1 object = new LoadData1();

            object.execute();

        }
        else{

            UpdateData1 object1 = new UpdateData1();

            object1.execute();

        }

    }

    public void restartApp(){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }


    //hello0

    static Notification notification = new Notification(R.drawable.ic_search, null,
            System.currentTimeMillis());

    private void startNotification(){
        String ns = Context.NOTIFICATION_SERVICE;
        notificationManager = (NotificationManager) getSystemService(ns);



        RemoteViews notificationView = new RemoteViews(getPackageName(),
                R.layout.custom_notification);

        //the intent that is started when the notification is clicked (works)
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        notification.contentView = notificationView;
        notification.contentIntent = pendingNotificationIntent;
        notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;

        //this is the intent that is supposed to be called when the
        //button is clicked
        Intent switchIntent = new Intent(this, switchButtonListener.class);
        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(this, 0,
                switchIntent, 0);

        notificationView.setOnClickPendingIntent(R.id.clearButton,
                pendingSwitchIntent);

        notificationManager.notify(1, notification);


    }


    public static class switchButtonListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "button clicked ", Toast.LENGTH_SHORT).show();
            notificationManager.cancelAll();
        }
    }






//
//    /**
//     * For back button , when pressed it exit the app
//     * @param item
//     * @return
//     */
////    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        finish();
//        System.exit(0);
//
//                return super.onOptionsItemSelected(item);
//
//
//    }

//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        int id = item.getItemId();
//
//        if (id == R.id.tag_red) {
//
//            Intent intent = new Intent(thisContext, TestingTagSearch.class);
//
//            startActivity(intent);
//
////            Toast toast = Toast.makeText(thisContext, "hello", Toast.LENGTH_LONG);
////            toast.show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


//    public void nextActivity(View view) {
//
//        Intent intent = new Intent(MainActivity.this, DatabaseTest.class);
//        startActivity(intent);
//    }


    /**
     * show progress bar and load data in SQL
     */
    public class LoadData1 extends AsyncTask<Void, Void ,Void>{

        ProgressDialog progressDialog;

        AppDbHelper DbHelper;


        LoadData1(){

            DbHelper = new AppDbHelper(thisContext);

        }

        @Override
        protected void onPreExecute(){

            progressDialog = ProgressDialog.show(thisContext, "First Time Loading", "LaLaLaLaLaLaLa");


        }


        @Override
        protected Void doInBackground(Void... voids) {

            SQLiteDatabase db1 = DbHelper.getWritableDatabase();

            ContentValues values1 = new ContentValues();

            final PackageManager pm = thisContext.getPackageManager();
//get a list of installed apps.
            final List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);


            /**
             * this loop fill the list of Apps that will be will shown on screen
             */

            for (int i = 0; i < packages.size(); i++) {

                ApplicationInfo packageInfo = packages.get(i);


                String packName = packageInfo.packageName;

                /**
                 * AppName
                 */
                String appName = pm.getApplicationLabel(packageInfo).toString();

                /**
                 * Secret ingredient
                 */
                if(thisContext.getPackageManager().getLaunchIntentForPackage(packName) != null) {
                    values1.put(AppEntry.COLUMN_APP_NAME, appName);
                    values1.put(AppEntry.COLUMN_APP_PACKAGE, packName);

                    db1.insert(AppEntry.TABLE_NAME, null, values1);
                }


            }
            return null;
        }



        @Override
        protected void onPostExecute(Void voids){

            progressDialog.dismiss();

            list = (RecyclerView) findViewById(R.id.app_List);

            list.setHasFixedSize(true);


            mLayoutManager = new LinearLayoutManager(thisContext);

            list.setLayoutManager(mLayoutManager);

            adapter = new MyAdapter(thisContext, new installedApps(thisContext).getAppPackage(), new installedApps(thisContext).getAppName());

            list.setAdapter(adapter);

//            SearchView searchView = (SearchView) findViewById(R.id.search_View);
//
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
////                list.setAdapter(adapter);
//
//                    adapter.getFilter().filter(newText);
//
//
//                    return false;
//                }
//
//            });

        }
    }

    /**
     * data already loaded, replace the old data with new data in background and notify data set changed in postExe.
     */
    public class UpdateData1 extends AsyncTask<Void, Void,Void>{

        AppDbHelper DBhelper;


        UpdateData1(){

            DBhelper = new AppDbHelper(thisContext);
        }

        @Override
        protected void onPreExecute(){


            list = findViewById(R.id.app_List);

            list.setHasFixedSize(true);
//            list.setItemViewCacheSize(20);
//            list.setDrawingCacheEnabled(true);
//            list.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);


            mLayoutManager = new LinearLayoutManager(thisContext);
            list.setLayoutManager(mLayoutManager);

            adapter = new MyAdapter(thisContext, new installedApps(thisContext).getAppPackage(), new installedApps(thisContext).getAppName());


            list.setAdapter(adapter);


//
//            SearchView searchView = (SearchView) findViewById(R.id.search_View);
//
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
////                list.setAdapter(adapter);
//
//                    adapter.getFilter().filter(newText);
//
//
//                    return false;
//                }
//
//            });

        }


        @Override
        protected Void doInBackground(Void... voids) {


            SQLiteDatabase db1 = DBhelper.getWritableDatabase();

            db1.execSQL("DELETE FROM " + AppEntry.TABLE_NAME + ";");
            db1.execSQL("DELETE FROM sqlite_sequence WHERE name = '" + AppEntry.TABLE_NAME + "';");

            ContentValues values1 = new ContentValues();

            final PackageManager pm = thisContext.getPackageManager();
//get a list of installed apps.
            final List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);


            /**
             * this loop fill the list of Apps that will be will shown on screen
             */

            for (int i = 0; i < packages.size(); i++) {

                ApplicationInfo packageInfo = packages.get(i);


                String packName = packageInfo.packageName;

                /**
                 * AppName
                 */
                String appName = pm.getApplicationLabel(packageInfo).toString();

                if(thisContext.getPackageManager().getLaunchIntentForPackage(packName) != null) {
                    values1.put(AppEntry.COLUMN_APP_NAME, appName);
                    values1.put(AppEntry.COLUMN_APP_PACKAGE, packName);

                    db1.insert(AppEntry.TABLE_NAME, null, values1);
                }
            }


            /**
             * Check for 15 days gone or not
             */
//            if(!(new SharedPrefClass(thisContext).getDefaults("seenAd"))){
//
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
////                restartApp();
//
//            }

            return null;
        }


        @Override
        protected void onPostExecute(Void voids){

            adapter.notifyDataSetChanged();
        }

    }

}


