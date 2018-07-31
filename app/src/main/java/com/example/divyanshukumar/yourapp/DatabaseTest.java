package com.example.divyanshukumar.yourapp;

import android.content.ContentValues;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.divyanshukumar.yourapp.data.AppContract;
import com.example.divyanshukumar.yourapp.data.AppContract.AppEntry;
import com.example.divyanshukumar.yourapp.data.AppDbHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Temp  Class , just to check how many elements are in data base and to update the database
 */
public class DatabaseTest extends AppCompatActivity {

    AppDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);

        mDbHelper = new AppDbHelper(this);


        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                AppEntry._ID,
                AppEntry.COLUMN_APP_NAME,
                AppEntry.COLUMN_APP_PACKAGE
        };

        Cursor cursor = db.query(
                AppEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        TextView displayView = (TextView) findViewById(R.id.textView2);


        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            displayView.setText("Number of rows in Apps database table: " + cursor.getCount() + "\n\n");

            displayView.append(AppEntry._ID + " - " +
            AppEntry.COLUMN_APP_NAME + " - " +
            AppEntry.COLUMN_APP_PACKAGE + "\n");

            int idColumnIndex = cursor.getColumnIndex(AppEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(AppEntry.COLUMN_APP_NAME);
            int packageColumnInde = cursor.getColumnIndex(AppEntry.COLUMN_APP_PACKAGE);

            while(cursor.moveToNext()){

                int currentId = cursor.getInt(idColumnIndex);

                String currentName = cursor.getString(nameColumnIndex);

                String currentPack = cursor.getString(packageColumnInde);

                displayView.append("\n" + currentId + " - " +
                currentName + " - " + currentPack);

            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void instertApps() {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AppEntry.COLUMN_APP_NAME, "YouTube");
        values.put(AppEntry.COLUMN_APP_PACKAGE, "com.google.youtube");


        long newRowId = db.insert(AppEntry.TABLE_NAME, null, values);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_database, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:

//                instertApps();
                AllAppInfo();
                displayDatabaseInfo();

                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:

                deleteAll();
                displayDatabaseInfo();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void AllAppInfo() {

        SQLiteDatabase db1 = mDbHelper.getWritableDatabase();

        ContentValues values1 = new ContentValues();

        final PackageManager pm = this.getPackageManager();
//get a list of installed apps.
        final List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);


        /**
         * this loop fill the list of Apps that will be will shown on screen
         */

        for (int i = 0; i< packages.size(); i++) {

            ApplicationInfo packageInfo = packages.get(i);


            String packName = packageInfo.packageName;

            /**
             * AppName
             */
            String appName = pm.getApplicationLabel(packageInfo).toString();


            values1.put(AppEntry.COLUMN_APP_NAME, appName);
            values1.put(AppEntry.COLUMN_APP_PACKAGE, packName);

            db1.insert(AppEntry.TABLE_NAME, null, values1);
        }

    }

    public void deleteAll(){

        SQLiteDatabase db2 = mDbHelper.getWritableDatabase();

        db2.delete(AppEntry.TABLE_NAME, null, null);

        String deleteRows = "UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='" + AppEntry.TABLE_NAME + "';";

        db2.execSQL(deleteRows);


    }

}

