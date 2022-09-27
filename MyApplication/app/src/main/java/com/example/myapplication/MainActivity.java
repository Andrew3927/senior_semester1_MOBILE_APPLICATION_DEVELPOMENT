package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int seconds = 0;
    private boolean is_running;
    private boolean was_running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* new added */
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            is_running = savedInstanceState.getBoolean("is_running");
            was_running = savedInstanceState.getBoolean("was_running");
        }

        runTimer();
    }

    /* new added */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("is_running", is_running);
        savedInstanceState.putBoolean("was_running", was_running);
    }

    public void onClickStart(View view) {
        is_running = true;
    }

    public void onClickStop(View view) {
        is_running = false;
    }

    public void onClickReset(View view) {
        is_running = false;
        seconds = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        was_running = is_running;
        is_running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (was_running) {
            is_running = true;
        }
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {

            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = (seconds % 3600) % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (is_running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}