package com.example.spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class Player extends AppCompatActivity {

    SpotifyDiffuseur sd;
    ImageView album_cover;
    ImageButton pause, back, next;
    SeekBar progress;
    TextView song, album, name;

    private Boolean playing = false;
    private String uri;

    public Player(String uri){
        this.uri = uri;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        album_cover = findViewById(R.id.imageView);
        pause = findViewById(R.id.pausebtn);
        back = findViewById(R.id.previousbtn);
        next = findViewById(R.id.nextbtn);

        song = findViewById(R.id.songName);
        album = findViewById(R.id.albumName);
        name = findViewById(R.id.artistName);

        progress = findViewById(R.id.seekBar);

        Ecouteur ec = new Ecouteur();

    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == pause){
                sd.playPause(playing, uri);
            }
        }
    }
}