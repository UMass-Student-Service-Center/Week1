package com.example.u.ussc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ethan on 3/15/18.
 */

public class AdvisingEditorActivity extends AppCompatActivity {


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
    }

}
