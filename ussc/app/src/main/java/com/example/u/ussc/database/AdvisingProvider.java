package com.example.u.ussc.database;

/**
 * Created by Ethan on 3/15/18.
 */
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import com.example.u.ussc.database.AdvisingContract.AdvisingEntry;

public class AdvisingProvider extends ContentProvider {

    public static final String LOG_TAG = AdvisingProvider.class.getSimpleName();

    private static final int ADVISING = 100;

    private static final int INVENTORY_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AdvisingContract.CONTENT_AUTHORITY, AdvisingContract.ADVISING_PATH, ADVISING);

        sUriMatcher.addURI(AdvisingContract.CONTENT_AUTHORITY, AdvisingContract.ADVISING_PATH + "/#", INVENTORY_ID);
    }

    public AdvisingProvider() {
    }

    private AdvisingDbHelper mDbHelper;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ADVISING:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(AdvisingEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case INVENTORY_ID:
                // Delete a single row given by the ID in the URI
                selection = AdvisingEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(AdvisingEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ADVISING:
                return AdvisingEntry.CONTENT_LIST_TYPE;
            case INVENTORY_ID:
                return AdvisingEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ADVISING:
                return insertItem(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        Log.i(LOG_TAG, "We prepared the a DB helper");
        mDbHelper = new AdvisingDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case ADVISING:
                cursor = db.query(AdvisingEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case INVENTORY_ID:

                selection = AdvisingEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = db.query(AdvisingEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    public Uri insertItem(Uri uri, ContentValues values) {

        // Check that the name is not null
        String name = values.getAsString(AdvisingEntry.COL_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Product requires a name");
        }

        //TODO:REMOVE THE QUANTITY AND PRICE COL

        // Check that the gender is valid
//        Integer quantity = values.getAsInteger(AdvisingEntry.COL_QUANTITY);
//
//        // If the weight is provided, check that it's greater than or equal to 0 kg
//        Float price = values.getAsFloat(AdvisingEntry.COL_PRICE);
//        if (price != null && price < 0) {
//            throw new IllegalArgumentException("product requires valid price");
//        }


        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();


        // Insert the new product with the given values
        long id = database.insert(AdvisingEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the inventory content URI
        getContext().getContentResolver().notifyChange(uri, null);
        Log.i(LOG_TAG, "We inserted a record");


        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsUpdated;

        if (values == null) {
            throw new IllegalArgumentException("Cannot update empty values");
        }

        switch (match) {
            case ADVISING:
                rowsUpdated = database.update(AdvisingEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case INVENTORY_ID:
                rowsUpdated = database.update(AdvisingEntry.TABLE_NAME,
                        values,
                        AdvisingEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return rowsUpdated;
    }
}
