package com.example.divyanshukumar.yourapp;


import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.example.divyanshukumar.yourapp.data.AppContract.AppEntry;
import com.example.divyanshukumar.yourapp.data.AppDbHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity{


    Context thisContext = this;

     RecyclerView list;

     LinearLayoutManager mLayoutManager;

     MyAdapter adapter;

     SearchView searchView;

   static int detectDoubleTap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DbHelper = new AppDbHelper(this);
/**
 * Toolbar commands
 */
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.app_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false);

        searchView.requestFocus();

        searchView.setQueryHint("Search YourApp..");

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
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


    /**
     * For back button , when pressed it exit the app
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        finish();
        System.exit(0);

                return super.onOptionsItemSelected(item);


    }



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

            list = (RecyclerView) findViewById(R.id.app_List);

            list.setHasFixedSize(true);


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


            return null;
        }


        @Override
        protected void onPostExecute(Void voids){

            adapter.notifyDataSetChanged();
        }

    }




    }


