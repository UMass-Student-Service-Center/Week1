package com.example.u.ussc;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.u.ussc.database.AdvisingContract.AdvisingEntry;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ethan on 3/15/18.
 */

public class AdvisingEditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_ADVISING_LOADER = 0;

    public final String[] ADVISING_COLS = {
            AdvisingEntry._ID,
            AdvisingEntry.COL_NAME,
            AdvisingEntry.COL_NUMBER,
            AdvisingEntry.COL_PREREQ,
            AdvisingEntry.COL_DESCRIPTION,
            AdvisingEntry.COL_CREDITS,
            //AdvisingEntry.COL_SEMESTER,
            AdvisingEntry.COL_YEAR

    };

    private EditText mClassName;
    private EditText mClassDescription;
    private EditText mClassNumber; //col_quantity
    private EditText mClassPrereq;

    private Uri mCurrentProductUri;

    private boolean mProductHasChanged = false;

//    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            mProductHasChanged = true;
//            return false;
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //populate drop down
        String[] semesterArr = new String[] { "Fall", "Winter", "Spring", "Summer"};
        ArrayAdapter<String> adapterSemester = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, semesterArr);
        Spinner spinSemester = (Spinner)findViewById(R.id.semester);
        spinSemester.setAdapter(adapterSemester);

        ArrayList<String> yearsArr = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i <= 2030; i++) {
            yearsArr.add(Integer.toString(i));
        }

        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearsArr);
        Spinner spinYear = (Spinner)findViewById(R.id.year);
        spinYear.setAdapter(adapterYear);

        String[] creditsArr = new String[] {"1","3","4"};
        ArrayAdapter<String> adapterCredits = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, creditsArr);
        Spinner spinCredits =  (Spinner) findViewById(R.id.credit);
        spinCredits.setAdapter(adapterCredits);

        mClassName = findViewById(R.id.edit_course_name);
        mClassDescription = findViewById(R.id.edit_course_description);
        mClassNumber = findViewById(R.id.edit_course_number);
        mClassPrereq = findViewById(R.id.edit_course_prereq);

//        mClassName.setOnTouchListener(mTouchListener);
//        mClassDescription.setOnTouchListener(mTouchListener);
//        mClassNumber.setOnTouchListener(mTouchListener);
//        mClassPrereq.setOnTouchListener(mTouchListener);

        Button actUpdate = (Button) findViewById(R.id.save_class_button);


        actUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProduct();
            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this,
                mCurrentProductUri,
                ADVISING_COLS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {

            int i_ID = 0;
            int i_COL_NAME = 1;
            int i_COL_NUM = 2;
            int i_COL_DESCRIPTION = 3;
            int i_COL_PREREQ = 4;

            // Extract values from current cursor
            String name = cursor.getString(i_COL_NAME);
            String number = cursor.getString(i_COL_NUM);
            String prereq = cursor.getString(i_COL_PREREQ);
            String description = cursor.getString(i_COL_DESCRIPTION);

            //We updates fields to values on DB
            mClassName.setText(name);
            mClassNumber.setText(number);
            mClassPrereq.setText(prereq);
            mClassDescription.setText(description);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mClassName.setText("");
        mClassNumber.setText("");
        mClassPrereq.setText("");
        mClassDescription.setText("");

    }

    private void saveProduct() {
        //Read Values from text field
        String nameString = mClassName.getText().toString().trim();
        String descriptionString = mClassDescription.getText().toString().trim();
        String numberString = mClassNumber.getText().toString().toString();
        String prereqString = mClassPrereq.getText().toString().trim();

        //Check if is new or if an update
        if (TextUtils.isEmpty(nameString) || TextUtils.isEmpty(descriptionString)
                || TextUtils.isEmpty(numberString)
                || TextUtils.isEmpty(prereqString)) {

            Toast.makeText(this, "Enter into all fields", Toast.LENGTH_SHORT).show();
            // No change has been made so we can return
            return;
        }

        ContentValues values = new ContentValues();

        values.put(AdvisingEntry.COL_NAME, nameString);
        values.put(AdvisingEntry.COL_DESCRIPTION, descriptionString);
        values.put(AdvisingEntry.COL_NUMBER, numberString);
        values.put(AdvisingEntry.COL_PREREQ, prereqString);


        if (mCurrentProductUri == null) {

            Uri insertedRow = getContentResolver().insert(AdvisingEntry.CONTENT_URI, values);

            if (insertedRow == null) {
                //Toast.makeText(this, R.string.err_inserting_product, Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, R.string.ok_updated, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, AdvisingActivity.class);
                startActivity(intent);
            }
        } else {
            // We are Updating
            int rowUpdated = getContentResolver().update(mCurrentProductUri, values, null, null);

            if (rowUpdated == 0) {
                //Toast.makeText(this, R.string.err_inserting_product, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Item added", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, AdvisingActivity.class);
                startActivity(intent);

            }

        }


    }
}
