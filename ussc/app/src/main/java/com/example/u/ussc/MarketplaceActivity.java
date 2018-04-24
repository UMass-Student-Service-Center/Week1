package com.example.u.ussc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

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


public class MarketplaceActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    private DatabaseReference databaseReference;
    private ListView listView;
    private List<item_names> list_item_s;
    private item_list_Adapter adapter;
    private SearchView searchView;
    private Button all;
    private Button sendMessage;
    private ProgressDialog progressDialog;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);
        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();

        /*
        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.menu_market);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_lostandfound:
                        //go to lost and found
                        goto_lost_and_found();
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
        */
        //mDatabase = FirebaseDatabase.getInstance().getReference();

        if (mFirebaseUser == null || !(user.isEmailVerified())) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            mUserId = mFirebaseUser.getUid();

            list_item_s = new ArrayList<>();
            listView = (ListView) findViewById(R.id.list_m);
            searchView = (SearchView) findViewById(R.id.searchbook_m);
            all = (Button) findViewById(R.id.all_m);
            sendMessage = (Button) findViewById(R.id.text_mess);

            list_item_s = new ArrayList<>();
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait loading.....");
            progressDialog.show();

            databaseReference = FirebaseDatabase.getInstance().getReference(add_item_marketplaceActivity.fb_database);

            progressDialog.dismiss();

            all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            progressDialog.dismiss();
                            list_item_s.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                item_names ln = snapshot.getValue(item_names.class);
                                //if (ln.getMUserId().equals(mUserId))
                                list_item_s.add(ln);
                            }

                            adapter = new item_list_Adapter(MarketplaceActivity.this, R.layout.lostandf_item, list_item_s);
                            listView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            progressDialog.dismiss();
                        }
                    });
                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {return false; }

                @Override
                public boolean onQueryTextChange(final String newText) {
                    //Query query = databaseReference.orderByChild("isbn").startAt(newText).endAt("~");
                    Query query = databaseReference.orderByChild("title").startAt(newText).endAt(newText);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            progressDialog.dismiss();
                            list_item_s.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                item_names ln = snapshot.getValue(item_names.class);
                                list_item_s.add(ln);
                            }
                            adapter = new item_list_Adapter(MarketplaceActivity.this,R.layout.lostandf_item, list_item_s);
                            listView.setAdapter(adapter);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            progressDialog.dismiss();
                        }
                    });
                    return false;
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    /*
                    sendMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MarketplaceActivity.this, MessageUserActivity.class);
                            startActivity(intent);
                        }
                    });*/
                    //ConversationReferenceItem tempListName = conversationRefrences.get(i);
                    //listName = tempListName;
                    //setUsers(listName.getConversationId());
                    //messageList = thirdQuery(listName.getConversationId());
                    //Intent intent = new Intent(ConversationsActivity.this, ChatRoomActivity.class);
                    //startActivity(intent);
                    Intent intent = new Intent(MarketplaceActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                }
            });

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_market, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_lostandfound:
                //go to lost and found
                goto_lost_and_found();
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
        return super.onOptionsItemSelected(item);
    }

    private void loadLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void goto_lost_and_found() {
        Intent intent = new Intent(MarketplaceActivity.this, LostandFActivity.class);
        startActivity(intent);
    }

    private void goto_marketplace_() {
        Intent intent = new Intent(MarketplaceActivity.this, MarketplaceActivity.class);
        startActivity(intent);
    }

    private void goto_advising() {
        Intent intent = new Intent(MarketplaceActivity.this, AdvisingActivity.class);
        startActivity(intent);
    }

    private void goto_conversations() {
        Intent intent = new Intent(MarketplaceActivity.this, ConversationsActivity.class);
        startActivity(intent);
    }

    private void goto_profile() {
        Intent intent = new Intent(MarketplaceActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
