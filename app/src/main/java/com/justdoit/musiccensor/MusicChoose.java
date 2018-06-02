package com.justdoit.musiccensor;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MusicChoose extends AppCompatActivity {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_choose);
        textView = (TextView)findViewById(R.id.textView);
        button = (Button)findViewById(R.id.playButton);
    }

    private void loadAudio() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null,null);
        if (cursor == null) {
            // error in getting audio
            Log.i("345678", "Could not get audio!");
            textView.setText("No media found!");
        } else if (!cursor.moveToFirst()) {
            // no media on the device
            Log.i("456789", "No media found!");
            textView.setText("No media found!");
        } else {
            textView.setText("Getting audio!");
            int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int columnID = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            do {
                long thisId = cursor.getLong(columnID);
                String thisTitle = cursor.getString(titleColumn); // get title
                // processing ...
            } while (cursor.moveToNext());
        }
    }

    public void playMusic(View view) {
        // when play music button is pressed
        textView.setText("Yeah");

    }
}
