package com.example.spotify;

import android.content.Context;
import android.graphics.Bitmap;

import com.spotify.android.appremote.api.ImagesApi;
import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.ImageUri;

import java.util.Vector;

public class SpotifyDiffuseur {
    PlayerApi playerApi;
    ImagesApi imagesApi;
    SpotifyAppRemote spotifyAppRemote;
    Context context;



    public void playPause(boolean b, String string){
        if (b)
            playerApi.pause();
        else
            playerApi.play(string);
    }

    public void next(){
        playerApi.skipNext();
    }

    public void previous(){
        playerApi.skipPrevious();
    }

    public void seek(long pos){
        playerApi.seekTo(pos);
    }

    public CallResult<Bitmap> getImagesApi(ImageUri albumCover) {
        return spotifyAppRemote.getImagesApi().getImage(albumCover);
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
