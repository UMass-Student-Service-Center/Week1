package com.example.u.ussc;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.app.Dialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;
import com.google.firebase.database.DatabaseError;
import android.app.ProgressDialog;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.lang.Thread;

public class MessageUserActivity extends AppCompatActivity{
    private static final int PICK_IMAGE_REQUEST = 0;
    protected EditText uidToSendTo;
    protected EditText message;
    private Uri actualUri;
    private Button imageBtn;
    protected Button signUpButton;
    private FirebaseAuth mFirebaseAuth;
    private StorageReference mStorageRef;
    private DatabaseReference ref;
    private DatabaseReference databaseReferenceConversation;
    private DatabaseReference databaseReferenceProfile;
    private DatabaseReference databaseReferenceMessage;
    private ProgressDialog progressDialog;
    public static final String fb_profile_database = "Profile";
    public static final String fb_conversation_database = "Conversation";
    public static final String fb_message_database = "Message";
    private FirebaseUser mFirebaseUser;
    private String mUserId;
    private String receiverID;
    private String messageToSend;
    private ProfileItem userInfo1;
    private ProfileItem userInfo2;
    private String currentUsername = MarketplaceActivity.currentUsername;
    private String currentUserID = MarketplaceActivity.currentUserID;
    private static final String TAG = "SignUp";
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId = mFirebaseUser.getUid();

        ref = FirebaseDatabase.getInstance().getReference(fb_conversation_database);
        databaseReferenceConversation = FirebaseDatabase.getInstance().getReference(fb_conversation_database);
        databaseReferenceProfile = FirebaseDatabase.getInstance().getReference(fb_profile_database);
        databaseReferenceMessage = FirebaseDatabase.getInstance().getReference(fb_message_database);

        uidToSendTo = (EditText) findViewById(R.id.editText2);
        uidToSendTo.setText(currentUsername);
        message = (EditText)findViewById(R.id.editText3);
        imageBtn = (Button) findViewById(R.id.text_mess);


        userInfo1 = new ProfileItem();
        userInfo2 = new ProfileItem();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading.....");
        progressDialog.show();
        progressDialog.dismiss();

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            receiverID = currentUserID;
            messageToSend = message.getText().toString();
            ProfileItem pi1 = firstQuery();
            }
        });
    }

    // Get the first query
    private ProfileItem firstQuery(){
        Query query = databaseReferenceProfile.orderByChild("muserId").startAt(receiverID).endAt(receiverID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ProfileItem pi = snapshot.getValue(ProfileItem.class);
                    userInfo1 = pi;
                    ProfileItem pi2 = secondQuery();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        return userInfo1;
    }

    // Get the second query
    private ProfileItem secondQuery(){
        Query query2 = databaseReferenceProfile.orderByChild("muserId").startAt(mUserId).endAt(mUserId);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ProfileItem pi = snapshot.getValue(ProfileItem.class);
                    userInfo2 = pi;
                    createConversation(userInfo1.getMUserId(), userInfo2.getMUserId(), userInfo1.getImages(),
                            userInfo2.getImages(), userInfo1.getName(), userInfo2.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        return userInfo2;
    }

    // update node function
    private boolean createConversation(String _muserid1, String _muserid2, String _actualUri1, String _actualUri2,
                                       String _muserName1, String _muserName2) {
        final String upload_id =  ref.push().getKey();
        final String message_upload_id = databaseReferenceMessage.push().getKey();
        final String userId1 = _muserid1;
        final String userId2 = _muserid2;
        final String actualUri1 = _actualUri1;
        final String actualUri2 = _actualUri2;
        final String userName1 = _muserName1;
        final String userName2 = _muserName2;
        final String _messageToSend = messageToSend;

        MessageItem mi = new MessageItem(message_upload_id, upload_id, userId2, _messageToSend);

        final String lastMessage = mi.getMessage();
        final String lastMessageDate = mi.getMessageDate();


        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Uploading");
        dialog.show();

        //set data
        ConversationItem ci = new ConversationItem(upload_id, userId1, userId2, actualUri1, actualUri2,
                userName1, userName2, lastMessage, lastMessageDate, message_upload_id);

        //save data
        databaseReferenceMessage.child(message_upload_id).setValue(mi);
        ref.child(upload_id).setValue(ci);
        dialog.dismiss();
        dialog.setTitle("Message Sent");
        dialog.show();
        dialog.dismiss();
        finish();

        return true;

    }
}
