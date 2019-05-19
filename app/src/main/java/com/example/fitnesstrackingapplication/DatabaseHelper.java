package com.example.fitnesstrackingapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(username text primary key, password text, Sunday text, Monday text, Tuesday text, Wednesday text," +
                "Thursday text, Friday text, Saturday text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
    }
    //insert Login into database
    public boolean insert(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("Sunday", 0);
        contentValues.put("Monday", 0);
        contentValues.put("Tuesday", 0);
        contentValues.put("Wednesday", 0);
        contentValues.put("Thursday", 0);
        contentValues.put("Friday", 0);
        contentValues.put("Saturday", 0);
        long ins = db.insert("user", null, contentValues);
        if (ins == -1) {
            return false;
        }
        return true;
    }
    //check if Login exists in database. True if exists. False if does not exist.
    public boolean exist(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=?", new String[]{username});
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }
    //Login. True if correct username/password. False if wrong username/password.
    public boolean Login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            String u = cursor.getString(cursor.getColumnIndex("username"));
            String p = cursor.getString(cursor.getColumnIndex("password"));
            if (username.equals(u) && password.equals(p)) {
                return true;
            }
            cursor.moveToNext();
        }
        return false;
    }
    public int getDistance(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=?", new String[]{username});
        cursor.moveToFirst();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
        while (cursor.isAfterLast() == false) {
            if ((cursor.getString(cursor.getColumnIndex("username")).equals(username))) {
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                String day = "";
                if (dayOfWeek == Calendar.SUNDAY) {
                    day = "Sunday";
                } else if (dayOfWeek == Calendar.MONDAY) {
                    day = "Monday";
                } else if (dayOfWeek == Calendar.TUESDAY) {
                    day = "Tuesday";
                } else if (dayOfWeek == Calendar.WEDNESDAY) {
                    day = "Wednesday";
                } else if (dayOfWeek == Calendar.THURSDAY) {
                    day = "Thursday";
                } else if (dayOfWeek == Calendar.FRIDAY) {
                    day = "Friday";
                } else {
                    day = "Saturday";
                }
                return cursor.getInt(cursor.getColumnIndex(day));
            }
            cursor.moveToNext();
        }
        return 100;
    }
}