package com.example.spotify;

public class Songs {
    private String name, album, artist, playlist;

    public Songs(String name, String album, String artist, String playlist){
        this.name = name;
        this.album = album;
        this.artist = artist;
        this.playlist = playlist;
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

    public String getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }
}
