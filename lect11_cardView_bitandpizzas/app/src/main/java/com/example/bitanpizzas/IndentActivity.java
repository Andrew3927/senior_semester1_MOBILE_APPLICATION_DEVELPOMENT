package com.example.bitanpizzas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class IndentActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent);

        DbHandler db = new DbHandler(this);
        ArrayList<HashMap<String, String>> indentList = db.GetIndents();
        ListView lv = (ListView) findViewById(R.id.list_indents);
        ListAdapter adapter = new SimpleAdapter(this, indentList, R.layout.list_row, new String[]{DbHandler.KEY_CustomerName, DbHandler.KEY_CuisineName}, new int[]{R.id.name, R.id.cuisine});
        lv.setAdapter(adapter);

        Button back = (Button) findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(IndentActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

//        Button cleanDatabase = (Button) findViewById(R.id.clean_database);
//        cleanDatabase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                confirmDialog();
//            }
//        });

        Button refresh = (Button) findViewById(R.id.btnRefresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPage();
            }
        });
    }

    public void refreshPage() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(IndentActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Warning!");
        builder.setMessage("Do you want to remove all record? All record will be deleted permanently " +
                "if you click the confirm button.");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DbHandler.is_reset_database = true;

                        DbHandler db = new DbHandler(IndentActivity.this);
                        db.onUpgrade();
                        intent = new Intent(IndentActivity.this, OrderActivity.class);
                        startActivity(intent);

                        DbHandler.is_reset_database = false;
                    }
                }
        );
        builder.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}