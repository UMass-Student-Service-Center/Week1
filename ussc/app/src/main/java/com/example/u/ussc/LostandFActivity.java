package com.example.u.ussc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LostandFActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    private DatabaseReference databaseReference;
    private ListView listView;
    private List<item_names> list_item_s;
    private item_list_Adapter adapter;
    private SearchView searchView;
    private Button all;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostand_f);
        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();
        //mDatabase = FirebaseDatabase.getInstance().getReference();

        if (mFirebaseUser == null || !(user.isEmailVerified())) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            mUserId = mFirebaseUser.getUid();

            list_item_s = new ArrayList<>();
            listView = (ListView) findViewById(R.id.list_1);
            searchView = (SearchView) findViewById(R.id.searchbook_m);
            all = (Button) findViewById(R.id.all);

            list_item_s = new ArrayList<>();
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait loading.....");
            progressDialog.show();

            databaseReference = FirebaseDatabase.getInstance().getReference(item_LostandFActivity.fb_database);

            progressDialog.dismiss();

            all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            progressDialog.dismiss();
                            list_item_s.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                item_names ln = snapshot.getValue(item_names.class);
                                //if (ln.getMUserId().equals(mUserId))
                                list_item_s.add(ln);
                            }

                            adapter = new item_list_Adapter(LostandFActivity.this, R.layout.lostandf_item, list_item_s);
                            listView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            progressDialog.dismiss();
                        }
                    });
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {return false; }

                @Override
                public boolean onQueryTextChange(final String newText) {
                    //Query query = databaseReference.orderByChild("isbn").startAt(newText).endAt("~");
                    Query query = databaseReference.orderByChild("title").startAt(newText).endAt(newText);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            progressDialog.dismiss();
                            list_item_s.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                item_names ln = snapshot.getValue(item_names.class);
                                list_item_s.add(ln);
                            }
                            adapter = new item_list_Adapter(LostandFActivity.this,R.layout.lostandf_item, list_item_s);
                            listView.setAdapter(adapter);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            progressDialog.dismiss();
                        }
                    });
                    return false;
                }
            });
        /*
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    item_names listName = books_list.get(i);
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference(item_LostandFActivity.fb_database).child(listName.getKey());
                    showUpdateDeleteDialog(listName.getKey(),listName.getTitle(),listName.getIsbn(),listName.getPrice(), listName.getCond() ,listName.getImages(), listName.getMUserId(),i, listName.getMUserEmail());
                    //return true;
                }
            });
            */
        }

    }

    private void loadLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                //
                account();
                // Exit activity
                // finish();
                return true;
            //break;

        }
        return super.onOptionsItemSelected(item);
    }
    //DISPLAY INPUT DIALOG
    private void account() {
        Intent intent = new Intent(LostandFActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
/*
    private void showUpdateDeleteDialog(final String mkey,final String title,final String isbn, String price, String cond,final String muri,final String _muserid, int position,final String _museremail) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.buyers_view, null);
        dialogBuilder.setView(dialogView);
        String dollarSign="$";
        String condition="Condition: ";

        final TextView txt_title = (TextView) dialogView.findViewById(R.id.title_view);
        txt_title.setText(title);
        final TextView txt_isbn = (TextView) dialogView.findViewById(R.id.isbn_view);
        txt_isbn.setText(isbn);
        final TextView txt_price = (TextView) dialogView.findViewById(R.id.price_view);
        txt_price.setText(dollarSign + price);
        final TextView spinnerGenre = (TextView) dialogView.findViewById(R.id.cond_view);
        spinnerGenre.setText(condition + cond);
        final ImageView imageView = (ImageView) dialogView.findViewById(R.id.image_buyer);
        Glide.with(LostandFActivity.this).load(books_list.get(position).getImages()).into(imageView);
        final Button buttonsend = (Button) dialogView.findViewById(R.id.buttonsend);

        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendEmail(_museremail, title);
            }
        });

        dialogBuilder.setTitle("Book View");
        final AlertDialog b = dialogBuilder.create();
        b.show();
    }
    */

}

