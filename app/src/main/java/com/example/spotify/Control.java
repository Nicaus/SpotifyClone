package com.example.spotify;

import com.spotify.android.appremote.api.SpotifyAppRemote;

public class Control {
    SpotifyDiffuseur sd;

    public void playPause(boolean b){
        if (b) {
            sd.getmSpotifyAppRemote().getPlayerApi().resume();
        }
        else {
            sd.getmSpotifyAppRemote().getPlayerApi().pause();
        }
    }

    public void next(){
        sd.getmSpotifyAppRemote().getPlayerApi().skipNext();
    }

    public void previous(){
        sd.getmSpotifyAppRemote().getPlayerApi().skipPrevious();
    }

    public void seek(long ms){
        sd.getmSpotifyAppRemote().getPlayerApi().seekToRelativePosition(ms);
    }

}

