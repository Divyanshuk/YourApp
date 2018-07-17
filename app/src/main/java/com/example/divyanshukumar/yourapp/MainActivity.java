package com.example.divyanshukumar.yourapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * it is an array list for
         * List of apps that will be shown on screen
         * this array list will be filled by installedApps.class
         *
         */

        final ArrayList<String> app_List = new ArrayList<String>();


       final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, app_List);

        final ListView list = (ListView) findViewById(R.id.app_List);

        list.setAdapter(adapter);

        /**
        *this calls the method which is down below
         */
       showInstalledApps(app_List, list);


        /**
         * Simple searchView which will filter the list
         */
        SearchView searchView = (SearchView) findViewById(R.id.search_View);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);



                return false;
            }
        });

//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                String apps = (String) list.getAdapter().getItem(position);
//
////                Intent intent = new Intent(Intent.ACTION_SEND);
////                intent.setType("text/html");
////                intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
////                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
////                intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
//
////                list.getContext().startActivity(Intent.createChooser(intent, "Send Email"));
//
//
//
//                Intent intent = getPackageManager().getLaunchIntentForPackage();
//
//                list.getContext().startActivity(intent);
//
//            }
//        });


    }


    /**
     * makes the object of another class and call its method
     * @param app_List
     * @param list
     */
    private void showInstalledApps(ArrayList<String> app_List,final ListView list ) {

        installedApps hi = new installedApps();
        hi.appss(app_List, list, this);


            }


    }

