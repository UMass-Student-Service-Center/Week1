package com.example.u.ussc.database;

/**
 * Created by Ethan on 3/15/18.
 */

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class AdvisingContract {
    private AdvisingContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.u.ussc";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String ADVISING_PATH = "ussc";

    public static final class AdvisingEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, ADVISING_PATH);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ADVISING_PATH;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ADVISING_PATH;


        public final static String TABLE_NAME = "advising";

        public final static String _ID = BaseColumns._ID;
        public final static String COL_SEMESTER = "semester";
        public final static String COL_YEAR = "YEAR";
        public final static String COL_NUMBER = "number";
        public final static String COL_NAME = "name";
        public final static String COL_CREDITS = "credits";
        public final static String COL_DESCRIPTION = "description";
        public final static String COL_PREREQ = "prerequisite";

    }

}
