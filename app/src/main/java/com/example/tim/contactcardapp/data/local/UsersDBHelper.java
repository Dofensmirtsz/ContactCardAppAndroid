package com.example.tim.contactcardapp.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tim.contactcardapp.data.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tim on 26-10-2017.
 */

public class UsersDBHelper extends SQLiteOpenHelper
{

    private static final String DB_NAME = "login.db";
    private static final String TABLE_NAME = "logins";

    private static final int DB_VERSION = 4;

    private static final String COLOMN_ID = "_id";  // primary key, auto increment
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public UsersDBHelper(Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DB_NAME, factory, DB_VERSION);
    }

    // Als de db niet bestaat wordt de db gemaakt. In de onCreate() de query
    // voor de aanmaak van de database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" +
                COLOMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_USERNAME + " TEXT," +
                COLUMN_PASSWORD + " TEXT" +
                ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }

    // Bij verandering van de db wordt onUpgrade aangeroepen.
    // Wat zou je hier kunnen doen? Weggooien en opnieuw aanmaken?
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // CRUD functies hier ....

    public void addUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void printUsers() {
        List<User> userList = new ArrayList<User>();
        List<String> idList = new ArrayList<String>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                User user = new User();
                user.setUsername(String.valueOf(cursor.getString(1)));
                user.setPassword(String.valueOf(cursor.getString(2)));
                userList.add(user);
                idList.add(String.valueOf(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        Log.i("USERLIST", "--------------------------------------------");

        for(int i =0; i<userList.size(); i++){
            Log.i("USERLIST",idList.get(i) + "  -  " + userList.get(i).toString());
            Log.i("USERLIST", "--------------------------------------------");
        }
    }

    public List<User> getUserList(){
        List<User> userList = new ArrayList<User>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                User user = new User();
                user.setUsername(String.valueOf(cursor.getString(cursor.getColumnIndex("username"))));
                user.setPassword(String.valueOf(cursor.getString(cursor.getColumnIndex("password"))));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        return userList;
    }

    //SELECT has to be 1 value;
    public String getDBValue(String select, String where, String whereValue){
        String result = "NO VALUE";
        String query = "SELECT " + select + " FROM " + TABLE_NAME + " WHERE " + where + " = '" + whereValue + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            result = String.valueOf(cursor.getString(cursor.getColumnIndex(select)));
        }

        Log.i("getDBValue", "String from DB: " + result);

        return result;
    }
}
