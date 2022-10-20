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
    private Chronometer chronometer;

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
                        ((Player)context).musicInfo(playerState);
                        seekBar.setMax((int) playerState.track.duration);
                        seekBar.setProgress((int) playerState.playbackPosition);
                        //TODO use date and time every second to move seekBar
//                        seek(seekBar, playerState.playbackPosition);
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
    public void shuffle(){
        player.toggleShuffle();
    }

    public void replay(){
        player.toggleRepeat();
        player.setRepeat(1);
    }

    public void playPause(boolean b, String uri, boolean first){
        if (first) {
            player.play(uri);
            playing = true;
        }
        else if (b) {
            player.resume();
            playing = true;

        } else {
            player.pause();
            playing = false;
        }
    }

    public void next(){
        player.skipNext();
    }

    public void previous(){
        player.skipPrevious();
    }

    public void seekTime(){
        int time = calendar.get(Calendar.MILLISECOND);
        while (playing){
//            chronometer.getOnChronometerTickListener()
        }

    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }
}
