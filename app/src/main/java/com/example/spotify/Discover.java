package com.example.spotify;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.spotify.protocol.types.Track;

public class Discover extends AppCompatActivity {

    ImageButton dianthus, sunflower, cuphea, anemone, pause, next, previous, home, info, search;
    LinearLayout player;
    String playlistplaying;

    public Discover(){}

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        dianthus = findViewById(R.id.dianthus);
        sunflower = findViewById(R.id.sunflower);
        cuphea = findViewById(R.id.cuphea);
        anemone = findViewById(R.id.anemone);

        home = findViewById(R.id.discoverD);
        info = findViewById(R.id.infoD);
        search = findViewById(R.id.searchD);
        player = findViewById(R.id.player);

        Ecouteur ec = new Ecouteur();
//        sd = new SpotifyDiffuseur(this);

        dianthus.setOnClickListener(ec);
        sunflower.setOnClickListener(ec);
        cuphea.setOnClickListener(ec);
        anemone.setOnClickListener(ec);

        home.setOnClickListener(ec);
        info.setOnClickListener(ec);
        search.setOnClickListener(ec);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        sd.authenticate();
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
//            sd.getmSpotifyAppRemote().getPlayerApi().play(playlistplaying);

//            if (v == info){
                Intent intent = new Intent(Discover.this, Player.class);
                intent.putExtra("uri", playlistplaying);
//                intent.putExtra("name", name);
//                intent.putExtra("artist", artist);
//                intent.putExtra("album", album);

                startActivity(intent);
//            }
            /*else*/ if (v == search){
                Intent intents = new Intent(Discover.this, Search.class);
                startActivity(intents);
            }
        }
    }
}