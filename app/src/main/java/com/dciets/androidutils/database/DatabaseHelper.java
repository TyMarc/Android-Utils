package com.dciets.androidutils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, "db", null, 1);
    }

    public static void init(final Context context) {
        if (instance != null) {
            return;
        }
        instance = new DatabaseHelper(context);
    }

    public static DatabaseHelper getInstance() {
        return instance;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE roommates (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, facebook_id TEXT, monitor INTEGER DEFAULT 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists roommates");
        onCreate(db);
    }

    public void addRoommate(String name, String facebookId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv = new ContentValues();
        cv.put("name", name);

        if(isUserAlreadyInDB(facebookId)) {
            db.update("roommates", cv, "facebook_id=?", new String[]{facebookId});
        } else {
            cv.put("facebook_id", facebookId);
            db.insert("roommates", null, cv);
        }
    }

    public void updateMonitor(String facebookId, boolean isMonitor) {
        Log.i(TAG, "updateMonitor: facebookId=" + facebookId + " isMonitor=" + isMonitor);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("monitor", isMonitor ? 1 : 0);
        db.update("roommates", cv, "facebook_id = ?", new String[]{facebookId});
    }

    public void deleteRoommate(String facebookId) {
        Log.i(TAG, "deleteRoommate: facebookId=" + facebookId);
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("roommates", "facebook_id = ?", new String[]{facebookId});
    }

    public String getFacebookName(final String facebookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String name = null;

        Cursor c = db.rawQuery("SELECT name FROM roommates WHERE facebook_id = ?;", new String[]{facebookId});

        while (c.moveToNext()) {
            name = c.getString(0);
            break;
        }

        c.close();

        return name;
    }

    public boolean isUserAlreadyInDB(final String facebookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isAlready = false;

        Cursor c = db.rawQuery("SELECT COUNT(*) FROM roommates WHERE facebook_id = ?;", new String[]{facebookId});

        while (c.moveToNext()) {
            isAlready = c.getInt(0) > 0;
            break;
        }

        c.close();

        return isAlready;
    }

    public boolean isMonitor(String facebookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isBoolean = false;

        Cursor c = db.rawQuery("SELECT monitor FROM roommates WHERE facebook_id = ?;", new String[]{facebookId});

        while (c.moveToNext()) {
            isBoolean = c.getInt(0) == 1;
            break;
        }

        c.close();

        return isBoolean;
    }
}