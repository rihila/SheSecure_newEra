package com.example.shesecure10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SOSDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sos_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "emergency_emails";
    private static final String COLUMN_USERNAME = "username";//rijilar ta extract hocchena
    private static final String COLUMN_EMAIL = "email";

    public SOSDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("SOSDatabaseHelper", "Creating database and table...");
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT)";
        db.execSQL(createTableQuery);
        Log.d("SOSDatabaseHelper", "Database and table created successfully.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addEmergencyEmail(String username, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, username);
            values.put(COLUMN_EMAIL, email);
            long result = db.insert(TABLE_NAME, null, values);
            if (result == -1) {
                Log.e("SOSDatabaseHelper", "Error inserting emergency email");
            } else {
                Log.d("SOSDatabaseHelper", "Emergency email inserted successfully");
            }
            db.close();
        } else {
            Log.e("SOSDatabaseHelper", "Writable database is null");
        }
    }


    public List<String> getEmergencyEmailsForCurrentUser(String currentUser) {
        List<String> emergencyEmails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = ?", new String[]{currentUser});
            if (cursor != null) {
                int emailColumnIndex = cursor.getColumnIndex(COLUMN_EMAIL);
                if (emailColumnIndex != -1 && cursor.moveToFirst()) {
                    do {
                        String email = cursor.getString(emailColumnIndex);
                        emergencyEmails.add(email);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                db.close();
            }
        } else {
            Log.e("SOSDatabaseHelper", "Readable database is null");
        }
        Log.d("SOSDatabaseHelper", "Retrieved emergency emails: " + emergencyEmails.toString());
        return emergencyEmails;
    }


}
