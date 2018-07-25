package com.example.divyanshukumar.yourapp;


import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * this is RecyclerView
         *
         * variable name == list
         */

        final RecyclerView list = (RecyclerView) findViewById(R.id.app_List);

        list.setHasFixedSize(true);


        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(mLayoutManager);


        /**
         * This is customAdapter
         *
         * There are two param, 2nd one takes the package name
         */
        final MyAdapter adapter = new MyAdapter(this, new installedApps(this).getAllAppInfo(), new installedApps(this).getNameOf());


        list.setAdapter(adapter);


        /**
         * Simple searchView which will filter the list through Custom Search
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

    }

//    public ArrayList<String> getAllAppInfo() {
//
//        ArrayList<String> PKname = new ArrayList<String>();
//
//        final PackageManager pm = this.getPackageManager();
////get a list of installed apps.
//        final List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
//
//
//        /**
//         * this loop fill the list of Apps that will be will shown on screen
//         */
//        for (int i = 0; i< packages.size(); i++) {
//
//            ApplicationInfo packageInfo = packages.get(i);
//
//
//            PKname.add(packageInfo.packageName);
//
//            /**
//             * AppName
//             */
////            String appName = pm.getApplicationLabel(packageInfo).toString();
//
//
//
//        }
//
//        return PKname;
//
//    }

//        public ArrayList<String> getNameOf() {
//
//
//        ArrayList<String> nameOf = new ArrayList<String>();
//
//        final PackageManager pm = this.getPackageManager();
//
//        final List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
//
//
//
//        for(ApplicationInfo packageInfo : packages){
//
//                String appName = pm.getApplicationLabel(packageInfo).toString();
//
//
//                nameOf.add(appName);
//        }
//
//
//        return nameOf;
//
//    }


}

