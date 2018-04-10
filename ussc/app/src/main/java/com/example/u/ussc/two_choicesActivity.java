package com.example.u.ussc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class two_choicesActivity extends AppCompatActivity {
    private Button marketPlace;
    private Button lost_and_found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_choices);

        lost_and_found = (Button) findViewById(R.id.lostandfound_1);
        marketPlace = (Button) findViewById(R.id.Marketplace_1);

        lost_and_found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto_lost_and_found();
            }
        });

        marketPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto_marketplace_();
            }
        });
    }

    private void goto_lost_and_found() {
        Intent intent = new Intent(two_choicesActivity.this, add_lostorfoundActivity.class);
        startActivity(intent);
    }

    private void goto_marketplace_() {
        Intent intent = new Intent(two_choicesActivity.this, new_item_MarketplaceActivity.class);
        startActivity(intent);
    }
}
