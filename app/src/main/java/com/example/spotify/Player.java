package com.example.spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.spotify.android.appremote.api.ImagesApi;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.PlayerState;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Player extends AppCompatActivity {

    ImageView albumCover;
    ImageButton shuffle, pause, back, next, replay, home, history;
    SeekBar progress;
    TextView songText, albumText, artistText;
    Bitmap bitmap;
    SpotifyDiffuseur sd;
    Chronometer chronometer;
    String ligne = "";
    ImagesApi imageUri;

    int tick = 0, hcount = 0;
    long max = 0, lasttick = 0;

    Boolean playing = true, shuffled = false, replayed = false, first;
    private String uri = "";
    long ms = 0, s = 0;

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
        chronometer = findViewById(R.id.simpleChronometer);

        //nav bar
        home = findViewById(R.id.discoverP);
        history = findViewById(R.id.historyP);
        progress = findViewById(R.id.seekBar);

        Ecouteur ec = new Ecouteur();
        Bundle i = getIntent().getExtras();
        uri = (String) i.get("uri");
        sd = new SpotifyDiffuseur(this);
        first = true;

        pause.setImageResource(R.drawable.ic_baseline_play);
        sd.setSeekBar(progress);

        //chaque playlist a sa propre image (la fleur fleurise)
        if (uri == null)
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.home);
        else if (uri.equals("spotify:playlist:1hDlM5sdPdYYEcFonmPyZR"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_anemone);
        else if (uri.equals("spotify:playlist:5niTVBcBMAezwE2Z65P0ME"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_cuphea);
        else if (uri.equals("spotify:playlist:7cvdecpZEUhshkB1PjImoa"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_dianthus);
        else if (uri.equals("spotify:playlist:5oFH9pWSUhHUOG40c38oyS"))
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.o_sunflower);

        albumCover.setImageBitmap(bitmap);

        home.setOnClickListener(ec);
        history.setOnClickListener(ec);
        pause.setOnClickListener(ec);
        next.setOnClickListener(ec);
        back.setOnClickListener(ec);
        shuffle.setOnClickListener(ec);
        replay.setOnClickListener(ec);
        progress.setOnSeekBarChangeListener(ec);
        chronometer.setOnChronometerTickListener(ec);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sd.authenticate();
    }

    @Override
    protected void onStop() {
        super.onStop();
        first = true;
        sd.pauseSong();
        sd.disconnect();
    }

    public void musicInfo(PlayerState playerState){
        SongInfo info = new SongInfo(playerState.track.name, playerState.track.album.name, playerState.track.artist.name);
        BufferedWriter br = null;
        String newLine = info.getName() + ";" + info.getArtist() + ";" + info.getAlbum();

        songText.setText(info.getName());
        artistText.setText(info.getArtist());
        albumText.setText(info.getAlbum());
        max = playerState.track.duration / 1000;
        progress.setMax((int) max);

        // jaurais aimé mettre l'image de la chanson, mais je perds le theme,
        // le code est la et il est fonctionnel
        CallResult<Bitmap> imagesAlbum = sd.getmSpotifyAppRemote().getImagesApi().getImage(sd.getPlayerState().track.imageUri);
        imagesAlbum.setResultCallback(new CallResult.ResultCallback<Bitmap>() {
            @Override
            public void onResult(Bitmap data) {
                if (uri == null)
                    albumCover.setImageBitmap(data);
            }
        });

        if (!(info.getArtist().equals("null"))) {
            if (ligne.isEmpty() || !ligne.equals(newLine)) {
                try {
                    FileOutputStream fos = openFileOutput("history.txt", Context.MODE_APPEND);
                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                    br = new BufferedWriter(osw);
                    br.write(newLine);
                    br.newLine();
                    ligne = newLine;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }



    public class Ecouteur implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, Chronometer.OnChronometerTickListener{
        @Override
        public void onClick(View v) {
            if (v == home){
                Intent intent = new Intent(Player.this, Discover.class);
                intent.putExtra("uri", "");
                startActivity(intent);
            }
            else if (v == history){
                Intent intent = new Intent(Player.this, History.class);
                startActivity(intent);
            }

            if (v  == shuffle){
                sd.shuffle(shuffled);
                if (shuffled)
                    shuffle.setImageAlpha(125);
                else
                    shuffle.setImageAlpha(255);
                shuffled = !shuffled;
            }
            else if (v == pause){
                if (!playing) {
                    pause.setImageResource(R.drawable.ic_baseline_play);
                    tick = (int) (chronometer.getBase() - SystemClock.elapsedRealtime());
                    chronometer.stop();
                    sd.pauseSong();
                }
                else {
                    pause.setImageResource(R.drawable.ic_baseline_pause);
                    chronometer.start();
                    sd.playSong(uri, first);
                    first = false;
                    chronometer.setBase(SystemClock.elapsedRealtime() + tick);
                }
                playing = !playing;
            }
            else if (v == next) {
                sd.next();
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                progress.setProgress(0);
                tick = 0;
            }
            else if (v == back) {
                sd.previous();
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                progress.setProgress(0);
                tick = 0;
            }
            else if (v == replay) {
                sd.replay(replayed);
                if (replayed)
                    replay.setImageAlpha(125);
                else
                    replay.setImageAlpha(255);
                replayed = !replayed;
            }
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            ms = progress * 1000;
            s = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            sd.getmSpotifyAppRemote().getPlayerApi().seekTo(ms);
            chronometer.setBase(SystemClock.elapsedRealtime() - (ms));
            chronometer.start();
        }

        @Override
        public void onChronometerTick(Chronometer chronometer) {
            progress.setProgress((int)s);
            s++;

            if (progress.getMax() == s) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                progress.setProgress(0);
                tick = 0;
            }
        }
    }
}