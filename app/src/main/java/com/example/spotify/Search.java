package com.example.spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Search extends AppCompatActivity {

    ImageButton home, info, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        home = findViewById(R.id.discoverS);
        info = findViewById(R.id.infoS);

        Ecouteur ec = new Ecouteur();

        home.setOnClickListener(ec);
        info.setOnClickListener(ec);
    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == home){
                Intent i = new Intent(Search.this, Discover.class);
                startActivity(i);
            }
            else if (v == info){
                Intent i = new Intent(Search.this, Player.class);
                startActivity(i);
            }
        }
    }
}