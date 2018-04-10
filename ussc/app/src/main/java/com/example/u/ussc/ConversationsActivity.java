/**
 * Created by Lundy on 3/29/2018.
 */

package com.example.u.ussc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConversationsActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference databaseReferenceConversation;
    private ProgressDialog progressDialog;
    private ArrayList<ConversationReferenceItem> conversationRefrences;
    private String mUserId;
    private ConversationListAdapter adapter;
    private ListView listView;
    public  String tempUser = "ySjOLxPHiua08gofQoWMznnVFO92";
    public static final String fb_conversation_database = "Conversation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId = mFirebaseUser.getUid();
        databaseReferenceConversation = FirebaseDatabase.getInstance().getReference(fb_conversation_database);

        conversationRefrences = new ArrayList<ConversationReferenceItem>(0);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading.....");
        progressDialog.show();
        progressDialog.dismiss();

        //ProfileItem pi1 = firstQuery();
        //firstQuery();
        //secondQuery();

        //add (needs to be changed to write messages)
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConversationsActivity.this, MessageUserActivity.class);
                startActivity(intent);
            }
        });
    }
/*
    // Get the first query
    private void firstQuery() {
        Query query = databaseReferenceConversation.orderByChild("muserId1").startAt(mUserId).endAt(mUserId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ProfileItem pi = snapshot.getValue(ProfileItem.class);
                    //userInfo1 = pi;
                    //ProfileItem pi2 = secondQuery();
                    ConversationItem ci = snapshot.getValue(ConversationItem.class);
                    ConversationReferenceItem cri = new ConversationReferenceItem(ci.getConversationId(),
                            ci.getMuserId1(), ci.getMuser1Image(), "hello", ci.getMessageKeys());
                    conversationRefrences.add(cri);
                    if (!conversationRefrences.isEmpty()){
                        Log.e("LOGGED MESSAGE", "Something was added");
                    }else{
                        Log.e("LOGGED MESSAGE", ci.getConversationId());
                    }
                }
                secondQuery();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        //return userInfo1;
    }

    // Get the second query
    private void secondQuery() {
        Query query2 = databaseReferenceConversation.orderByChild("muserId2").startAt(mUserId).endAt(mUserId);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("LOGGED MESSAGE", "Something was added");
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ProfileItem pi = snapshot.getValue(ProfileItem.class);
                    //userInfo2 = pi;
                    ConversationItem ci = snapshot.getValue(ConversationItem.class);
                    Log.e("LOGGED MESSAGE", ci.getConversationId());
                    ConversationReferenceItem cri = new ConversationReferenceItem(ci.getConversationId(),
                            ci.getMuserId1(), ci.getMuser1Image(), "hello", ci.getMessageKeys());
                    conversationRefrences.add(cri);
                    if (!conversationRefrences.isEmpty()){
                        Log.e("LOGGED MESSAGE", "Something was added");
                    }else{
                        Log.e("LOGGED MESSAGE", ci.getConversationId());
                    }

                }
                adapter = new ConversationListAdapter(ConversationsActivity.this,R.layout.activity_conversations, conversationRefrences);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        //return userInfo2;
    }
    */
}