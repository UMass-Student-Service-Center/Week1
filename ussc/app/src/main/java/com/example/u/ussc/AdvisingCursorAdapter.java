package com.example.u.ussc;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.u.ussc.database.AdvisingContract.AdvisingEntry;

/**
 * Created by Ethan on 3/15/18.
 */

public class AdvisingCursorAdapter extends CursorAdapter {

    public AdvisingCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context,  final Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView courseNumberTextView  = (TextView) view.findViewById(R.id.course_number);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.description);
        TextView creditTextView = (TextView) view.findViewById(R.id.credits);
        TextView semesterTextView = (TextView) view.findViewById(R.id.semester_plus_year);
        TextView prereqTextView = (TextView) view.findViewById(R.id.course_prereq);


        int nameColumnIndex = cursor.getColumnIndex(AdvisingEntry.COL_NAME);
        int courseNumberColumnIndex = cursor.getColumnIndex(AdvisingEntry.COL_NUMBER);
        int descriptionColumnIndex = cursor.getColumnIndex(AdvisingEntry.COL_DESCRIPTION);
        int creditsColumnIndex = cursor.getColumnIndex(AdvisingEntry.COL_CREDITS);
        //int semesterColumnIndex = cursor.getColumnIndex(AdvisingEntry.COL_SEMESTER);
        int yearColumnIndex = cursor.getColumnIndex(AdvisingEntry.COL_YEAR);
        int prereqColumnIndex = cursor.getColumnIndex(AdvisingEntry.COL_PREREQ);

        String className = cursor.getString(nameColumnIndex);
        String classNumber = cursor.getString(courseNumberColumnIndex);
        String classDescription = cursor.getString(descriptionColumnIndex);
        String classCredits = cursor.getString(creditsColumnIndex);
        //String classSemester = cursor.getString(semesterColumnIndex);
        String classPrereq = cursor.getString(prereqColumnIndex);
        String classYear = cursor.getString(yearColumnIndex);


        nameTextView.setText(className);
        courseNumberTextView.setText(classNumber);
        descriptionTextView.setText(classDescription);
        creditTextView.setText(classCredits);
        prereqTextView.setText(classPrereq);
        //semesterTextView.setText(classSemester);
//        semesterTextView.append(" ");
//        semesterTextView.append(classYear);
//        int id = cursor.getInt(cursor.getColumnIndex(InventoryEntry._ID));
//        final Uri currentProductUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);
    }

}
