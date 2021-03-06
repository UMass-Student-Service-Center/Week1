package com.example.u.ussc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainMenuActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    private DatabaseReference databaseReference;
    private ListView listView;
    private SearchView searchView;
    private Button marketPlace;
    private Button lost_and_found;
    private Button advising;
    private ProgressDialog progressDialog;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setTitle("Main Menu");

        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();
        //mDatabase = FirebaseDatabase.getInstance().getReference();

        if (mFirebaseUser == null || !(user.isEmailVerified())) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {

            //Buttons from the main menu
            lost_and_found = (Button) findViewById(R.id.lostandfound);
            marketPlace = (Button) findViewById(R.id.Marketplace);
            advising = (Button) findViewById(R.id.advising);

            //set up on click listener for all the buttons
            //on click calls each activity
            lost_and_found.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goto_lost_and_found();
                }
            });

            marketPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goto_marketplace_();
                }
            });

            advising.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goto_advising();
                }
            });

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //go to profile
            case R.id.action_profile:
                goto_profile();
                return true;
            //go to chat
            case R.id.action_chat:
                goto_conversations();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void loadLogInView() {
        Intent intent = new Intent(MainMenuActivity.this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void goto_lost_and_found() {
        Intent intent = new Intent(MainMenuActivity.this, LostandFActivity.class);
        startActivity(intent);
    }

    private void goto_marketplace_() {
        Intent intent = new Intent(MainMenuActivity.this, MarketplaceActivity.class);
        startActivity(intent);
    }

    private void goto_advising() {
        Intent intent = new Intent(MainMenuActivity.this, AdvisingActivity.class);
        startActivity(intent);
    }

    private void goto_conversations() {
        Intent intent = new Intent(MainMenuActivity.this, ConversationsActivity.class);
        startActivity(intent);
    }

    private void goto_profile() {
        Intent intent = new Intent(MainMenuActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

}