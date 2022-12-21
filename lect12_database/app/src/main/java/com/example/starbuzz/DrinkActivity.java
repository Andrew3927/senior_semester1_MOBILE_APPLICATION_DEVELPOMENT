/**
 * 例子：
 *  处理意图传递过来的信息，并且用此信息去数据库寻找数据，把数据设置在当前的视图上。
 */
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
    public static final String EXTRA_DRINK_ID = "drinkId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkId = (Integer)getIntent().getExtras().get(EXTRA_DRINK_ID);  // 从传递来的意图中解析出额外的信息
        /**
         * 每次访问数据库前都要new 一个Helper。
         */
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            /**
             * 每次访问数据库都要通过Helper来getReadableDatabase。
             */
            SQLiteDatabase sqLiteDatabase = starbuzzDatabaseHelper.getReadableDatabase();
            /**
             * String table,
             * String[] columns,
             * String selection,
             * String[] selectionArgs,
             * String groupBy,
             * String having,
             * String orderBy
             *
             * 每次数据库信息查找都要通过 游标进行
             */
            Cursor cursor = sqLiteDatabase.query(
                    "Drink",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[]{Integer.toString(drinkId)},
                    null,
                    null,
                    null);
            if (cursor.moveToFirst()) {
                // ##### 获取 NAME, DESCRIPTION, IMAGE_RESOURCE_ID 对应的信息 #####
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                // ####################### EMD ################################

                // ############### 将获得的信息放在layout里面 #####################
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText);
                TextView description = (TextView) findViewById(R.id.description);
                description.setText(descriptionText);
                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
                // ####################### EMD ################################


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