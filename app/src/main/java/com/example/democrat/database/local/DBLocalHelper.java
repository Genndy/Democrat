package com.example.democrat.database.local;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBLocalHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "localStorageDB";
    public static final String TABLE_LOGGED_USER = "loggedUser";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";

    public DBLocalHelper(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Вызывается при создании таблицы
        db.execSQL("CREATE TABLE " + TABLE_LOGGED_USER + "(" + KEY_ID
                + " integer primary key, " + KEY_NAME +  " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_LOGGED_USER);
        onCreate(db);
    }
}
