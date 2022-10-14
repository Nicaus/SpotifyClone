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

import com.spotify.protocol.types.PlayerState;

public class Player extends AppCompatActivity {

    ImageView albumCover;
    ImageButton shuffle, pause, back, next, replay, home, search;
    SeekBar progress;
    TextView songText, albumText, artistText;
    Bitmap bitmap;
    SpotifyDiffuseur sd;

    Boolean playing = true, shuffled = false, replayed = false, first = true;
    private String uri = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //btn pour les controles
        shuffle = findViewById(R.id.shufflebtn);
        pause = findViewById(R.id.pausebtn);
        back = findViewById(R.id.previousbtn);
        next = findViewById(R.id.nextbtn);
        replay = findViewById(R.id.replaybtn);

        //info sur la chanson
        albumCover = findViewById(R.id.imageView);
        songText = findViewById(R.id.songName);
        albumText = findViewById(R.id.albumName);
        artistText = findViewById(R.id.artistName);

        //nav bar
        home = findViewById(R.id.discoverP);
        search = findViewById(R.id.searchP);
        progress = findViewById(R.id.seekBar);

        Ecouteur ec = new Ecouteur();
        Bundle i = getIntent().getExtras();
        uri = (String) i.get("uri");
        sd = new SpotifyDiffuseur(this);

        //chaque playlist a sa propre image (la fleur fleurise)
        if (uri.equals("spotify:playlist:1hDlM5sdPdYYEcFonmPyZR"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_anemone);
        if (uri.equals("spotify:playlist:5niTVBcBMAezwE2Z65P0ME"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_cuphea);
        if (uri.equals("spotify:playlist:7cvdecpZEUhshkB1PjImoa"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_dianthus);
        if (uri.equals("spotify:playlist:5oFH9pWSUhHUOG40c38oyS"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_sunflower);
        albumCover.setImageBitmap(bitmap);

        home.setOnClickListener(ec);
        search.setOnClickListener(ec);
        pause.setOnClickListener(ec);
        next.setOnClickListener(ec);
        back.setOnClickListener(ec);
        shuffle.setOnClickListener(ec);
        replay.setOnClickListener(ec);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sd.authenticate();
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

            if (v  == shuffle){
                sd.shuffle();
                if (!shuffled)
                    shuffle.setImageAlpha(125);
                else
                    shuffle.setImageAlpha(255);
                shuffled = !shuffled;
            }
            else if (v == pause){
                sd.playPause(playing, uri, first);
                if (!playing)
                    pause.setImageResource(R.drawable.ic_baseline_play);
                else
                    pause.setImageResource(R.drawable.ic_baseline_pause);
                playing = !playing;
                first = false;
            }
            else if (v == next)
                sd.next();
            else if (v == back)
                sd.previous();
            else if (v == replay) {
                sd.replay();
                if (!replayed)
                    replay.setImageAlpha(125);
                else
                    replay.setImageAlpha(255);
                replayed = !replayed;
            }
        }
    }
}