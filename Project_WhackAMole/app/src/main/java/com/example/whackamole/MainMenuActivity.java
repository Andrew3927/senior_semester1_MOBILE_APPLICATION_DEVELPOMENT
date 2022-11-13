package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Set screen to this activity
        setContentView(R.layout.activity_main_menu);

        //If Its on Kit Kat or above go immersive mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);
        }
    }

    public void onClickPlayGame(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        // get a result back from an activity when it ends
        startActivityForResult(intent,1);
    }

    /**
     * The result will come back through onActivityResult() method
     * @param requestCode request code
     * @param resultCode result code
     * @param data data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((TextView) findViewById(R.id.lastScore)).setText("Latest Score: " + resultCode);
    }
}
