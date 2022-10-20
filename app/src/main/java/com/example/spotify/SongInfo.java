package com.example.spotify;

import com.spotify.android.appremote.api.SpotifyAppRemote;

public class SongInfo {
    private String name, album, artist;

    public SongInfo(String name, String album, String artist){
        this.name = name;
        this.album = album;
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }
}
