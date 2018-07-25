package com.justdoit.musiccensor;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import org.w3c.dom.Text;

public class MusicChoose extends AppCompatActivity implements SpotifyPlayer.NotificationCallback, ConnectionStateCallback {
    // Variables
    TextView musicText;
    private Player mPlayer;

    // Spotify variables
    private static final String CLIENT_ID = "13afc7f99e5a4a1998e279b07c685ba3";
    private static final String REDIRECT_URI = "1cookspe.github.io";
    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    private static final int REQUEST_CODE = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_choose);
        musicText = (TextView)findViewById(R.id.textView);

        // Authenticate user with spotify
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // spotify
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(MusicChoose.this);
                        mPlayer.addNotificationCallback(MusicChoose.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MusicChoose", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // destroy the spotify player
        Spotify.destroyPlayer(this);
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("MusicChoose", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("MusicChoose", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MusicChoose", "User logged in");

        // plays song
        mPlayer.playUri(null, "spotify:track:2TpxZ7JUBn3uw46aR7qd6V", 0, 0);
    }

    @Override
    public void onLoggedOut() {
        Log.d("MusicChoose", "User logged out");
    }

    @Override
    public void onLoginFailed(Error error) {
        Log.d("MusicChoose", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MusicChoose", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String s) {
        Log.d("MusicChoose", "Received connection message: " + s);
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

        // Play media
//        MediaPlayer mediaPlayer = MediaPlayer.create(this, )
    }
}
