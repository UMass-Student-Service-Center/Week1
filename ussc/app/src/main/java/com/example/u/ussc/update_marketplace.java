package com.example.u.ussc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class update_marketplace extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 0;
    private EditText txt_title;
    private EditText txt_desc;
    private EditText txt_price;
    private ImageView imageView;
    private String strDate;
    private item_list_Adapter adapter;
    public static final String fb_storage = "image/";
    public static final String fb_database = "Marketplace";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_marketplace);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss MM-dd-yyyy");
        strDate = sdf.format(c.getTime());
        DatabaseReference databaseReference;
        imageView = (ImageView) findViewById(R.id.image_view_s);
        txt_title = (EditText) findViewById(R.id.user_sel);
        txt_desc = (EditText) findViewById(R.id.user_describe_s);
        txt_price = (EditText) findViewById(R.id.user_amount);

        databaseReference = FirebaseDatabase.getInstance().getReference(add_item_marketplaceActivity.fb_database);
        Query query = databaseReference.orderByChild("item_key").equalTo(ProfileActivity.marketplace_key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    item_names ln = snapshot.getValue(item_names.class);
                    //user_names = ln.getName();
                    //Userimages =
                    //(key,user_id,user_n,type ,u_image ,mtitle,mimage,mdecr,mprice,mtime)
                    //ln.getItem_key(),ln.getUserid(),ln.getName(),ln.getItem_type(),ln.getUser_image(),ln.getTitle(),ln.getImage(),ln.getDescr(),ln.getPrice(),strDate
                    updatemarketplace(ln.getItem_key(),ln.getUserid(),ln.getName(),ln.getItem_type(),ln.getUser_image(),ln.getTitle(),ln.getImage(),ln.getDescr(),ln.getPrice(),strDate);
                    txt_title.setText(ln.getTitle());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //progressDialog.dismiss();
            }
        });
    }
    // update node function
    private boolean updatemarketplace(String upload_id,String mUserId,String user_names, String type, String Userimages,String title, String image, String desc, String price_of_item, String _strDate) {
        //getting the specified node reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference(add_item_marketplaceActivity.fb_database).child(upload_id);
        //updating book
         item_names s = new item_names(upload_id,mUserId, user_names, type, Userimages, title, image, desc, price_of_item, _strDate);
       // ListName ln = new ListName(mkey,mtitle,misbn,mprice,_cond,muri,_muserid, _museremail);
        dR.setValue(s);
        Toast.makeText(getApplicationContext(), "Updated Marketplace", Toast.LENGTH_LONG).show();
        //books_list.clear();
        adapter.notifyDataSetChanged();
        return true;
    }
}
