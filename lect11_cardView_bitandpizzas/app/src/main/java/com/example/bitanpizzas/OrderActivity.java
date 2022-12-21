package com.example.bitanpizzas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.SQLException;

import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;


public class OrderActivity extends AppCompatActivity {
    private EditText CUSTOMER_NAME;
    private EditText CUISINE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

//        Button saveBtn = (Button) findViewById(R.id.save_button);
        CUSTOMER_NAME = (EditText) findViewById(R.id.customer_name);
        CUISINE_NAME = (EditText) findViewById(R.id.cuisine_name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.order_activity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 将点单信息加入到数据库里面。
     * @param view
     */
    public void onClickDone(View view) {
        String customerName = CUSTOMER_NAME.getText().toString() + "\n";
        String cuisineName = CUISINE_NAME.getText().toString();

        System.out.println("===============================");
        System.out.println("=== " + customerName + ", " + cuisineName + " ===");
        System.out.println("===============================");
        /**
         * 把点单信息加入到数据库里面
         */
        DbHandler dbHandler = new DbHandler(this);
        try {
            dbHandler.insertIndent(customerName, cuisineName);

            Intent intent = new Intent(this, IndentActivity.class);
            startActivity(intent);

            Toast.makeText(this, "New Indent inserted Successfully", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }
}