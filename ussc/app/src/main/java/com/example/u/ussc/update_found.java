package com.example.u.ussc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class update_found extends AppCompatActivity {

    private EditText txt_title;
    private EditText txt_desc;
    private TextView txt_price;
    private ImageView imageView;
    private String strDate;
    private String editprice;
    private String price_of_item;
    private String image_user;
    private String item_image;
    private String mkey;
    private String user_id;
    private String type;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_found);
        setTitle("Update Found");

        //get the current day and time
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss MM-dd-yyyy");
        strDate = sdf.format(c.getTime());

        DatabaseReference databaseReference;
        imageView = (ImageView) findViewById(R.id.image_view_f);
        txt_title = (EditText) findViewById(R.id.user_found);
        txt_desc = (EditText) findViewById(R.id.user_describe_f);
        txt_price = (TextView) findViewById(R.id.user_amount);

        //method that query firebase using the static variable from profile activity based on the item clicked on the listview
        databaseReference = FirebaseDatabase.getInstance().getReference(item_LostandFActivity.fb_database);
        Query query = databaseReference.orderByChild("item_key").equalTo(ProfileActivity.marketplace_key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    item_names ln = snapshot.getValue(item_names.class);

                    //set all the textviews, edittext and imageview
                    mkey = ln.getItem_key();
                    txt_title.setText(ln.getTitle());
                    txt_desc.setText(ln.getDescr());
                    price_of_item = ln.getPrice();
                    txt_price.setText(" NONE");
                    image_user = ln.getUser_image();
                    item_image = ln.getImage();
                    Glide.with(update_found.this).load(ln.getImage()).into(imageView);
                    name =ln.getName();
                    type = ln.getItem_type();
                    user_id = ln.getUserid();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    // update node function
    private boolean updatemarketplace(String upload_id,String mUserId,String user_names, String type, String Userimages,String title, String image, String desc, String price_of_item, String _strDate) {
        //getting the specified node reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference(item_LostandFActivity.fb_database).child(upload_id);
        //updating book
        item_names s = new item_names(upload_id,mUserId, user_names, type, Userimages, title, image, desc, price_of_item, _strDate);
        dR.setValue(s);
        Intent intent = new Intent(update_found.this, ProfileActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Updated Found", Toast.LENGTH_LONG).show();

        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    // Save the new data to firebase
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (!updatemarketplace(mkey,user_id,name,type,image_user,txt_title.getText().toString(),item_image,txt_desc.getText().toString(),price_of_item,strDate)) {
                    return true;
                }
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
