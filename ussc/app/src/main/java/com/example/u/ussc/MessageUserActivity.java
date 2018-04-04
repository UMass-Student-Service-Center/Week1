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
    private ProgressDialog progressDialog;
    public static final String fb_profile_database = "Profile";
    public static final String fb_message_database = "Conversation";
    private FirebaseUser mFirebaseUser;
    private String mUserId;
    private List<ProfileItem> members_list;
    private static final String TAG = "SignUp";
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId = mFirebaseUser.getUid();

        ref = FirebaseDatabase.getInstance().getReference(fb_message_database);
        databaseReferenceConversation = FirebaseDatabase.getInstance().getReference(fb_message_database);
        databaseReferenceProfile = FirebaseDatabase.getInstance().getReference(fb_profile_database);

        uidToSendTo = (EditText) findViewById(R.id.editText2);
        message = (EditText)findViewById(R.id.editText3);
        imageBtn = (Button) findViewById(R.id.text_mess);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading.....");
        progressDialog.show();
        progressDialog.dismiss();

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Query query = databaseReferenceProfile.orderByChild("muserId").startAt(uidToSendTo.toString()).endAt(uidToSendTo.toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        members_list.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            ProfileItem pi = snapshot.getValue(ProfileItem.class);
                            members_list.add(pi);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });
*/
                Query query2 = databaseReferenceProfile.orderByChild("muserId").startAt(mUserId).endAt(mUserId);
                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Log.e("user 2 id:", mUserId);
                            ProfileItem pi = snapshot.getValue(ProfileItem.class);
                            //members_list.add(pi);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });


                //ProfileItem pi1 = members_list.get(0);
                //ProfileItem pi2 = members_list.get(0);

                //Log.e("user 2 id:", mUserId);

                //createConversation(pi1.getMUserId(), pi2.getMUserId(), pi1.getImages(), pi2.getImages());

            }
        });
    }
    // update node function
    private boolean createConversation(String _muserid1, String _muserid2, String _actualUri1, String _actualUri2) {
        final String upload_id =  ref.push().getKey();
        final String userId1 = _muserid1;
        final String userId2 = _muserid2;
        final String actualUri1 = _actualUri1;
        final String actualUri2 = _actualUri2;

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Uploading");
        dialog.show();

        //set data
        ConversationItem ci = new ConversationItem(upload_id, userId1, userId2, actualUri1, actualUri2);

        //save data
        //String upload_id = ref.push().getKey();
        ref.child(upload_id).setValue(ci);

        return true;

    }
}
