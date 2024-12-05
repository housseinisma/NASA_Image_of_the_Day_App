package com.example.nasaimageapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "nasa_images.db";
    private static final String TABLE_NAME = "saved_images";
    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " TEXT, " +
                COL_DATE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertImage(String title, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TITLE, title);
        contentValues.put(COL_DATE, date);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // Return true if inserted successfully
    }

    public ArrayList<String> getAllImages() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> images = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                String date = cursor.getString(cursor.getColumnIndex(COL_DATE));
                images.add(title + " - " + date);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return images;
    }

    public boolean deleteImage(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_TITLE + " = ?", new String[]{title}) > 0;
    }
}
