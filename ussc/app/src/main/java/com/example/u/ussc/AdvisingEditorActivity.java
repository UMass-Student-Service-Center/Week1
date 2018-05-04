package com.example.u.ussc;

import android.app.Activity;
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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.u.ussc.database.AdvisingContract.AdvisingEntry;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ethan on 3/15/18.
 */

public class AdvisingEditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "editorAcivity";

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

    private Spinner mClassName;
    private Spinner mClassDescription;
    private Spinner mClassNumber; //col_quantity
    private Spinner mClassCredits;
    private EditText mClassPrereq;

    private Uri mCurrentProductUri;

    private boolean mProductHasChanged = false;

    private String myJson;

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

        //res/raw/json file
        myJson= inputStreamToString(getResources().openRawResource(R.raw.cs_catalog));
        classModel myModel = new Gson().fromJson(myJson, classModel.class);

//        //populate drop down
//        ArrayList<String> yearsArr = new ArrayList<String>();
//        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
//        for (int i = thisYear; i <= 2030; i++) {
//            yearsArr.add(Integer.toString(i));
//        }
//
//        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearsArr);
//        Spinner spinYear = (Spinner)findViewById(R.id.year);
//        spinYear.setAdapter(adapterYear);


        //adding the course values to their respective spinner
        ArrayList<String> idArr = new ArrayList<String>();
        ArrayList<String> nameArr = new ArrayList<String>();
        ArrayList<String> descriptionArr = new ArrayList<String>();
        ArrayList<String> creditArr = new ArrayList<String>();
        for(int i=0;i < myModel.list.size(); i++) {
            idArr.add(myModel.list.get(i).id);
            nameArr.add(myModel.list.get(i).name);
            descriptionArr.add(myModel.list.get(i).description);
            creditArr.add(myModel.list.get(i).credits);
        }

        ArrayAdapter<String> adapterId = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,idArr);
        mClassNumber = (Spinner) findViewById(R.id.edit_course_number);
        mClassNumber.setAdapter(adapterId);

        ArrayAdapter<String> adapterName = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,nameArr);
        mClassName = (Spinner) findViewById(R.id.edit_course_name);
        mClassName.setAdapter(adapterName);


        ArrayAdapter<String> adapterDescription = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,descriptionArr);
        mClassDescription = (Spinner) findViewById(R.id.edit_course_description);
        mClassDescription.setEnabled(false);
        mClassDescription.setAdapter(adapterDescription);

        ArrayAdapter<String> adapterCredits = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,creditArr);
        mClassCredits = (Spinner) findViewById(R.id.credit);
        mClassCredits.setEnabled(false);
        mClassCredits.setAdapter(adapterCredits);

//        mClassName = findViewById(R.id.edit_course_name);
//        mClassDescription = findViewById(R.id.edit_course_description);
//        mClassNumber = findViewById(R.id.edit_course_number);
        mClassPrereq = findViewById(R.id.edit_course_prereq);

        //when course name changes other fields change with it
        mClassName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
                mClassDescription.setSelection(position);
                mClassNumber.setSelection(position);
                mClassCredits.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        mClassNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
                mClassDescription.setSelection(position);
                mClassName.setSelection(position);
                mClassCredits.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

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
//            mClassName.setText(name);
//            mClassNumber.setText(number);
//            mClassPrereq.setText(prereq);
//            mClassDescription.setText(description);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
//        mClassName.setSel;
//        mClassNumber.setText("");
//        mClassPrereq.setText("");
//        mClassDescription.setText("");

    }

    private void saveProduct() {
        //Read Values from text field
        String nameString = mClassName.getSelectedItem().toString().trim();
        String descriptionString = mClassDescription.getSelectedItem().toString().trim();
        String numberString = mClassNumber.getSelectedItem().toString().toString();
        String prereqString = mClassPrereq.getText().toString().trim();
        String creditString = mClassCredits.getSelectedItem().toString();
        //Check if is new or if an update
        if (TextUtils.isEmpty(nameString) || TextUtils.isEmpty(descriptionString)
                || TextUtils.isEmpty(numberString)
                || TextUtils.isEmpty(prereqString)) {

            Toast.makeText(this, "Enter into all fields", Toast.LENGTH_SHORT).show();
            // No change has been made so we can return
            return;
        }

        ContentValues values = new ContentValues();


        //TODO: Add semester season and credit to the data base
        values.put(AdvisingEntry.COL_NAME, nameString);
        values.put(AdvisingEntry.COL_DESCRIPTION, descriptionString);
        values.put(AdvisingEntry.COL_NUMBER, numberString);
        values.put(AdvisingEntry.COL_PREREQ, prereqString);
        values.put(AdvisingEntry.COL_CREDITS, creditString);


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

    //for reading in json file
    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }
}
