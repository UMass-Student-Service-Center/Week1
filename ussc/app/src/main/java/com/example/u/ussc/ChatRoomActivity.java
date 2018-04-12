package com.example.u.ussc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    protected EditText message;
    public static final String fb_conversation_database = "Conversation";
    public static final String fb_message_database = "Message";
    private FirebaseAuth mFirebaseAuth;
    private StorageReference mStorageRef;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReferenceConversation;
    private DatabaseReference databaseReferenceMessage;
    private FirebaseUser mFirebaseUser;
    private String mUserId;
    private Button send;
    private ConversationItem currentConverstion;
    private String conversationID;
    private ArrayList<String> messageKeys;
    private int arraySize;
    private String messageToSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId = mFirebaseUser.getUid();

        databaseReferenceConversation = FirebaseDatabase.getInstance().getReference(fb_conversation_database);
        databaseReferenceMessage = FirebaseDatabase.getInstance().getReference(fb_message_database);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading.....");
        progressDialog.show();
        progressDialog.dismiss();

        message = (EditText)findViewById(R.id.messageTxt);

        arraySize = ConversationsActivity.messageList.size();
        messageKeys = new ArrayList<String>(0);
        for (int i = 0; i < arraySize; i++){
            messageKeys.add(ConversationsActivity.messageList.get(i).getMessageId());
        }

        /*
        STILL NEED TO CREATE A NEW MESSAGE AND THEN ADD IT TO THE FIREBASE, AND THEN ADD THE MESSAGE
        KEY TO messageKeys AND SET THE messageKeys CHILD OF CONVERSATION ON THE FIREBASE TO THE NEW
        messageKeys IF POSSIBLE. IF NOT POSSIBLE I WILL HAVE TO DELETE THE CURRENT CONVERSATION CHILD
        UPDATE IT ON THIS PAGE AND THEN RESEND THE SAME CONVERSATIONITEM BACK TO FIREBASE.
         */

        conversationID = ConversationsActivity.messageList.get(0).getConversationId();

        ListView listView = (ListView)findViewById(R.id.chatRoomList);
        ChatRoomAdapter adapter = new ChatRoomAdapter(ConversationsActivity.messageList,this);
        listView.setAdapter(adapter);
        //add (needs to be changed to write messages)
        send = (Button)findViewById(R.id.btnSend);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageToSend = message.getText().toString();
                //addQuery();

            }
        });
    }

    private void addQuery(){
        Query query1 = databaseReferenceConversation.orderByChild("conversationId").startAt(conversationID).endAt(conversationID);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ConversationItem ci = snapshot.getValue(ConversationItem.class);
                    currentConverstion = ci;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
}