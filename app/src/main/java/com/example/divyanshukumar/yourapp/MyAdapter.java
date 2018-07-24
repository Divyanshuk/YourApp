package com.example.divyanshukumar.yourapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {


     Context context1;

     ArrayList<String> mNameset;

    List<String> filterList;

    ItemFilter mFilter ;


    /**
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
     **/
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mTextView;

        private ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context, ArrayList<String> nameoftheapplication) {

        context1 = context;
        mNameset = nameoftheapplication;

        filterList = mNameset;

    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_item_view, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//        installedApps apkInfoExtractor = new installedApps(context1);
////
//        final String ApplicationPackageName = (String) mDataset.get(position);
////
//        String ApplicationLabelName = apkInfoExtractor.getAppName(ApplicationPackageName);

        final installedApps apkInfoExtractor = new installedApps(context1);

        final String ApplicationLabelName = (String) mNameset.get(position);


        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.mTextView.setText(ApplicationLabelName);

        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = context1.getPackageManager().getLaunchIntentForPackage(apkInfoExtractor.getPackNameByAppName(ApplicationLabelName));
                if (intent != null) {

                    context1.startActivity(intent);

                } else {

                    Toast.makeText(context1, apkInfoExtractor.getPackNameByAppName(ApplicationLabelName) + " Error, Please Try Again.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mNameset.size();

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

            for (String ApplicationLabelName : filterList) {

                    if (ApplicationLabelName.toLowerCase().contains(filterString)) {

                        tempList.add(ApplicationLabelName);
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

                mNameset = (ArrayList<String>) results.values;

                notifyDataSetChanged();
            }
        }


//


    }









