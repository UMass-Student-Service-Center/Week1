package com.example.u.ussc.database;

import android.content.Context;

/**
 * Created by Ethan on 3/15/18.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.u.ussc.database.AdvisingContract.AdvisingEntry;

public class AdvisingDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = AdvisingDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "advising.db";

    private static final int DATABASE_VERSION = 4;

    public AdvisingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ADVISING = "CREATE TABLE " + AdvisingEntry.TABLE_NAME + " ("
                + AdvisingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + AdvisingEntry.COL_SEMESTER + " TEXT NOT NULL, "
                + AdvisingEntry.COL_YEAR + " TEXT, "
                + AdvisingEntry.COL_NUMBER + " TEXT, "
                + AdvisingEntry.COL_NAME + " TEXT, "
                + AdvisingEntry.COL_CREDITS + " TEXT, "
                + AdvisingEntry.COL_DESCRIPTION + " TEXT, "
                + AdvisingEntry.COL_PREREQ + "  TEXT"
                + ");";

        db.execSQL(SQL_CREATE_ADVISING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AdvisingEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
