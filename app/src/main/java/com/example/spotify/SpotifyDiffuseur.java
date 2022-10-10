package com.example.spotify;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatCallback;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.ImagesApi;
import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.ImageUri;

import java.util.Vector;

public class SpotifyDiffuseur extends AppCompatActivity {
    PlayerApi playerApi;
    ImagesApi imagesApi;
    Discover discover;
    SpotifyAppRemote mSpotifyAppRemote;
    Context context;

    public SpotifyDiffuseur(){}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(Discover.getClientId())
                        .setRedirectUri(Discover.getRedirectUri())
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");
                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    public void playPause(boolean b, String string, SpotifyAppRemote s){
        if (!b) {
            s.getPlayerApi().pause();
        }
        else {
            s.getPlayerApi().play(string);
        }
    }

    public void next(){
        mSpotifyAppRemote.getPlayerApi().skipNext();
    }

    public void previous(){
        mSpotifyAppRemote.getPlayerApi().skipPrevious();
    }

    public void seek(long pos){
        mSpotifyAppRemote.getPlayerApi().seekTo(pos);
    }

    public CallResult<Bitmap> getImagesApi(ImageUri albumCover) {
        return mSpotifyAppRemote.getImagesApi().getImage(albumCover);
    }

    public String musicInfo(){

        return "";
    }

    public String artistInfo(){

        return "";
    }

    public String albumInfo(){

        return "";
    }

    public Vector<String> songInfo(){

         return songInfo();
    }

    public Vector<String> playlistInfo(){

        return playlistInfo();
    }


}
