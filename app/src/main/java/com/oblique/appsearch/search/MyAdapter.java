package com.oblique.appsearch.search;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oblique.appsearch.search.data.AppDbHelper;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {


     Context context1;

     ArrayList<String> mDataset;

     ArrayList<String> mNameset;

    List<String> filterList;

    ItemFilter mFilter ;

    AppDbHelper DbHelper;

    ApplicationInfo applicationInfo;

    PackageManager packageManager;

    String ApplicationLabelName;

    Drawable ApplicationIcon;

    String Name = "";

    Drawable icon = null;




//    Cursor cursor;

    /**
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
     **/
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

//        private TextView mTextView;

        private CardView mCardView;
        private TextView textView_App_Name;
        private ImageView icom_App;

//        private ViewHolder(TextView v) {
//            super(v);
//            mTextView = v;
//        }

        private ViewHolder(CardView v) {
            super(v);
            mCardView = v;

            textView_App_Name =  v.findViewById(R.id.simple_text1);
            icom_App = v.findViewById(R.id.App_imageView);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context, ArrayList<String> packagenameofApplication, ArrayList<String> applicationName) {

        context1 = context;

        mDataset = packagenameofApplication;

        mNameset = applicationName;

        filterList = mDataset;

        DbHelper = new AppDbHelper(context1);

        packageManager = context1.getPackageManager();
        }




    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.simple_item_view, parent, false);

        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }


    /**
     * Testing ads
     */


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//        installedApps apkInfoExtractor = new installedApps(context1);
//
        final String ApplicationPackageName = (String) mDataset.get(position);
//
          ApplicationLabelName = getAppName(ApplicationPackageName);

//        String ApplicationLabelName = mNameset.get(position);

//        String ApplicationLabelName = gettheName(ApplicationPackageName);


          ApplicationIcon = getAppIcon(ApplicationPackageName);


        holder.textView_App_Name.setText(ApplicationLabelName);

        holder.icom_App.setBackground(ApplicationIcon);

//        holder.mTextView.setText(mNameset.get(position));

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Intent intent = context1.getPackageManager().getLaunchIntentForPackage(apkInfoExtractor.getPackNameByAppName(ApplicationLabelName));
//                if (intent != null) {
//
//                    context1.startActivity(intent);
//
//                } else {
//
//                    Toast.makeText(context1, apkInfoExtractor.getPackNameByAppName(ApplicationLabelName) + " Error, Please Try Again.", Toast.LENGTH_LONG).show();
//                }

                Intent intent = context1.getPackageManager().getLaunchIntentForPackage(ApplicationPackageName);
                if (intent != null) {

                    context1.startActivity(intent);

                } else {

                    Toast.makeText(context1, ApplicationPackageName + " Error, Please Try Again.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {


        return mDataset.size();

    }


    /**
     * DOWN BELOW THERE IS JUST CUSTOM FILTER
     *
     */

    @Override
    public Filter getFilter() {

        if(mFilter == null) {

            mFilter = new ItemFilter();
        }

        return mFilter;
    }

    class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {


            FilterResults results = new FilterResults();

            if(constraint != null && constraint.length()>0) {

                String filterString = constraint.toString().toLowerCase();


                List<String> tempList = new ArrayList<String>();

//                installedApps apkInfoExtractor = new installedApps(context1);

                String packageOfApplication;


//                for (String packageOfApplication : filterList) {

                    for(int i=0; i < filterList.size(); i++){

                        packageOfApplication = filterList.get(i);

//                    String ApplicationLabelName = gettheName(packageOfApplication);

                        String ApplicationLabelName = mNameset.get(i);


                if (ApplicationLabelName.toLowerCase().contains(filterString)) {

//                    installedApps apkInfoExtractor = new installedApps(context1);
//
//
//                    String ApplicationLabelName = apkInfoExtractor.getAppName(packageOfApplication);


                    tempList.add(packageOfApplication);

                        }

                }


                results.values = tempList;
                results.count = tempList.size();


            }

            else {

                results.values = filterList;

                results.count = filterList.size();
            }

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results){

                mDataset = (ArrayList<String>) results.values;

                notifyDataSetChanged();
            }
        }

    /**
     *
     * @param appPackageName this is package name
     * @return App name
     */
    public String getAppName(String appPackageName){



//        ApplicationInfo applicationInfo;

//        PackageManager packageManager = context1.getPackageManager();

        try {

            applicationInfo = packageManager.getApplicationInfo(appPackageName, 0);

            if(applicationInfo!=null){

                Name = (String)packageManager.getApplicationLabel(applicationInfo);
            }

        }catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return Name;
    }

//    public String gettheName(String packaname){
//
//        String currentName;
//
//        Cursor cursor1 = getCursor2();
//
//
////        SQLiteDatabase db1 = DbHelper.getReadableDatabase();
//
//
//
//
////            String newQuery = "SELECT "+ AppEntry.COLUMN_APP_NAME+ " FROM " + AppEntry.TABLE_NAME +
////                    " WHERE " + AppEntry.COLUMN_APP_PACKAGE + " = '" + packaname + "';";
////
////            cursor1 = db1.rawQuery(newQuery, null);
//
//            int nameColumnIndex = cursor1.getColumnIndex(AppEntry.COLUMN_APP_NAME);
//
//             currentName = cursor1.getString(nameColumnIndex);
//
//
//
//
//
//        return currentName;
//    }


public Drawable getAppIcon(String appPackName){

        Drawable icon = null;

    try {

        applicationInfo = packageManager.getApplicationInfo(appPackName, 0);

        if(applicationInfo!=null){

            icon = packageManager.getApplicationIcon(applicationInfo);
        }

    }catch (PackageManager.NameNotFoundException e) {

        e.printStackTrace();
    }

    return icon;
}




    }









