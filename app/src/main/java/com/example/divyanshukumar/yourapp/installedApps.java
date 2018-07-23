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
     * IT WILL MAKE AN ARRAYLIST OF APPNAME
     *
     * @return APP NAME ARRAYLIST
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
    public String getPackNameByAppName(String name) {
        PackageManager pm = context1.getPackageManager();
        List<ApplicationInfo> l = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        String packName = "";
        for (ApplicationInfo ai : l) {
            String n = (String)pm.getApplicationLabel(ai);
            if (n.contains(name) || name.contains(n)){
                packName = ai.packageName;
            }
        }

        return packName;
    }



//    public ArrayList<String> getAllAppInfo() {
//
//        ArrayList<String> PKname = new ArrayList<String>();
//
//
////        final ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, nameOf);
//
//
////        final List<PackageInfo> packageInfoList = context.getPackageManager().getInstalledPackages(0);
//
//        final PackageManager pm = context1.getPackageManager();
////get a list of installed apps.
//        final List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
//
//
//
//        /**
//         * this loop fill the list of Apps that will be will shown on screen
//         */
//
//        for (int i = 0; i< packages.size(); i++) {
//
//            ApplicationInfo packageInfo = packages.get(i);
//
////                if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)==0) {
//
////                    app_List.add(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
//
////            app_List.add(packageInfo.loadLabel(context.getPackageManager()).toString());
//
//
//
//                    PKname.add(packageInfo.packageName);
//
//
//
////                }
//                }
//
//                return PKname;
//
//         }


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





