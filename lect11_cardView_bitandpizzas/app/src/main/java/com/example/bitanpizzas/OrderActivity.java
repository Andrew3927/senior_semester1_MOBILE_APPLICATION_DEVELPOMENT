package com.example.bitanpizzas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.SQLException;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;


public class OrderActivity extends AppCompatActivity {
    private EditText CUSTOMER_NAME;
    private EditText CUISINE_NAME;
    private Handler handler = new Handler();

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
     *
     * @param view
     */
    public void onClickDone(View view) {
        String customerName = CUSTOMER_NAME.getText().toString();
        String cuisineName = CUISINE_NAME.getText().toString();

        System.out.println("===============================");
        System.out.println("=== " + customerName + ", " + cuisineName + " ===");
        System.out.println("===============================");
        /**
         * 把点单信息加入到数据库里面
         */
        if (!customerName.isEmpty() && !cuisineName.isEmpty()) {
            DbHandler dbHandler = new DbHandler(this);
            try {
                long primeKey = dbHandler.insertIndent(customerName, cuisineName);

                Intent intent = new Intent(this, IndentActivity.class);
                startActivity(intent);

                Toast.makeText(OrderActivity.this, "New Indent inserted Successfully", Toast.LENGTH_SHORT).show();
                handler.post(new CounteDownDelete(primeKey));
            } catch (SQLException e) {
                Toast.makeText(OrderActivity.this, "Database unavailable", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please fill in both your name and the food you want to order.", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * 创建一个新的倒计时程序进程。
     */
    private class CounteDownDelete extends Thread {
        int count = 0;
        long primeKey;
        Boolean is_databaseCleaned;

        public CounteDownDelete(long primeKey) {
            this.primeKey = primeKey;
        }

        @Override
        public void run() {
            synchronized (DbHandler.class) {
                synchronized (DbHandler.is_reset_database) {
                    is_databaseCleaned = DbHandler.is_reset_database;
                }
            }

            if (is_databaseCleaned) {
                this.interrupt();
            }
            if (count < 30 && !is_databaseCleaned) {
                count++;
                handler.postDelayed(this, 1000);
                System.out.println(count + ", " + DbHandler.is_reset_database.toString());
            } else if (count >= 30 && !is_databaseCleaned) {
                DbHandler db = new DbHandler(OrderActivity.this);
                String deleteResult = db.getCustomerNameByUserId(primeKey);
                Toast.makeText(OrderActivity.this, deleteResult + " deleted. \n Please Refresh the page", Toast.LENGTH_LONG).show();
                db.DeleteUser(primeKey);

                System.out.println("=======================================");
                System.out.println("======== " + primeKey + " deleted ========");
                System.out.println("=======================================");
                this.interrupt();
            }
        }
    }
}