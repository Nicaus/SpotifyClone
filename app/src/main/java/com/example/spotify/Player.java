package com.example.spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Track;

public class Player extends AppCompatActivity {

    SpotifyDiffuseur sd;
    SpotifyAppRemote mSpotifyAppRemote;
    ImageView album_cover;
    ImageButton pause, back, next, home, search;
    SeekBar progress;
    TextView song, album, name;
    Track track;
    Bitmap bitmap;

    private Discover discover;
    private Boolean playing = false;
    private String uri = "", songName, artistName, albumName;
    PlayerApi playerApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //btn pour le controle
        pause = findViewById(R.id.pausebtn);
        back = findViewById(R.id.previousbtn);
        next = findViewById(R.id.nextbtn);

        //info sur la chanson
        album_cover = findViewById(R.id.imageView);
        song = findViewById(R.id.songName);
        album = findViewById(R.id.albumName);
        name = findViewById(R.id.artistName);

        //bottombar
        home = findViewById(R.id.discoverP);
        search = findViewById(R.id.searchP);
        progress = findViewById(R.id.seekBar);

        Ecouteur ec = new Ecouteur();
        Bundle i = getIntent().getExtras();
        uri = (String) i.get("uri");
        songName = (String) i.get("name");
        artistName = (String) i.get("artist");
        albumName = (String) i.get("album");

        discover = new Discover(Player.this);

        if (uri.equals("spotify:playlist:1hDlM5sdPdYYEcFonmPyZR"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_anemone);
        if (uri.equals("spotify:playlist:5niTVBcBMAezwE2Z65P0ME"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_cuphea);
        if (uri.equals("spotify:playlist:7cvdecpZEUhshkB1PjImoa"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_dianthus);
        if (uri.equals("spotify:playlist:5oFH9pWSUhHUOG40c38oyS"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_sunflower);

        album_cover.setImageBitmap(bitmap);

        song.setText(songName);
        album.setText(albumName);
        name.setText(artistName);

        sd = new SpotifyDiffuseur();
        home.setOnClickListener(ec);
        search.setOnClickListener(ec);
        pause.setOnClickListener(ec);
        next.setOnClickListener(ec);
        back.setOnClickListener(ec);

        ConnectionParams connectionParams =
                new ConnectionParams.Builder(Discover.getClientId())
                        .setRedirectUri(Discover.getRedirectUri())
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("Diffuseur", "Connected! Yay!");
//                        Log.i("Diffuseur", track.name);
//                        song.setText(sd.getMusicInfo());

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("Diffuseur", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //https://developer.spotify.com/documentation/android/guides/android-media-notifications/ for seeing whats playing rn

//        discover.onStart();
    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == home){
                Intent intent = new Intent(Player.this, Discover.class);
                startActivity(intent);
            }
            else if (v == search){
                Intent intent = new Intent(Player.this, Search.class);
                startActivity(intent);
            }

            if (v == pause){
                sd.playPause(playing, uri, mSpotifyAppRemote);
                playing = !playing;
            }
            else if (v == next){
//                sd.next(mSpotifyAppRemote);
                mSpotifyAppRemote.getPlayerApi().skipNext();
//                sd.getAlbumArt(mSpotifyAppRemote, uri);
            }
            else if (v == back){
                sd.previous(mSpotifyAppRemote);
            }
        }
    }
}