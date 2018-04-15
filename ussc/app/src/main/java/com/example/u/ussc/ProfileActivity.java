package com.example.u.ussc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference_1;
    private DatabaseReference databaseReference_2;
    private String mUserId;
    private ListView listView_1;
    private ListView listView_2;
    private List<item_names> list_item_1;
    private List<item_names> list_item_2;
    private item_list_Adapter adapter;
    private String user_names;
    private String Userimages;
    private String User_year;
    private Button market;
    private Button lostanffound;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //setContentView(R.layout.profile_2);
        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.menu_profile);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_lostandfound:
                        //go to lost and found
                        goto_lost_and_found();
                        return true;
                    case R.id.action_market:
                        //go to marketplace
                        goto_marketplace_();
                        return true;
                    case R.id.action_advising:
                        //go to advising
                        goto_advising();
                        return true;
                    case R.id.action_chat:
                        //go to chat
                        goto_conversations();
                        return true;
                    case R.id.action_profile:
                        //go to profile
                        goto_profile();
                        return true;
                }
                return true;
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId = mFirebaseUser.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference(RegistrationActivity.fb_database);
        //databaseReference = FirebaseDatabase.getInstance().getReference(RegistrationActivity.fb_database);
        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            mUserId = mFirebaseUser.getUid();

            final de.hdodenhof.circleimageview.CircleImageView imageView = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_image);
            final TextView TextView1 = (TextView) findViewById(R.id.user_n);
            final TextView TextView2 = (TextView) findViewById(R.id.since);

            Query query = databaseReference.orderByChild("muserId").equalTo(mUserId);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ProfileItem ln = snapshot.getValue(ProfileItem.class);
                        user_names = ln.getName();
                        Userimages = ln.getImages();
                        User_year = ln.getSignUpDate();

                        Glide.with(ProfileActivity.this).load(Userimages).into(imageView);
                        TextView1.setText(user_names);
                        TextView2.setText(User_year);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //progressDialog.dismiss();
                }
            });
            list_item_1 = new ArrayList<>();
            list_item_2 = new ArrayList<>();
            listView_1 = (ListView) findViewById(R.id.list1);
            market = (Button) findViewById(R.id.market_1);
            lostanffound = (Button) findViewById(R.id.lost_2);

            databaseReference_1 = FirebaseDatabase.getInstance().getReference(item_LostandFActivity.fb_database);
            databaseReference_2 = FirebaseDatabase.getInstance().getReference(add_item_marketplaceActivity.fb_database);

            lostanffound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference_1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //progressDialog.dismiss();
                            list_item_1.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                item_names ln = snapshot.getValue(item_names.class);
                                if (ln.getUserid().equals(mUserId))
                                    list_item_1.add(ln);
                            }

                            adapter = new item_list_Adapter(ProfileActivity.this, R.layout.lostandf_item, list_item_1);
                            listView_1.setAdapter(adapter);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // progressDialog.dismiss();
                        }
                    });
                }
            });

            market.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference_2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //progressDialog.dismiss();
                            list_item_1.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                item_names ln = snapshot.getValue(item_names.class);
                                if (ln.getUserid().equals(mUserId))
                                    list_item_1.add(ln);
                            }

                            adapter = new item_list_Adapter(ProfileActivity.this, R.layout.lostandf_item, list_item_1);
                            listView_1.setAdapter(adapter);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // progressDialog.dismiss();
                        }
                    });
                }
            });

            /*
            databaseReference_2 = FirebaseDatabase.getInstance().getReference(add_item_marketplaceActivity.fb_database);//add_item_marketplaceActivity.fb_database

            databaseReference_2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //progressDialog.dismiss();
                    list_item_2.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        item_names ln = snapshot.getValue(item_names.class);
                        if (ln.getUserid().equals(mUserId))
                            list_item_2.add(ln);
                    }

                    adapter = new item_list_Adapter(ProfileActivity.this, R.layout.lostandf_item, list_item_2);
                    listView_2.setAdapter(adapter);
                    Utility.setListViewHeightBasedOnChildren(listView_2);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // progressDialog.dismiss();
                }
            });

            */

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                // back home
                goto_add();
                return true;
            case R.id.logout:
                //logout
                mFirebaseAuth.signOut();
                loadLogInView();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goto_add() {
        Intent intent = new Intent(ProfileActivity.this, two_choicesActivity.class);
        startActivity(intent);
    }

    private void loadLogInView() {
        Intent intent = new Intent(ProfileActivity.this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void goto_lost_and_found() {
        Intent intent = new Intent(ProfileActivity.this, LostandFActivity.class);
        startActivity(intent);
    }

    private void goto_marketplace_() {
        Intent intent = new Intent(ProfileActivity.this, MarketplaceActivity.class);
        startActivity(intent);
    }

    private void goto_advising() {
        Intent intent = new Intent(ProfileActivity.this, AdvisingActivity.class);
        startActivity(intent);
    }

    private void goto_conversations() {
        Intent intent = new Intent(ProfileActivity.this, ConversationsActivity.class);
        startActivity(intent);
    }

    private void goto_profile() {
        Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

}
