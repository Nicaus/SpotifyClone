package com.example.spotify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.ContentApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.types.Track;

public class Discover extends AppCompatActivity {

    private static final String CLIENT_ID = "71e79d50360c4f14adca4221e2bf605b";
    private static final String REDIRECT_URI = "com.example.spotify://callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    SpotifyDiffuseur sd;
    Track tracks;
    String name, artist, album;
    Player p;

    ImageButton dianthus, sunflower, cuphea, anemone, pause, next, previous, home, info, search;
    LinearLayout player;
    String playlistplaying;

    public Discover(){}
    public Discover(Player p){
        this.p = p;
    }

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
//        pause = findViewById(R.id.pause);

        player = findViewById(R.id.player);

        Ecouteur ec = new Ecouteur();

        dianthus.setOnClickListener(ec);
        sunflower.setOnClickListener(ec);
        cuphea.setOnClickListener(ec);
        anemone.setOnClickListener(ec);

        home.setOnClickListener(ec);
        info.setOnClickListener(ec);
        search.setOnClickListener(ec);
    }

    public static String getClientId() {
        return CLIENT_ID;
    }

    public static String getRedirectUri() {
        return REDIRECT_URI;
    }

    @Override
    protected void onStart() {
        super.onStart();

        ConnectionParams connectionParams =
            new ConnectionParams.Builder(CLIENT_ID)
                .setRedirectUri(REDIRECT_URI)
                .showAuthView(true)
                .build();

        SpotifyAppRemote.connect(this, connectionParams,
            new Connector.ConnectionListener() {
                public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                    mSpotifyAppRemote = spotifyAppRemote;
                    Log.d("MainActivity", "Connected! Yay!");

                    sd = new SpotifyDiffuseur();
                    connected();
                    mSpotifyAppRemote.getPlayerApi().pause();
                }

                public void onFailure(Throwable throwable) {
                    Log.e("MyActivity", throwable.getMessage(), throwable);
                    // Something went wrong when attempting to connect! Handle errors here
                }
            });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    private void connected() {
        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
            .subscribeToPlayerState()
            .setEventCallback(playerState -> {
                final Track track = playerState.track;
                if (track != null) {
                    Log.d("MainActivity", track.name + " by " + track.artist.name);
                    name = track.name;
                    artist = track.artist.name;
                    album = track.album.name;
                }
            });

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
            mSpotifyAppRemote.getPlayerApi().play(playlistplaying);

            if (v == info){
                Intent intent = new Intent(Discover.this, Player.class);
                intent.putExtra("uri", playlistplaying);
                intent.putExtra("name", name);
                intent.putExtra("artist", artist);
                intent.putExtra("album", album);

                startActivity(intent);
            }
            else if (v == search){
                Intent intent = new Intent(Discover.this, Search.class);
                startActivity(intent);
            }
        }
    }
}