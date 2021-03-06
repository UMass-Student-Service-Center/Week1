package com.example.u.ussc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    public static String marketplace_key;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference_1;
    private DatabaseReference databaseReference_2;
    private DatabaseReference databaseReference_3;
    private Query query_f;
    private Query query_l;
    private String sandf_query;
    private String mUserId;
    private ListView listView_1;
    private List<item_names> list_item_1;
    private List<item_names> list_item_2;
    private item_list_Adapter adapter;
    private String user_names;
    private String Userimages;
    private String User_year;
    private TextView type_text;
    private Button market;
    private Button lost;
    private Button found;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

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
            final TextView TextView3 = (TextView) findViewById(R.id.view_type);

            //query firebase from profile's child  and set up the current user textviews and imageview
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
            listView_1 = (ListView) findViewById(R.id.list1);
            market = (Button) findViewById(R.id.market_1);
            lost = (Button) findViewById(R.id.lost_2);
            found = (Button) findViewById(R.id.found_2);

            databaseReference_1 = FirebaseDatabase.getInstance().getReference(item_LostandFActivity.fb_database);
            databaseReference_2 = FirebaseDatabase.getInstance().getReference(add_item_marketplaceActivity.fb_database);
            query_f = databaseReference_1.orderByChild("item_type").equalTo("Found Item");
            query_l = databaseReference_1.orderByChild("item_type").equalTo("Lost Item");
            lost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    query_l.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //progressDialog.dismiss();
                            list_item_1.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                item_names ln = snapshot.getValue(item_names.class);
                                if(ln.getUserid().equals(mUserId)) {
                                    list_item_1.add(ln);
                                }
                            }
                            adapter = new item_list_Adapter(ProfileActivity.this,R.layout.lostandfprofile_item, list_item_1);
                            listView_1.setAdapter(adapter);
                            TextView3.setText("Lost");

                            //using a temp variable that it will be use in if statement for update button created in alert dialog builder
                            sandf_query = "lost";
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                           // progressDialog.dismiss();
                        }
                    });
                }
            });

            //if found button is clicked it would only show the current user found items
            found.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    query_f.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //progressDialog.dismiss();
                            list_item_1.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                item_names ln = snapshot.getValue(item_names.class);
                                if(ln.getUserid().equals(mUserId)) {
                                    list_item_1.add(ln);
                                }
                            }
                            adapter = new item_list_Adapter(ProfileActivity.this,R.layout.lostandfprofile_item, list_item_1);
                            listView_1.setAdapter(adapter);
                            TextView3.setText("Found");

                            //using a temp variable that it will be use in if statement for update button created in alert dialog builder
                            sandf_query = "found";
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            });

            //if market button is clicked it would only show the current user for sale items
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

                            adapter = new item_list_Adapter(ProfileActivity.this, R.layout.lostandfprofile_item, list_item_1);
                            listView_1.setAdapter(adapter);
                            databaseReference_3 = databaseReference_2;
                            TextView3.setText("Marketplace");

                            //using a temp variable that it will be use in if statement for update button created in alert dialog builder
                            sandf_query ="marketplace";
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            });

            //listview and it would get the item key from selected item
            listView_1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    item_names listName = list_item_1.get(i);
                    marketplace_key = listName.getItem_key();
                   showUpdateDeleteDialog(listName.getItem_key());
                    return true;
                }
            });

        }

    }

    //menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    //menu bar items
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //go to lost and found
            case R.id.action_lostandfound:
                goto_lost_and_found();
                return true;
            //go to marketplace
            case R.id.action_market:
                goto_marketplace_();
                return true;
            //go to advising
            case R.id.action_advising:
                goto_advising();
                return true;
            //go to chat
            case R.id.action_chat:
                goto_conversations();
                return true;
            // add item
            case R.id.add:
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

    //method that creates an alert dialog builder with two buttons update and delete
    private void showUpdateDeleteDialog(final String mkey){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_and_update, null);
        dialogBuilder.setView(dialogView);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);
        dialogBuilder.setTitle("UPDATE OR DELETE");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if statement based on user selection it will go to one of three updates activities
                if(sandf_query.equals("lost")){
                    Intent intent = new Intent(ProfileActivity.this, update_lost.class);
                    startActivity(intent);

                }else if(sandf_query.equals("found")){
                    Intent intent = new Intent(ProfileActivity.this, update_found.class);
                    startActivity(intent);

                }else{

                   Intent intent = new Intent(ProfileActivity.this, update_marketplace.class);
                   startActivity(intent);
                }
            }
        });

        //if the user would like to delete item
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booksremove(mkey);
                list_item_1.clear();
                adapter.notifyDataSetChanged();
                b.dismiss();
            }
        });
    }

    //delete node function
    public boolean booksremove(String id){
        //getting rhe specified node reference
        DatabaseReference dR = databaseReference_3.child(id);
        //removing node
        dR.removeValue();
        return true;
    }

}
