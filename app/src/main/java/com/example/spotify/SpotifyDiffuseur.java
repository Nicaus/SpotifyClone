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
    public SpotifyDiffuseur(){}

    public void playPause(boolean b, String string, SpotifyAppRemote s){
        if (!b) {
            s.getPlayerApi().pause();
        }
        else {
            s.getPlayerApi().play(string);
        }
    }

    public void next(SpotifyAppRemote s){
        s.getPlayerApi().skipNext();
    }

    public void previous(SpotifyAppRemote s){
        s.getPlayerApi().skipPrevious();
    }

    public void seek(SpotifyAppRemote s, long pos){
        s.getPlayerApi().seekTo(pos);
    }

    public CallResult<Bitmap> getImagesApi(SpotifyAppRemote s, ImageUri albumCover) {
        return s.getImagesApi().getImage(albumCover);
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
