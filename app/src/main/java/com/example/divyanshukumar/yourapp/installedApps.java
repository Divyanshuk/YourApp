package com.example.divyanshukumar.yourapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class installedApps {

    ArrayList<String> nameOf = new ArrayList<String>();

//    ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameOf);

    /**
     *
     * @param app_List
     * @param list
     * @param context
     */
    public void appss(final ArrayList<String> app_List, final ListView list, final Context context) {

//        ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, nameOf);


//        final List<PackageInfo> packageInfoList = context.getPackageManager().getInstalledPackages(0);

        final PackageManager pm = context.getPackageManager();
//get a list of installed apps.
        final List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);


        /**
         * this loop fill the list of Apps that will be will shown on screen
         */

        for (int i = 0; i< packages.size(); i++) {

            ApplicationInfo packageInfo = packages.get(i);

//                if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)==0) {

//                    app_List.add(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());

            app_List.add(packageInfo.loadLabel(context.getPackageManager()).toString());



                    nameOf.add(packageInfo.packageName);



//                }
                }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                String apps = (String) list.getAdapter().getItem(position);
//
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/html");
//                intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
//                intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

//                list.getContext().startActivity(Intent.createChooser(intent, "Send Email"));

//                PackageInfo packageInfo = packageInfoList.get(position+2);

//                String hi = packageInfoList.get(position).packageName;



//                int place = list.getAdapter().getViewTypeCount();



                ApplicationInfo app = packages.get(position);



//                    Intent intent = context.getPackageManager().getLaunchIntentForPackage(nameOf.get(position));

                Intent intent = context.getPackageManager().getLaunchIntentForPackage(app.packageName);



                list.getContext().startActivity(intent);



            }
        });


         }



         }





