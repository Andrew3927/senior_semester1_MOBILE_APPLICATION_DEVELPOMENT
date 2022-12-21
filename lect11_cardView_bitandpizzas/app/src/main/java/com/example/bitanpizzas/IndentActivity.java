package com.example.bitanpizzas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

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
    }
}