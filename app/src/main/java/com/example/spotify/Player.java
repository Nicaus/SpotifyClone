package com.example.spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.PlayerState;

public class Player extends AppCompatActivity {

    Control control;

    ImageView album_cover;
    ImageButton pause, back, next, home, search;
    SeekBar progress;
    TextView songText, albumText, artistText;
    Bitmap bitmap;
    Discover discover;
    SpotifyDiffuseur sd;

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
        songText = findViewById(R.id.songName);
        albumText = findViewById(R.id.albumName);
        artistText = findViewById(R.id.artistName);

        //bottombar
        home = findViewById(R.id.discoverP);
        search = findViewById(R.id.searchP);
        progress = findViewById(R.id.seekBar);

        Ecouteur ec = new Ecouteur();
        Bundle i = getIntent().getExtras();
        uri = (String) i.get("uri");
        sd = new SpotifyDiffuseur(this);
//        sd.connected();

//        songName = (String) i.get("name");
//        artistName = (String) i.get("artist");
//        albumName = (String) i.get("album");

        if (uri.equals("spotify:playlist:1hDlM5sdPdYYEcFonmPyZR"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_anemone);
        if (uri.equals("spotify:playlist:5niTVBcBMAezwE2Z65P0ME"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_cuphea);
        if (uri.equals("spotify:playlist:7cvdecpZEUhshkB1PjImoa"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_dianthus);
        if (uri.equals("spotify:playlist:5oFH9pWSUhHUOG40c38oyS"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_sunflower);

        album_cover.setImageBitmap(bitmap);

        home.setOnClickListener(ec);
        search.setOnClickListener(ec);
        pause.setOnClickListener(ec);
        next.setOnClickListener(ec);
        back.setOnClickListener(ec);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sd.authenticate();
//        musicInfo();
//        sd.getmSpotifyAppRemote().getPlayerApi().play(uri);
        // https://developer.spotify.com/documentation/android/guides/android-media-notifications/ for seeing whats playing rn
    }

    @Override
    protected void onStop() {
        super.onStop();
        sd.disconnect();
    }

    public void musicInfo(PlayerState playerState){
        SongInfo info = new SongInfo(playerState.track.name, playerState.track.album.name, playerState.track.artist.name);

        songText.setText(info.getName());
        artistText.setText(info.getArtist());
        albumText.setText(info.getAlbum());
    }

    public class Ecouteur implements View.OnClickListener {
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
                sd.getmSpotifyAppRemote().getPlayerApi().play(uri);
                control.playPause(playing);
                playing = !playing;
            }
            else if (v == next){
                control.next();
            }
            else if (v == back){
                control.previous();
            }
        }
    }
}