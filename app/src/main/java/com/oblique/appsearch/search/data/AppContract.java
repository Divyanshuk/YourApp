package com.oblique.appsearch.search.data;

import android.provider.BaseColumns;

public final class AppContract {

    private AppContract() {}


    public static final class AppEntry implements BaseColumns{


        public final static String TABLE_NAME = "apps";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_APP_NAME = "name";
        public final static String COLUMN_APP_PACKAGE = "package";
//        public final static String COLUMN_APP_ICON = "icon"
    }

}
