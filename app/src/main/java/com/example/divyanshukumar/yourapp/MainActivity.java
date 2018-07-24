package com.example.divyanshukumar.yourapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;


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
         * There are two param, 2nd one takes the name of Application
         */
        final MyAdapter adapter = new MyAdapter(this, new installedApps(this).getAllAppInfo());


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

}

