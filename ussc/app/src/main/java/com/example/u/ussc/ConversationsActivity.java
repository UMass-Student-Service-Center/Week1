/**
 * Created by Lundy on 3/29/2018.
 */

package com.example.u.ussc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    private DatabaseReference databaseReferenceMessage;
    private static DatabaseReference staticDatabaseReferenceMessage;
    private ProgressDialog progressDialog;
    private ArrayList<ConversationReferenceItem> conversationRefrences;
    private String mUserId;
    private ConversationListAdapter adapter;
    private ListView listView;
    public static ConversationReferenceItem listName;
    public  String tempUser = "ySjOLxPHiua08gofQoWMznnVFO92";
    public static ArrayList<MessageItem> messageList;
    public ArrayList<MessageItem> tempMessageList;
    public static ArrayList<MessageItem> tempMessageList2;
    public static final String fb_conversation_database = "Conversation";
    public static final String fb_message_database = "Message";
    public static String sendUser;
    public static String receiveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId = mFirebaseUser.getUid();
        databaseReferenceConversation = FirebaseDatabase.getInstance().getReference(fb_conversation_database);
        staticDatabaseReferenceMessage = FirebaseDatabase.getInstance().getReference(fb_message_database);
        databaseReferenceMessage = FirebaseDatabase.getInstance().getReference(fb_message_database);

        listView = (ListView) findViewById(R.id.conversation_list);

        conversationRefrences = new ArrayList<ConversationReferenceItem>(0);
        messageList = new ArrayList<MessageItem>(0);
        tempMessageList = new ArrayList<MessageItem>(0);
        tempMessageList2 = new ArrayList<MessageItem>(0);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading.....");
        progressDialog.show();
        progressDialog.dismiss();

        //ProfileItem pi1 = firstQuery();
        firstQuery();
        //secondQuery();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                ConversationReferenceItem tempListName = conversationRefrences.get(i);
                listName = tempListName;
                setUsers(listName.getConversationId());
                messageList = thirdQuery(listName.getConversationId());
                Intent intent = new Intent(ConversationsActivity.this, ChatRoomActivity.class);
                startActivity(intent);
            }
        });

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_conversations, menu);
        return true;
    }

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
            //go to profile
            case R.id.action_profile:
                goto_profile();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Get the first query
    private void firstQuery() {
        Query query = databaseReferenceConversation.orderByChild("muserId1").startAt(mUserId).endAt(mUserId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ConversationItem ci = snapshot.getValue(ConversationItem.class);
                    ConversationReferenceItem cri = new ConversationReferenceItem(ci.getConversationId(),
                            ci.getMuserName2(), ci.getMuser2Image(), ci.getLastMessage(), ci.getLastMessageDate(),
                            ci.getMessageKeys());
                    conversationRefrences.add(cri);
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
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ProfileItem pi = snapshot.getValue(ProfileItem.class);
                    //userInfo2 = pi;
                    ConversationItem ci = snapshot.getValue(ConversationItem.class);
                    ConversationReferenceItem cri = new ConversationReferenceItem(ci.getConversationId(),
                            ci.getMuserName1(), ci.getMuser1Image(), ci.getLastMessage(), ci.getLastMessageDate(),
                            ci.getMessageKeys());
                    conversationRefrences.add(cri);
                }
                adapter = new ConversationListAdapter(ConversationsActivity.this,R.layout.list_conversation, conversationRefrences);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        //return userInfo2;
    }

    private ArrayList<MessageItem> thirdQuery(String conversationID) {
        //messageList.clear();
        tempMessageList.clear();
        Query query3 = databaseReferenceMessage.orderByChild("conversationId").startAt(conversationID).endAt(conversationID);
        query3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.e("LOGGED MESSAGE", "Something was added");
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MessageItem mi = snapshot.getValue(MessageItem.class);
                    tempMessageList.add(mi);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        return tempMessageList;
    }

    private static ArrayList<MessageItem> fourthQuery(String conversationID) {
        //messageList.clear();
        //tempMessageList.clear();
        tempMessageList2.clear();
        Query query4 = staticDatabaseReferenceMessage.orderByChild("conversationId").startAt(conversationID).endAt(conversationID);
        query4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.e("LOGGED MESSAGE", "Something was added");
                //progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MessageItem mi = snapshot.getValue(MessageItem.class);
                    tempMessageList2.add(mi);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //progressDialog.dismiss();
            }
        });
        return tempMessageList2;
    }

    public void setUsers(String conversationID) {
        Query query5 = databaseReferenceConversation.orderByChild("conversationId").startAt(conversationID).endAt(conversationID);
        query5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.e("LOGGED MESSAGE", "Something was added");
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ConversationItem ci = snapshot.getValue(ConversationItem.class);
                    if(ci.getMuserId1().equals(mUserId)) {
                        sendUser = ci.getMuserName1();
                        receiveUser = ci.getMuserName2();
                    } else {
                        sendUser = ci.getMuserName2();
                        receiveUser = ci.getMuserName1();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }

    public static ArrayList<MessageItem> getMessageList() {
        return messageList;
    }

    public static void refreshMessageList(){
        messageList = fourthQuery(listName.getConversationId());
    }
    private void goto_lost_and_found() {
        Intent intent = new Intent(ConversationsActivity.this, LostandFActivity.class);
        startActivity(intent);
    }

    private void goto_marketplace_() {
        Intent intent = new Intent(ConversationsActivity.this, MarketplaceActivity.class);
        startActivity(intent);
    }

    private void goto_advising() {
        Intent intent = new Intent(ConversationsActivity.this, AdvisingActivity.class);
        startActivity(intent);
    }


    private void goto_profile() {
        Intent intent = new Intent(ConversationsActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
