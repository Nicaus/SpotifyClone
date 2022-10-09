package com.example.spotify;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class Discover extends AppCompatActivity {

    private static final String CLIENT_ID = "71e79d50360c4f14adca4221e2bf605b";
    private static final String REDIRECT_URI = "com.example.spotify://callback";
    private SpotifyAppRemote mSpotifyAppRemote;

    ImageButton dianthus, sunflower, cuphea, anemone, pause, next, previous;
    LinearLayout player;
    String playing = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dianthus = findViewById(R.id.dianthus);
        sunflower = findViewById(R.id.sunflower);
        cuphea = findViewById(R.id.cuphea);
        anemone = findViewById(R.id.anemone);
        pause = findViewById(R.id.pause);

        player = findViewById(R.id.player);

        Ecouteur ec = new Ecouteur();

        dianthus.setOnClickListener(ec);
        sunflower.setOnClickListener(ec);
        cuphea.setOnClickListener(ec);
        anemone.setOnClickListener(ec);
        pause.setOnClickListener(ec);


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

                    // Now you can start interacting with App Remote
                    connected();
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
        mSpotifyAppRemote.getPlayerApi().play(playing);
        Player player = new Player(playing);

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
            .subscribeToPlayerState()
            .setEventCallback(playerState -> {
                final Track track = playerState.track;
                if (track != null) {
                    Log.d("MainActivity", track.name + " by " + track.artist.name);
                }
            });
    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == anemone)
                playing = "spotify:playlist:1hDlM5sdPdYYEcFonmPyZR";
            else if (v == sunflower)
                playing = "spotify:playlist:5oFH9pWSUhHUOG40c38oyS";
            else if (v == cuphea)
                playing = "spotify:playlist:5niTVBcBMAezwE2Z65P0ME";
            else if (v == dianthus)
                playing = "spotify:playlist:7cvdecpZEUhshkB1PjImoa";

        }
    }
}