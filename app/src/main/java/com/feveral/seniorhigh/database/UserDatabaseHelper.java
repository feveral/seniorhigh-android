package com.feveral.seniorhigh.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.feveral.seniorhigh.Config;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    public UserDatabaseHelper(Context context) {
        super(context, Config.USER_DB_FILE_NAME, null, Config.USER_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Config.TABLE_NAME_FAVORITE +
            "(" +
                "examine TEXT NOT NULL ," +
                "school TEXT NOT NULL , " +
                "department TEXT NOT NULL ," +
                "PRIMARY KEY (examine, school, department)"+
            ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
