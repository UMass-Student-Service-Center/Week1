package com.example.u.ussc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class add_lostorfoundActivity extends AppCompatActivity {
    private Button lost;
    private Button found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lostorfound);

        lost = (Button) findViewById(R.id.lost);
        found = (Button) findViewById(R.id.found);

        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto_lost();
            }
        });

        found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto_found();
            }
        });
    }

    private void goto_lost() {
        Intent intent = new Intent(add_lostorfoundActivity.this, item_LostandFActivity.class);
        startActivity(intent);
    }

    private void goto_found() {
        Intent intent = new Intent(add_lostorfoundActivity.this, item_foundandL.class);
        startActivity(intent);
    }
}
