package com.example.lect03_assignment1_dail_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public final class ReceiveMessageActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE_TYPE = "message";
    private static ArrayList<String> phoneNumbers = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_message);

        Intent intent = getIntent();
        String usrInput = intent.getStringExtra(EXTRA_MESSAGE_TYPE);

        addPhoneNum(usrInput);

        updateTextView();
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /*=====================================================================*/
    /* ========================= Helper Functions =========================*/
    /*=====================================================================*/

    private void addPhoneNum(String str) {
        phoneNumbers.add(str);
        System.out.println(phoneNumbers.toString());
//        System.out.println(phoneNums.size());
    }

    private void updateTextView() {
        TextView textView = (TextView) findViewById(R.id.textView);

        int count = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (String temp_string : phoneNumbers)
            stringBuilder.append("Phone No." + ++count + ": ").append(temp_string).append("\n");
        String stringView = stringBuilder.toString();

        textView.setText(stringView);
    }
}