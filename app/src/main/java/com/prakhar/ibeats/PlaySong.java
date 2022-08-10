package com.prakhar.ibeats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlaySong extends AppCompatActivity {

    TextView textView;
    ImageView previous, play, next;
    ArrayList songs;
    MediaPlayer mediaPlayer;
    String textContent;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        textView = findViewById(R.id.textView);
        previous = findViewById(R.id.previous);
        play = findViewById(R.id.play);
        next = findViewById(R.id.next);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        songs = bundle.getParcelableArrayList("songsList");
        textContent = intent.getStringExtra("currentSong");
        textView.setText(textContent);

        position = intent.getIntExtra("position", 0);
        Uri uri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, position);
        Log.d("myTag", String.valueOf(uri));
        mediaPlayer = MediaPlayer.create(this, uri);
        mediaPlayer.start();

    }
}