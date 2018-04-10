package com.example.u.ussc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class item_foundandL extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 0;
    private EditText txt_title;
    private EditText txt_desc;
    private EditText txt_price;
    private String title;
    private String desc;
    private String price;
    //private String sspinner;
    //private Spinner mspinner;
    private ImageView imageView;
    private Uri actualUri;
    private StorageReference mStorageRef;
    private DatabaseReference ref;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference databaseReference;
    private DatabaseReference mDatabase;
    private String mUserId;
    private String strDate;

    private String user_names;
    private String Userimages;
    public static final String fb_storage = "image/";
    public static final String fb_database = "LostandFound";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_foundand_l);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        ref = FirebaseDatabase.getInstance().getReference(fb_database);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserId = mFirebaseUser.getUid();

        imageView = (ImageView) findViewById(R.id.image_view_f);
        txt_title = (EditText) findViewById(R.id.user_found);
        txt_desc = (EditText) findViewById(R.id.user_describe_f);
        //txt_price = (EditText) findViewById(R.id.user_amount);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        strDate = sdf.format(c.getTime());

        databaseReference = FirebaseDatabase.getInstance().getReference(RegistrationActivity.fb_database);

        Query query = databaseReference.orderByChild("muserId").equalTo(mUserId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ProfileItem ln = snapshot.getValue(ProfileItem.class);
                    user_names = ln.getName();
                    Userimages = ln.getImages();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //progressDialog.dismiss();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }


    public void openImageSelector(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code READ_REQUEST_CODE.
        // If the request code seen here doesn't match, it's the response to some other intent,
        // and the below code shouldn't run at all.
        super.onActivityResult(requestCode,resultCode, resultData);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && resultData != null && resultData.getData() != null) {
            actualUri = resultData.getData();
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(),actualUri);
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

    //DISPLAY INPUT DIALOG
    private void savebook() {
        //get data
        title = txt_title.getText().toString();
        desc = txt_desc.getText().toString();
        price = "none";
        //sspinner = mspinner.getSelectedItem().toString();



        if(title != null && desc !=null && price != null &&  actualUri != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading");
            dialog.show();
            mUserId = mFirebaseUser.getUid();

            StorageReference sf = mStorageRef.child(fb_storage + System.currentTimeMillis() + "." + getImageExt(actualUri));

            sf.putFile(actualUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "uploaded",Toast.LENGTH_SHORT).show();

                    //set data
                    //item_names(String user_id,String mtitle,String mimage,String mdecr,String mprice,String mtime)
                    item_names s = new item_names(mUserId,user_names,Userimages, txt_title.getText().toString(),taskSnapshot.getDownloadUrl().toString(), txt_desc.getText().toString(),price,strDate);

                    //save data
                    String upload_id = ref.push().getKey();
                    ref.child(upload_id).setValue(s);
                    // ref.child("Books").push().setValue(s);
                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded" + (int) progress + "0");

                        }
                    });
            dialog.dismiss();
        }else {
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
        }
                 /*
                 ref.child("Title").setValue(title);
                 ref.child("ISBN").setValue(isbn);
                 ref.child("Price").setValue(price);
                 ref.child("Books").push().setValue(s);
                 */


        Intent intent = new Intent(item_foundandL.this, ProfileActivity.class);
        startActivity(intent);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                // Save pet to database
                savebook();
                // Exit activity
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
//}

