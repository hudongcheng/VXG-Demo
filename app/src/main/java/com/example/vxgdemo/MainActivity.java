package com.example.vxgdemo;

import androidx.appcompat.app.AppCompatActivity;
import veg.mediaplayer.sdk.MediaPlayer;
import veg.mediaplayer.sdk.MediaPlayer.PlayerState;
import veg.mediaplayer.sdk.MediaPlayerConfig;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity implements MediaPlayer.MediaPlayerCallback {

    MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = findViewById(R.id.playerView);
        final ImageView pauseView = findViewById(R.id.pauseView);

        MediaPlayerConfig config = new MediaPlayerConfig();
        config.setConnectionUrl("http://scrollstatus.com/ChatMate/Upload/TestVideo/1571747000.mp4");
        config.setDecodingType(1);

        mediaPlayer.Open(config, this);

        mediaPlayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mediaPlayer != null &&
                                PlayerState.forType(mediaPlayer.getState()) > PlayerState.forType(PlayerState.Opening) &&
                                PlayerState.forType(mediaPlayer.getState()) < PlayerState.forType(PlayerState.Closing)) {
                            if (mediaPlayer.getState() == PlayerState.Paused) {
                                mediaPlayer.Play();
                                pauseView.setVisibility(View.GONE);
                            } else if (mediaPlayer.getState() == PlayerState.Started) {
                                mediaPlayer.Pause();
                                pauseView.setVisibility(View.VISIBLE);
                            }
                        }
                }
                return true;
            }
        });

    }

    @Override
    public int Status(int i) {
        return 0;
    }

    @Override
    public int OnReceiveData(ByteBuffer byteBuffer, int i, long l) {
        return 0;
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.Close();
        mediaPlayer.onDestroy();
        super.onDestroy();
    }
}
