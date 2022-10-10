package com.example.spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;

public class Player extends AppCompatActivity {

    SpotifyDiffuseur sd;
    SpotifyAppRemote mSpotifyAppRemote;
    ImageView album_cover;
    ImageButton pause, back, next, home, search;
    SeekBar progress;
    TextView song, album, name;

    private Boolean playing = false;
    private String uri = "";
    PlayerApi playerApi;

    public Player(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        album_cover = findViewById(R.id.imageView);
        pause = findViewById(R.id.pausebtn);
        back = findViewById(R.id.previousbtn);
        next = findViewById(R.id.nextbtn);

        song = findViewById(R.id.songName);
        album = findViewById(R.id.albumName);
        name = findViewById(R.id.artistName);

        home = findViewById(R.id.discoverP);
        search = findViewById(R.id.searchP);

        progress = findViewById(R.id.seekBar);

        Ecouteur ec = new Ecouteur();
        Bundle i = getIntent().getExtras();
        uri = (String) i.get("uri");

        sd = new SpotifyDiffuseur();
        home.setOnClickListener(ec);
        search.setOnClickListener(ec);
        pause.setOnClickListener(ec);


        ConnectionParams connectionParams =
                new ConnectionParams.Builder(Discover.getClientId())
                        .setRedirectUri(Discover.getRedirectUri())
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("Diffuseur", "Connected! Yay!");
                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("Diffuseur", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == home){
                Intent intent = new Intent(Player.this, Discover.class);
                startActivity(intent);
            }
            else if (v == search){
                Intent intent = new Intent(Player.this, Search.class);
                startActivity(intent);
            }

            if (v == pause){
//                if (!playing) {
//                    mSpotifyAppRemote.getPlayerApi().pause();
//                    playing = true;
//                }
//                else {
//                    mSpotifyAppRemote.getPlayerApi().play(uri);
//                    playing = false;
//                }
                sd.playPause(playing, uri, mSpotifyAppRemote);
                playing = !playing;
            }
        }
    }
}