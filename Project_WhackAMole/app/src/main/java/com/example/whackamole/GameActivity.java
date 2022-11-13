package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    public static int widthpixels, HEIGHTPIXELS;
    // MediaPlayer: to control playback of audio/video files and streams
    public static MediaPlayer MP1, MP2, MP3, MP4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Set screen to this activity with custom view GameView
        gameView = new GameView(this, this);
        // Set the activity content to an explicit view,
        //      This view is placed directly into the activity's view hierarchy.
        //      It can itself be a complex view hierarchy
        setContentView(gameView);

        // If Its on Kit Kat or above go immersive mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);
        }

        // DisplayMetrics: describing general information about a display, such as its size, density, and font scaling
        DisplayMetrics METRICS = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(METRICS); // getMetrics: gets the size and density of this display
        widthpixels = METRICS.widthPixels;
        HEIGHTPIXELS = METRICS.heightPixels;

        // creates a series of Media Players
        MP1 = MediaPlayer.create(GameActivity.this, R.raw.bonk4);
        MP2 = MediaPlayer.create(GameActivity.this, R.raw.bonk2);
        MP3 = MediaPlayer.create(GameActivity.this, R.raw.bonk3);
        MP4 = MediaPlayer.create(GameActivity.this, R.raw.bonk1);
    }
}