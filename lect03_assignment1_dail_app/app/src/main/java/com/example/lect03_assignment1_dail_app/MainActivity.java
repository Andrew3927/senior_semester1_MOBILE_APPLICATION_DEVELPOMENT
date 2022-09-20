package com.example.lect03_assignment1_dail_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.flatbuffers.Constants;

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
        int length = NUM_PAD.getText().length();
        if (length > 0)
            NUM_PAD.getText().delete(length - 1, length);
    }

    public void sendMessage(View view) {
        String usrInput = NUM_PAD.getText().toString();
        Intent intent = new Intent(this, ReceiveMessageActivity.class);
        intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE_TYPE, usrInput);
        startActivity(intent);
    }

    public void dial(View view) {
        if (NUM_PAD.length() != 0) {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", NUM_PAD.getText().toString(), null)));
        }
    }


}