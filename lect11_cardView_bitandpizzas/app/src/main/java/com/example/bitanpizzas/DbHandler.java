/**
 * 在这个文件里面创建数据库以及自定义数据库相关的操作。
 */
package com.example.bitanpizzas;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    /**
     * ** 特别要注意一点是 database name 和 table name 不能够相同。否则找不到 table。
     */
    private static final String DB_NAME = "bitanpizzas";
    private static final String TABLE_NAME = "INDENTS";
    private static final String KEY_id = "ID";
    public static final String KEY_CustomerName = "CUSTOMER_NAME";
    public static final String KEY_CuisineName = "CUISINE_NAME";
    private static DbHandler indentDatabaseHelper;
    public static Boolean is_reset_database = false;

    /**
     * 构造一个数据库。
     * @param context 使用这个函数的（上下文）对象。
     */
    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        System.out.println("===========================================");
        System.out.println("======= Database initialization ===========");
        System.out.println("===========================================");
    }

    /**
     * 创建数据库表格，API调用，onUpgrade调用。
     *
     * @param db
     */
    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
//        updateMyDatabase(db, 0, DB_VERSION);
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_CustomerName + " TEXT," +
                KEY_CuisineName + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

        System.out.println("-----------------------------");
        System.out.println("-------- DB onCreate --------");
        System.out.println("-----------------------------");
    }

    /**
     * API 生命周期调用
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        System.out.println("--------------------------------");
        System.out.println("-------- DB onUpgrade ----------");
        System.out.println("--------------------------------");
    }

    public void onUpgrade() {
        onUpgrade(this.getWritableDatabase(), 0, 0);
    }

    /**
     * 插入数据。
     *
     * @param customer_name 顾客的名字。
     * @param cuisine_name  菜品的名字。
     * @return 如果数据插入成功返回true，否则返回false。
     */
    public long insertIndent(String customer_name, String cuisine_name) {
        SQLiteDatabase db = this.getWritableDatabase();         // 获得 可写模式的 数据库
        ContentValues contentValues = new ContentValues();      // 创建值的键值对（表格），列的名字是键值。
        contentValues.put(KEY_CustomerName, customer_name);
        contentValues.put(KEY_CuisineName, cuisine_name);
        // 将新的一行信息加入，返回的值是新的一行对应的 primary key value。
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();                                             // 使用完数据库不要忘记关掉。
        return result;
    }

    /**
     * 获得整个点单数据库的数据。
     *
     * @return 阅读整个数据库，将数据处理好成ArrayList，返回ArrayList
     */
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetIndents() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> indentList = new ArrayList<>();
        String query = "SELECT " + KEY_CustomerName + ", " + KEY_CuisineName + " FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> indent = new HashMap<>();
            indent.put(KEY_CustomerName, cursor.getString(cursor.getColumnIndex(KEY_CustomerName)));
            indent.put(KEY_CuisineName, cursor.getString(cursor.getColumnIndex(KEY_CuisineName)));
            indentList.add(indent);                 // 将一行数据加入到要返回的ArrayList当中。
        }
        return indentList;
    }

    // Delete User Details
    public void DeleteUser(long userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_id+" = ?",new String[]{String.valueOf(userid)});
        db.close();
    }

    // Get User Details based on userid
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetUserByUserId(long userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
//        String query = "SELECT name, location, designation FROM "+ TABLE_NAME;
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_id, KEY_CustomerName, KEY_CuisineName}, KEY_id+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put(KEY_CustomerName,cursor.getString(cursor.getColumnIndex(KEY_CustomerName)));
            user.put(KEY_CuisineName,cursor.getString(cursor.getColumnIndex(KEY_CuisineName)));
            userList.add(user);
        }
        return  userList;
    }

    public String getCustomerNameByUserId(long userid) {
        ArrayList<HashMap<String, String>> userList = GetUserByUserId(userid);
        String[] customerCuisineKeyPair = userList.get(0).values().toArray(new String[0]);
        String ret = customerCuisineKeyPair[0] + ", " + customerCuisineKeyPair[1];
        return ret;
    }



}
