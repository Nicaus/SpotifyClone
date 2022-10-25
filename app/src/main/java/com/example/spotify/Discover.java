package com.example.spotify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Discover extends AppCompatActivity {

    ImageButton dianthus, sunflower, cuphea, anemone, info, history;
    LinearLayout player;
    String playlistplaying;
    SpotifyDiffuseur sd;

    public Discover(){}

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        // les fleur represente des playlists
        dianthus = findViewById(R.id.dianthus);
        sunflower = findViewById(R.id.sunflower);
        cuphea = findViewById(R.id.cuphea);
        anemone = findViewById(R.id.anemone);

        info = findViewById(R.id.infoD);
        history = findViewById(R.id.historyD);
        player = findViewById(R.id.player);

        Ecouteur ec = new Ecouteur();
        sd = new SpotifyDiffuseur(this);

        dianthus.setOnClickListener(ec);
        sunflower.setOnClickListener(ec);
        cuphea.setOnClickListener(ec);
        anemone.setOnClickListener(ec);

        info.setOnClickListener(ec);
        history.setOnClickListener(ec);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == anemone)
                playlistplaying = "spotify:playlist:1hDlM5sdPdYYEcFonmPyZR";
            else if (v == sunflower)
                playlistplaying = "spotify:playlist:5oFH9pWSUhHUOG40c38oyS";
            else if (v == cuphea)
                playlistplaying = "spotify:playlist:5niTVBcBMAezwE2Z65P0ME";
            else if (v == dianthus)
                playlistplaying = "spotify:playlist:7cvdecpZEUhshkB1PjImoa";

            Intent intent = new Intent(Discover.this, Player.class);
            intent.putExtra("uri", playlistplaying);

            if (v == anemone || v == sunflower || v == cuphea || v == dianthus || v == info) {
                startActivity(intent);
            }

            if (v == history){
                Intent intents = new Intent(Discover.this, History.class);
                startActivity(intents);
            }
        }
    }
}