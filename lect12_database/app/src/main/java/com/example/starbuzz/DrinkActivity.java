package com.example.starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {
    public static final String EXTRA_DRINKID = "drinkId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkId = (Integer)getIntent().getExtras().get(EXTRA_DRINKID);
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            SQLiteDatabase sqLiteDatabase = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query("Drink",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[]{Integer.toString(drinkId)}, null, null, null);
            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText);
                TextView description = (TextView) findViewById(R.id.description);
                description.setText(descriptionText);
                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
                cursor.close();
                sqLiteDatabase.close();
            }
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
//
//        // get the drink from the intent
//        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);
//        Drink drink = Drink.drinks[drinkId];
//
//        // pose the name to the TextView
//        TextView name = (TextView) findViewById(R.id.name);
//        name.setText(drink.getName());
//
//        // pose the drink description
//        TextView description = (TextView) findViewById(R.id.description);
//        description.setText(drink.getDescription());
//
//        // pose the drink image
//        ImageView photo = (ImageView) findViewById(R.id.photo);
//        photo.setImageResource(drink.getImageResourceID());
//        photo.setContentDescription(drink.getName());
    }
}