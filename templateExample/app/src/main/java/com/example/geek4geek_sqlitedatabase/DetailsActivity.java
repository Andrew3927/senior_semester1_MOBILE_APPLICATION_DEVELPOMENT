package com.example.geek4geek_sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tutlane on 05-01-2018.
 */

public class DetailsActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        DbHandler db = new DbHandler(this);
        ArrayList<HashMap<String, String>> userList = db.GetUsers();
        ListView lv = (ListView) findViewById(R.id.user_list);
        /**
         * 构建一堆 list_row 的 ListAdapter
         *
         * android.content.Context context,                             哪里使用SimpleAdapter
         * java.util.List<? extends java.util.Map<String, ?>> data,     对什么数据源进行处理
         * @LayoutRes int resource,                                     要配适到哪一个Layout文件
         * String[] from,                                               数据源里面的columns名
         * @IdRes int[] to                                              list row xml 对应的位置
         */
        ListAdapter adapter = new SimpleAdapter(DetailsActivity.this, userList, R.layout.list_row, new String[]{"name", "designation", "location"}, new int[]{R.id.name, R.id.designation, R.id.location});
        /**
         * 将拿着一堆 list_row 的配适器设置给 ListView
         */
        lv.setAdapter(adapter);
        /**
         * 一个设置buttom相应的模板。
         */
        Button back = (Button) findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}