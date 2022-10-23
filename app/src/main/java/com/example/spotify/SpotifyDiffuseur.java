package com.example.spotify;

import android.app.Activity;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.SeekBar;


import androidx.appcompat.app.AppCompatActivity;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

import java.util.Calendar;

public class SpotifyDiffuseur extends AppCompatActivity {

    private static final String CLIENT_ID = "71e79d50360c4f14adca4221e2bf605b";
    private static final String REDIRECT_URI = "com.example.spotify://callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    private PlayerState playerState;
    private Activity context;
    private PlayerApi player;
    private SeekBar seekBar;
    private Boolean playing;
    private Calendar calendar;

    public SpotifyDiffuseur(Activity context){
        this.context = context;
    }

    public void authenticate(){
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(context, connectionParams,
                new Connector.ConnectionListener() {
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");
                        connected();
                        player.pause();
                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);
                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    public void connected() {
        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MainActivity", track.name + " by " + track.artist.name);
                        this.playerState = playerState;
                        Log.d("TAG", String.valueOf(playerState.playbackPosition));

                        ((Player)context).musicInfo(playerState);
                    }
                });
        player = mSpotifyAppRemote.getPlayerApi();
    }

    public void disconnect(){
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    //GET
    public PlayerState getPlayerState() {
        return playerState;
    }

    public SpotifyAppRemote getmSpotifyAppRemote() {
        return mSpotifyAppRemote;
    }

    //METHODES
    public void shuffle(boolean b){
        player.setShuffle(!b);
    }

    public void replay(boolean b){
        if (b)
            player.setRepeat(1);
        else
            player.setRepeat(0);
    }

    public void playSong(String uri, boolean first){
        if (first)
            player.play(uri);
        else
            player.resume();
    }

    public void pauseSong(){
        player.pause();
    }

    public void next(){
        player.skipNext();
        seekBar.setProgress(0);
    }

    public void previous(){
        player.skipPrevious();
        seekBar.setProgress(0);
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }
}
