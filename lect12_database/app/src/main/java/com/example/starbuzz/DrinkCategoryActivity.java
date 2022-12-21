/**
 * 例子：
 *  通过调取数据库的信息来设置ListView以达到动态设置的效果
 *  设置ListView点击监听器，来跳转到下一个Activity（读取数据库并且把找到的信息设置在下一个页面中）
 */
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
import android.widget.ListView;
import android.widget.Toast;

public class DrinkCategoryActivity extends AppCompatActivity {
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);


        // ###################################################################
        // ######################## 访问数据库一系列的操作 #######################
        // ###################################################################
        ListView listDrinks = (ListView) findViewById(R.id.list_drinks);
        /**
         * 继承 SQLiteOpenHelper的类 创建使用数据库的Helper。Helper把数据放入数据库当中。
         */
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            /**
             * 获得数据库需要通过Helper
             */
            sqLiteDatabase = starbuzzDatabaseHelper.getReadableDatabase();
            /**
             * String table,
             * String[] columns,
             * String selection,
             * String[] selectionArgs,
             * String groupBy,
             * String having,
             * String orderBy
             *
             * 创建游标，告诉游标关于查询的信息。我们访问数据库要通过 游标。
             */
            cursor = sqLiteDatabase.query(
                    "DRINK",
                    new String[]{"_id", "NAME"},
                    null,
                    null,
                    null,
                    null,
                    null);
            /**
             * 游标 访问数据库返回的数据需要 通过 游标配适器 来进行接下来的操作
             */
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
            listDrinks.setAdapter(listAdapter);  // 游标配适器 设置给ListView 就可以显示出 游标找到的信息
        } catch (SQLException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
        // ###################################################################
        // ############################# END #################################
        // ###################################################################


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
        /**
         * 创建并设置好 点击监听器，并把点击的位置传递给DrinkActivity.class（之后通过查找数据库 的方式）
         */
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    AdapterView<?> listDrinks,
                    View itemView,
                    int position,
                    long id) {
                Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINK_ID, (int) id);
                startActivity(intent);
            }
        };

        listDrinks.setOnItemClickListener(itemClickListener); // 将设置好的 点击监听器 匹配给 ListView
    }
}