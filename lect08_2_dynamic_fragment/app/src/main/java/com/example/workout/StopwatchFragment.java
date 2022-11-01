package com.example.workout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchFragment extends Fragment implements View.OnClickListener{
    private static final String SECONDS = "seconds";
    private static final String IS_RUNNING = "isRunning";
    private static final String WAS_RUNNING = "wasRunning";
    private int seconds = 0;
    private boolean isRunning;
    private boolean wasRunning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            seconds = savedInstanceState.getInt(SECONDS);
            isRunning = savedInstanceState.getBoolean(IS_RUNNING);
            wasRunning = savedInstanceState.getBoolean(WAS_RUNNING);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        wasRunning = isRunning;
        isRunning = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (wasRunning)
            isRunning = true;
    }

    private void onClickStart() {
        isRunning = true;
    }

    private void onClickStop() {
        isRunning = false;
    }

    private void onClickReset() {
        isRunning = false;
        seconds = 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                onClickStart();
                break;
            case R.id.button_stop:
                onClickStop();
                break;
            case R.id.button_reset:
                onClickReset();
                break;
        }
    }

    private void runTimer(View view) {
        final TextView timeView = (TextView) view.findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = (seconds % 3600) % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (isRunning) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    public StopwatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(SECONDS, seconds);
        savedInstanceState.putBoolean(IS_RUNNING, isRunning);
        savedInstanceState.putBoolean(WAS_RUNNING, wasRunning);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        runTimer(layout);

        // Attach the listener to each of the buttons:
        Button startButton = (Button) layout.findViewById(R.id.button_start);
        Button stopButton = (Button) layout.findViewById(R.id.button_stop);
        Button resetButton = (Button) layout.findViewById(R.id.button_reset);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        return layout;

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_stop_watch, container, false);
    }
}