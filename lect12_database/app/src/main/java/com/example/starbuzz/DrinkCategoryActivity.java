package com.example.starbuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DrinkCategoryActivity extends AppCompatActivity {
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);

        ListView listDrinks = (ListView) findViewById(R.id.list_drinks);
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            sqLiteDatabase = starbuzzDatabaseHelper.getReadableDatabase();
            cursor = sqLiteDatabase.query("DRINK", new String[]{"_id", "NAME"},
                    null, null, null, null, null);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
                    new String[]{"NAME"}, new int[]{android.R.id.text1}, 0);
            listDrinks.setAdapter(listAdapter);
        } catch (SQLException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
        
        
//        // use the Adapter (android) to bring the gap between Data Source and ListView
//        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_list_item_1,
//                Drink.drinks
//        );
//        // make the ListView use the array adapter via the setAdapter() method
//        ListView listDrinks = (ListView) findViewById(R.id.list_drinks);
//        listDrinks.setAdapter(listAdapter);
//
        // create a Listener AdapterView
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    AdapterView<?> listDrinks,
                    View itemView,
                    int position,
                    long id) {
                Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);
                startActivity(intent);
            }
        };
        // link the listener to an ListView
        listDrinks.setOnItemClickListener(itemClickListener);
    }
}