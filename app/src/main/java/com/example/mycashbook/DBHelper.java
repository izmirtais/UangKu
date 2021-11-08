package com.example.mycashbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "MyCashBook.db";

    // User table name
    private static final String TABLE_USER = "user";
    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USERNAME = "user_username";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // Cashflow table name
    private static final String TABLE_CASHFLOW = "cashflow";
    // User Table Columns names
    private static final String COLUMN_CASHFLOW_ID = "cashflow_id";
    private static final String COLUMN_USERNAME_FK = "username";
    private static final String COLUMN_CASHFLOW_JENIS = "jenis";
    private static final String COLUMN_CASHFLOW_TGL = "tgl";
    private static final String COLUMN_CASHFLOW_NOMINAL = "nominal";
    private static final String COLUMN_CASHFLOW_KET = "keterangan";


    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USERNAME + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT)";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    // create table sql query
    private String CREATE_CASHFLOW_TABLE = "CREATE TABLE " + TABLE_CASHFLOW + "("
            + COLUMN_CASHFLOW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USERNAME_FK + " TEXT,"
            + COLUMN_CASHFLOW_JENIS + " TEXT," + COLUMN_CASHFLOW_TGL + " TEXT," +COLUMN_CASHFLOW_NOMINAL +
            " TEXT," +COLUMN_CASHFLOW_KET + " TEXT)";

    // drop table sql query
    private String DROP_CASHFLOW_TABLE = "DROP TABLE IF EXISTS " + TABLE_CASHFLOW;

    /**
     * Constructor
     *
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CASHFLOW_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        // Create tables again
        onCreate(db);
        //Drop User Table if exist
        db.execSQL(DROP_CASHFLOW_TABLE);
        // Create tables again
        onCreate(db);
    }
    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addCashflow(Cashflow cashflow, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME_FK, username);
        values.put(COLUMN_CASHFLOW_JENIS, cashflow.getJenis());
        values.put(COLUMN_CASHFLOW_TGL, cashflow.getTgl());
        values.put(COLUMN_CASHFLOW_NOMINAL, cashflow.getNominal());
        values.put(COLUMN_CASHFLOW_KET, cashflow.getKeterangan());

        // Inserting Row
        db.insert(TABLE_CASHFLOW, null, values);
        db.close();
    }
    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USERNAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USERNAME + " ASC";
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return userList;
    }
    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        // updating row
        db.update(TABLE_USER, values, COLUMN_USERNAME + " = ?",
                new String[]{String.valueOf(user.getUsername())});
        db.close();
    }
    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
    /**
     * This method to check user exist or not
     *
     * @param username
     * @return true/false
     */
    public boolean checkUser(String username) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USERNAME + " = ?";
        // selection argument
        String[] selectionArgs = {username};
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
    /**
     * This method to check user exist or not
     *
     * @param username
     * @param password
     * @return true/false
     */
    public boolean checkUser(String username, String password) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {username, password};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public List<Cashflow> getAllCashflow(String username) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_CASHFLOW_ID,
                COLUMN_CASHFLOW_JENIS,
                COLUMN_CASHFLOW_NOMINAL,
                COLUMN_CASHFLOW_KET,
                COLUMN_CASHFLOW_TGL
        };
        // sorting orders
        String sortOrder =
                COLUMN_CASHFLOW_TGL + " DESC";
        List<Cashflow> cashflowList = new ArrayList<Cashflow>();
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USERNAME_FK + " = ?";
        // selection arguments
        String[] selectionArgs = {username};
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_CASHFLOW, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cashflow cashflow = new Cashflow();
                cashflow.setId_cashflow(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CASHFLOW_ID))));
                cashflow.setJenis(cursor.getString(cursor.getColumnIndex(COLUMN_CASHFLOW_JENIS)));
                cashflow.setNominal(cursor.getString(cursor.getColumnIndex(COLUMN_CASHFLOW_NOMINAL)));
                cashflow.setKeterangan(cursor.getString(cursor.getColumnIndex(COLUMN_CASHFLOW_KET)));
                cashflow.setTgl(cursor.getString(cursor.getColumnIndex(COLUMN_CASHFLOW_TGL)));
                // Adding user record to list
                cashflowList.add(cashflow);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return cashflowList;
    }
}
