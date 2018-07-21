package com.example.divyanshukumar.yourapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {
    List<String> mDataset;


    List<String> nlist;
    Context context1;

    ArrayList<String> mNameset;

    ArrayList<String> filterList;





//    public void updateList() {
//
//        mDataset
//
//        installedApps apkInfoExtractor = new installedApps(context1);
//        final String ApplicationPackageName = (String) mDataset.get(position);
//
//        String ApplicationLabelName = apkInfoExtractor.getAppName(ApplicationPackageName);
//
//
//        notifyDataSetChanged();
//    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mTextView;

        private ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context, ArrayList<String> myDataset, ArrayList<String> nameoftheapplication) {

        context1 = context;
        mDataset = myDataset;
        mNameset = nameoftheapplication;

        filterList = mNameset;

    }

//    installedApps obj = new installedApps(context1);
//    ArrayList<String> anothername = obj.getNameOf();




    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_item_view, parent, false);

        ViewHolder vh = new ViewHolder(v);

//            vh.mTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                }
//            });


        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//        installedApps apkInfoExtractor = new installedApps(context1);
//
        final String ApplicationPackageName = (String) mDataset.get(position);
//
//        String ApplicationLabelName = apkInfoExtractor.getAppName(ApplicationPackageName);


        String ApplicationLabelName = (String) mNameset.get(position);


        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(ApplicationLabelName);

//        filterApps(ApplicationLabelName);


        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
//        return mDataset.size();

        return filterList.size();

    }


    @Override
    public Filter getFilter() {

         ItemFilter mFilter = new ItemFilter();

        return mFilter;
    }

    class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

//            installedApps apkInfoExtractor = new installedApps(context1);


            FilterResults results = new FilterResults();

            final List<String> list = mNameset;
//
            int count = list.size();

//            final List<String> nlist ;

             List<String> tempList = new ArrayList<String>();

            for(int i = 0; i<count; i++){

                String ApplicationPackagesName = mNameset.get(i);

                if(ApplicationPackagesName.contains(filterString)){

                    tempList.add(ApplicationPackagesName);
                }
            }

            nlist = tempList;


//
////            String filterableString ;
//
//            for (int i = 0; i < count; i++) {
//
//                final String ApplicationPackageName = (String) mDataset.get(i);
//
//                String ApplicationLabelName = apkInfoExtractor.getAppName(ApplicationPackageName);
//
////                filterableString = list.get(i);
//                if (ApplicationLabelName.toLowerCase().contains(filterString)) {
//                    nlist.add(ApplicationLabelName);
//                }
//            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results){

//            mNameset.remove("YouTube");
//
//            mDataset.remove("com.google.android.youtube");


            filterList = (ArrayList<String>) results.values;

            notifyDataSetChanged();
        }
    }


//    public void filterApps(){

//    public void filterApps(){

//        ArrayList<String> filterd = new ArrayList<>();
//
//        for(int i = 0; i<mNameset.size(); i++) {
//
//            String ApplicationPackageName = (String) mNameset.get(i);
//
//            if (ApplicationPackageName.contains(filter)){
//
////            if (ApplicationPackageName.contains(filter)){
//
////                filterd = hashMapFilter.get(ApplicationPackageName);
//
//                filterd.add(ApplicationPackageName);
//
////                mNameset.remove(ApplicationPackageName);
//        }
//        }

//        filterList = filterd;

//        mDataset.clear();
//
//        mDataset = filterd;
//
//        mDataset = filterList;

//        mDataset.remove("com.google.android.youtube");
//
////        mNameset.remove("com.google.android.youtube");
//
//
//
//        notifyDataSetChanged();
//    }



    }








