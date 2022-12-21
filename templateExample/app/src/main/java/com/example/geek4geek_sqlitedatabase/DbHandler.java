package com.example.geek4geek_sqlitedatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tutlane on 06-01-2018.
 */

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "usersdb";
    private static final String TABLE_Users = "userdetails";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOC = "location";
    private static final String KEY_DESG = "designation";

    /**
     * 调用SQLiteOpenHelper构造函数完成 context，数据库名字，工厂，数据库版本 的设置。
     * @param context 上下文（谁调用这个构造函数）
     */
    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 创建4个列的数据库表格
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_LOC + " TEXT,"
                + KEY_DESG + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    /**
     * API 的生命周期调用这个函数
     * 定义更新数据库的方式。
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
    void insertUserDetails(String name, String location, String designation) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_LOC, location);
        cValues.put(KEY_DESG, designation);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users, null, cValues);
        db.close();
    }

    // Get User Details
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, location, designation FROM " + TABLE_Users;
        Cursor cursor = db.rawQuery(query, null);   // Cursor 按照 query要求去查东西。
        while (cursor.moveToNext()) {                          // Cursor查完有一个视图，这里遍历 视图。
            HashMap<String, String> user = new HashMap<>();
            user.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("designation", cursor.getString(cursor.getColumnIndex(KEY_DESG)));
            user.put("location", cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            userList.add(user);
        }
        return userList;
    }

    // Get User Details based on userid
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, location, designation FROM " + TABLE_Users;
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_NAME, KEY_LOC, KEY_DESG}, KEY_ID + "=?", new String[]{String.valueOf(userid)}, null, null, null, null);
        if (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("designation", cursor.getString(cursor.getColumnIndex(KEY_DESG)));
            user.put("location", cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            userList.add(user);
        }
        return userList;
    }

    // Delete User Details
    public void DeleteUser(int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users, KEY_ID + " = ?", new String[]{String.valueOf(userid)});
        db.close();
    }

    // Update User Details
    public int UpdateUserDetails(String location, String designation, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_LOC, location);
        cVals.put(KEY_DESG, designation);
        int count = db.update(TABLE_Users, cVals, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        return count;
    }
}