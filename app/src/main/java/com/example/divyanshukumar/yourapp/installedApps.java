package com.example.divyanshukumar.yourapp;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import java.util.List;


public class installedApps {

    private Context context1;



    public installedApps(Context context){


        context1 = context;
    }



    /**
     * GO BELOW THE COMMENTS
     *
     */
    public ArrayList<String> getNameOf() {


        ArrayList<String> nameOf = new ArrayList<String>();

        final PackageManager pm = context1.getPackageManager();

        final List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);



        for(ApplicationInfo packageInfo : packages){

                String appName = pm.getApplicationLabel(packageInfo).toString();


                nameOf.add(appName);
        }


        return nameOf;

    }


    /**
     * IT WILL GET THE APP PACKAGE NAME THROUGH APP NAME FROM THE ABOVE FUNCTION
     *
     * @param name
     * @return PACKAGE NAME
     */
//    public String getPackNameByAppName(String name) {
//        PackageManager pm = context1.getPackageManager();
//        List<ApplicationInfo> l = pm.getInstalledApplications(PackageManager.GET_META_DATA);
//        String packName = "";
//        for (ApplicationInfo ai : l) {
//            String n = (String)pm.getApplicationLabel(ai);
//            if (n.contains(name) || name.contains(n)){
//                packName = ai.packageName;
//            }
//        }
//
//        return packName;
//    }


    /**
     *
     * @return ArrayList of package name
     */
    public ArrayList<String> getAllAppInfo() {

        ArrayList<String> PKname = new ArrayList<String>();

        final PackageManager pm = context1.getPackageManager();
//get a list of installed apps.
        final List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);



        /**
         * this loop fill the list of Apps that will be will shown on screen
         */

        for (int i = 0; i< packages.size(); i++) {

            ApplicationInfo packageInfo = packages.get(i);


                    PKname.add(packageInfo.packageName);

            /**
             * AppName
             */
//            String appName = pm.getApplicationLabel(packageInfo).toString();



        }

        return PKname;

         }


//         public String getAppName(String appPackageName){
//
//        String Name = "";
//
//        ApplicationInfo applicationInfo;
//
//        PackageManager packageManager = context1.getPackageManager();
//
//             try {
//
//                 applicationInfo = packageManager.getApplicationInfo(appPackageName, 0);
//
//                 if(applicationInfo!=null){
//
//                     Name = (String)packageManager.getApplicationLabel(applicationInfo);
//                 }
//
//             }catch (PackageManager.NameNotFoundException e) {
//
//                 e.printStackTrace();
//             }
//             return Name;
//         }

}





