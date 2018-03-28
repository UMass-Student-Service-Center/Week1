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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import android.app.ProgressDialog;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


public class RegistrationActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 0;
    protected EditText fullNameEditText;
    protected EditText passwordEditText;
    protected EditText emailEditText;
    private ImageView imageView;
    private Uri actualUri;
    private Button imageBtn;
    protected Button signUpButton;
    private FirebaseAuth mFirebaseAuth;
    private StorageReference mStorageRef;
    private DatabaseReference ref;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    public static final String fb_storage = "image/";
    public static final String fb_database = "Profile";
    private FirebaseUser mFirebaseUser;
    private String mUserId;
    private static final String TAG = "SignUp";

    Bitmap bm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        ref = FirebaseDatabase.getInstance().getReference(fb_database);

        imageView = (ImageView) findViewById(R.id.profile_image);
       // fullNameEditText = (EditText) findViewById(R.id.fullnameField);
        passwordEditText = (EditText)findViewById(R.id.passwordField);
        emailEditText = (EditText)findViewById(R.id.emailField);
        signUpButton = (Button)findViewById(R.id.signupButton);
        imageBtn = (Button) findViewById(R.id.select_image);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final String fullName = fullNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();

                //set user's first name
                String first_remove = email.replaceAll("@student.uml.edu","");
                String second_remove = first_remove.replaceAll("[_]+[A-Z0-9a-z._%+-]{1,61}","");
                final String fullName = second_remove;

                password = password.trim();
                email = email.trim();
                final String userEmail = email;

                if (!isValidEmail(emailEditText.getText().toString())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                    builder.setMessage(R.string.signup_invaild_email_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else if (password.isEmpty() || email.isEmpty() ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                    builder.setMessage(R.string.signup_error_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else if (actualUri == null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                    builder.setMessage(R.string.signup_missing_image_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //get the current user id
                                        mFirebaseUser = mFirebaseAuth.getCurrentUser();
                                        mUserId = mFirebaseUser.getUid();
                                        sendEmailVerification();
                                        createProfile(mUserId, userEmail, fullName, actualUri);
                                        Intent intent = new Intent(RegistrationActivity.this, LogInActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                                        builder.setMessage(task.getException().getMessage())
                                                .setTitle(R.string.login_error_title)
                                                .setPositiveButton(android.R.string.ok, null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }
                            });
                }
            }
        });
    }

    public void openImageSelector(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode,resultCode, resultData);
        if ((requestCode == PICK_IMAGE_REQUEST) &&
                (resultCode == Activity.RESULT_OK) &&
                (resultData != null) &&
                (resultData.getData() != null)) {
            actualUri = resultData.getData();
            try {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(),actualUri);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getImageExt (Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    Boolean isValidEmail(String testEmail) {
        String emailREGEX = "[A-Z0-9a-z._%+-]{1,61}+@[student]+\\.[uml]+\\.[edu]*$";
        String emailTest = testEmail;
        Pattern p = Pattern.compile(emailREGEX);
        Matcher m = p.matcher(emailTest);   // get a matcher object
        Boolean result = emailTest.matches(emailREGEX);
        return result;

    }

    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(RegistrationActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    // update node function
    private boolean createProfile(String _muserid, String _museremail, String _mname, Uri _actualUri) {
        final String upload_id =  ref.push().getKey();
        final String userId = _muserid;
        final String userEmail = _museremail;
        final String usersName = _mname;
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        ProfileItem pi = new ProfileItem(userId, userEmail, usersName,
                date.toString(), _actualUri.toString());
        ref.child(upload_id).setValue(pi);
        Toast.makeText(getApplicationContext(), "Profile Created", Toast.LENGTH_LONG).show();
        return true;
    }
}




