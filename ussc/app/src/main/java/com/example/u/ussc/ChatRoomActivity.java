package com.example.u.ussc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private ArrayList<String> messageKeys;
    private int arraySize;
    private String messageToSend;
    private ConversationItem ci;
    private ListView listView;
    public ArrayList<MessageItem> messageList;
    public String conversationID;
    public static String ConversationID;


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

        /*
        STILL NEED TO CREATE A NEW MESSAGE AND THEN ADD IT TO THE FIREBASE, AND THEN ADD THE MESSAGE
        KEY TO messageKeys AND SET THE messageKeys CHILD OF CONVERSATION ON THE FIREBASE TO THE NEW
        messageKeys IF POSSIBLE. IF NOT POSSIBLE I WILL HAVE TO DELETE THE CURRENT CONVERSATION CHILD
        UPDATE IT ON THIS PAGE AND THEN RESEND THE SAME CONVERSATIONITEM BACK TO FIREBASE.
         */

        listView = (ListView)findViewById(R.id.chatRoomList);
        ChatRoomAdapter adapter = new ChatRoomAdapter(ConversationsActivity.messageList,this);
        listView.setAdapter(adapter);

        message = (EditText)findViewById(R.id.messageTxt);
        conversationID = ConversationsActivity.listName.getConversationId();

        messageKeys = new ArrayList<String>(0);
        messageKeys = ConversationsActivity.listName.getMessageKeys();
        arraySize = messageKeys.size();

        //add (needs to be changed to write messages)
        send = (Button)findViewById(R.id.btnSend);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageToSend = message.getText().toString();
                addQuery();
                Intent intent = new Intent(ChatRoomActivity.this, ConversationsActivity.class);
                startActivity(intent);
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
                    ci = snapshot.getValue(ConversationItem.class);
                    Log.e("ConversationID", ci.getConversationId());
                    sendMessage();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        //sendMessage();
    }

    // update node function
    private void sendMessage() {
        final String upload_id =  ConversationsActivity.listName.getConversationId();
        final String message_upload_id = databaseReferenceMessage.push().getKey();
        final String messageToSend = message.getText().toString();;

        MessageItem mi = new MessageItem(message_upload_id, upload_id, mUserId, messageToSend);

        final String lastMessage = mi.getMessage();
        final String lastMessageDate = mi.getMessageDate();


        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Uploading");
        dialog.show();

        //set data
        //ConversationItem tempCI = ci;
        ci.setLastMessage(mi.getMessage());
        ci.setLastMessageDate(mi.getMessageDate());
        ci.addMessageKey(mi.getMessageId());

        //save data
        databaseReferenceMessage.child(message_upload_id).setValue(mi);
        databaseReferenceConversation.child(upload_id).child("lastMessage").setValue(mi.getMessage());
        databaseReferenceConversation.child(upload_id).child("lastMessageDate").setValue(mi.getMessageDate());
        dialog.dismiss();
    }
}