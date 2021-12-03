package com.example.fitapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Date;


public class SQLHelperUser extends SQLiteOpenHelper {
    private Context context;
    private static final int DB_VERSION=1;
    private static final String DB_NAME = "NotesDB";
    private static final String TABLE_NAME = "NotesDB";
    private static final String COL_ID = "_id";
    private static final String COL_WEIGHT = "notes_name";
    private static final String COL_DATE = "notes_desc";

    public SQLHelperUser(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE="CREATE TABLE "+TABLE_NAME+"("
                + COL_ID +" INTEGER PRIMARY KEY," + COL_DATE + " TEXT,"
                +COL_WEIGHT + " INTEGER" +")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    void addUser(String date, int weight){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_DATE,date);
        cv.put(COL_WEIGHT,weight);
        long res=db.insert(TABLE_NAME,null,cv);
        if (res==-1){
            Toast.makeText(context,"failed",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context,"success",Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
