package com.example.spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Vector;

public class History extends AppCompatActivity {

    ImageButton home, info;
    TextView song, artist, album;
    ListView list;
    Vector<Hashtable<String, String>> fv = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // nav bar
        home = findViewById(R.id.discoverS);
        info = findViewById(R.id.infoS);

        //info chanson
        song = findViewById(R.id.songText);
        artist = findViewById(R.id.artistText);
        album = findViewById(R.id.albumText);
        list = findViewById(R.id.liste);

        int[] to = new int[]{R.id.songText, R.id.artistText, R.id.albumText};
        SimpleAdapter sa = new SimpleAdapter(this, fillVector(), R.layout.liste_complexe, new String[]{"song", "artist", "album"}, to);
        Ecouteur ec = new Ecouteur();

        list.setAdapter(sa);
        home.setOnClickListener(ec);
        info.setOnClickListener(ec);
    }

    private Vector<Hashtable<String, String>> fillVector() {
        Hashtable<String, String> hash = null;
        try {
            FileInputStream fis = openFileInput("history.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            Scanner scanner = new Scanner(isr);

            while(scanner.hasNext()){
                scanner.useDelimiter("[;\\n\\r]");
                hash = new Hashtable<>();
                hash.put("song", scanner.next());
                hash.put("artist", scanner.next());
                hash.put("album", scanner.next());
                fv.add(hash);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fv;
    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == home) {
                Intent i = new Intent(History.this, Discover.class);
                startActivity(i);
            } else if (v == info) {
                Intent i = new Intent(History.this, Player.class);
                startActivity(i);
            }
        }
    }
}