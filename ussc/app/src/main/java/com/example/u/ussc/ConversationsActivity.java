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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ConversationsActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference databaseReferenceConversation;
    private DatabaseReference databaseReferenceMessage;
    private ProgressDialog progressDialog;
    private ArrayList<ConversationReferenceItem> conversationRefrences;
    private String mUserId;
    private ConversationListAdapter adapter;
    private ListView listView;
    public static ConversationReferenceItem listName;
    public  String tempUser = "ySjOLxPHiua08gofQoWMznnVFO92";
    public static ArrayList<MessageItem> messageList;
    public ArrayList<MessageItem> tempMessageList;
    public static final String fb_conversation_database = "Conversation";
    public static final String fb_message_database = "Message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId = mFirebaseUser.getUid();
        databaseReferenceConversation = FirebaseDatabase.getInstance().getReference(fb_conversation_database);
        databaseReferenceMessage = FirebaseDatabase.getInstance().getReference(fb_message_database);

        listView = (ListView) findViewById(R.id.conversation_list);

        conversationRefrences = new ArrayList<ConversationReferenceItem>(0);
        messageList = new ArrayList<MessageItem>(0);
        tempMessageList = new ArrayList<MessageItem>(0);

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
                            ci.getMuserId1(), ci.getMuser1Image(), ci.getLastMessage(), ci.getLastMessageDate(),
                            ci.getMessageKeys());
                    conversationRefrences.add(cri);
                    /*
                    if (!conversationRefrences.isEmpty()){
                        Log.e("LOGGED MESSAGE", "Something was added");
                    }else{
                        Log.e("LOGGED MESSAGE", ci.getConversationId());
                    }
                    */
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
                    //ProfileItem pi = snapshot.getValue(ProfileItem.class);
                    //userInfo2 = pi;
                    MessageItem mi = snapshot.getValue(MessageItem.class);
                    //Log.e("LOGGED MESSAGE", mi.getConversationId());
                    //messageList.add(mi);
                    tempMessageList.add(mi);
                    //tempMessageList = messageList;
                    /*
                    if (messageList.isEmpty()){
                        Log.e("LOGGED MESSAGE", "it's empty");
                    }else{
                        Log.e("mi.getConversationId", messageList.get(0).getConversationId());
                        Log.e("mi.getLastMessage", messageList.get(0).getMessage());
                        Log.e("mi.getMessageDate", messageList.get(0).getMessageDate());
                        Log.e("mi.getMuserId1", messageList.get(0).getSenderId());
                    }
                    */
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        return tempMessageList;
    }

    public static ArrayList<MessageItem> getMessageList() {
        return messageList;
    }
/*
    public void sort(ArrayList<MessageItem> ml){
        int size = ml.size();
        if (size >= 1) {
            String minDate = ml.get(0).getMessageDate();
            for (int i = 0; i < size; i++){

            }
        }
    }
    */
}
