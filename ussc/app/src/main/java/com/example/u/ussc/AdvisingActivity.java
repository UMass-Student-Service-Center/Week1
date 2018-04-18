package com.example.u.ussc;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.u.ussc.database.AdvisingContract.AdvisingEntry;

public class AdvisingActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ADVISING_LOADER = 0;

    AdvisingCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advising);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdvisingActivity.this, AdvisingEditorActivity.class);
                startActivity(intent);
            }
        });

        ListView AdvisingListView = (ListView) findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);

        AdvisingListView.setEmptyView(emptyView);

        mCursorAdapter = new AdvisingCursorAdapter(this, null);
        AdvisingListView.setAdapter(mCursorAdapter);

        getLoaderManager().initLoader(ADVISING_LOADER, null, this);
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                AdvisingEntry._ID,
                AdvisingEntry.COL_NAME,
                AdvisingEntry.COL_DESCRIPTION,
                AdvisingEntry.COL_NUMBER,
                AdvisingEntry.COL_CREDITS,
                AdvisingEntry.COL_PREREQ,
                //AdvisingEntry.COL_SEMESTER,
                AdvisingEntry.COL_YEAR

        };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                AdvisingEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link PetCursorAdapter} with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_advising, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            // Respond to a click on the "Delete all entries" menu option
            case R.id.reset_list:
                deleteAllItems();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllItems() {
        int rowsDeleted = getContentResolver().delete(AdvisingEntry.CONTENT_URI, null, null);
        //Log.v("CatalogActivity", rowsDeleted + " rows deleted from products database");
    }

}
