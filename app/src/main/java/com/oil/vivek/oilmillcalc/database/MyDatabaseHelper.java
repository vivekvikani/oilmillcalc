package com.oil.vivek.oilmillcalc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static com.oil.vivek.oilmillcalc.database.Constants.DATABASE_NAME;
import static com.oil.vivek.oilmillcalc.database.Constants.DATABASE_VERSION;
import static com.oil.vivek.oilmillcalc.database.Constants.ID;
import static com.oil.vivek.oilmillcalc.database.Constants.IMG_URI;
import static com.oil.vivek.oilmillcalc.database.Constants.MSG_BODY;
import static com.oil.vivek.oilmillcalc.database.Constants.MSG_DATE;
import static com.oil.vivek.oilmillcalc.database.Constants.MSG_TITLE;
import static com.oil.vivek.oilmillcalc.database.Constants.TABLE_NOTIFICATION;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAILS);*/
        String query = "CREATE TABLE " + TABLE_NOTIFICATION + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, " +
                MSG_BODY + " TEXT, " +
                MSG_TITLE + " TEXT, " +
                IMG_URI + " TEXT, " +
                MSG_DATE + " TEXT " +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        onCreate(db);
    }

    public Boolean saveNotification(String body, String title, String img_uri, String date)
    {
        ContentValues values = new ContentValues();
        values.put(MSG_BODY,body);
        values.put(MSG_TITLE, title);
        values.put(IMG_URI, img_uri);
        values.put(MSG_DATE,date);
        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_NOTIFICATION,null,values);
        db.close();
        return true;
    }

    public List<HashMap<String,String>> getAllNotifications(){
        SQLiteDatabase db = getWritableDatabase();
        HashMap<String,String> notifications_map = new HashMap<String,String>();
        List<HashMap<String,String>> notification_list = new ArrayList<>();

        String sql = "SELECT * FROM table_notification";
        /*String sql = "SELECT * FROM table_notification WHERE uid = '" + uid + "'";*/
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                notifications_map.put(MSG_TITLE,c.getString(c.getColumnIndex(MSG_TITLE)));
                notifications_map.put(MSG_BODY,c.getString(c.getColumnIndex(MSG_BODY)));
                notifications_map.put(IMG_URI,c.getString(c.getColumnIndex(IMG_URI)));
                notifications_map.put(MSG_DATE,c.getString(c.getColumnIndex(MSG_DATE)));
                notification_list.add(notifications_map);
            } while (c.moveToNext());
        }
        c.close();
        db.close();

        Collections.sort(notification_list, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                return lhs.get(MSG_DATE).compareTo(rhs.get(MSG_DATE));
            }
        });

        return notification_list;
    }
}
