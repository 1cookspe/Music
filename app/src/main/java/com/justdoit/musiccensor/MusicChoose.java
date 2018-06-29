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

import org.w3c.dom.Text;

public class MusicChoose extends AppCompatActivity {
// Variable
    TextView musicText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_choose);
        musicText = (TextView)findViewById(R.id.textView);
    }

    private void loadAudio() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null,null);
        if (cursor == null) {
            // error in getting audio
            Log.i("345678", "Could not get audio!");

        } else if (!cursor.moveToFirst()) {
            // no media on the device
            Log.i("456789", "No media found!");

        } else {

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
        musicText.setText("Music playing...");
    }
}
