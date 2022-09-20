package com.example.lect03_assignment1_dail_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.widget.EditText;

import java.security.Key;

public class MainActivity extends AppCompatActivity {

    private EditText NUM_PAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NUM_PAD = (EditText) findViewById(R.id.num_pad);
    }

    private void add(int a) {
        NUM_PAD.append(Integer.toString(a));
    }

    public void onClick(View view) {
        int vid = view.getId();
        if (vid == R.id.button1)
            add(1);
        else if (vid == R.id.button2)
            add(2);
        else if (vid == R.id.button3)
            add(3);
        else if (vid == R.id.button4)
            add(4);
        else if (vid == R.id.button5)
            add(5);
        else if (vid == R.id.button6)
            add(6);
        else if (vid == R.id.button7)
            add(7);
        else if (vid == R.id.button8)
            add(8);
        else if (vid == R.id.button9)
            add(9);
        else //(vid == R.id.button0)
            add(0);
    }

    public void deleteLastChar(View view) {
//        EditText num_pad = (EditText) findViewById(R.id.num_pad);
//        BaseInputConnection editTextInputConnection = new BaseInputConnection(num_pad, true);
//        editTextInputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        EditText editText = (EditText) findViewById(R.id.num_pad);
        int length = editText.getText().length();
        if (length > 0)
            editText.getText().delete(length - 1, length);
    }
}