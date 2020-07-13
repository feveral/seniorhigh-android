package com.feveral.seniorhigh.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class UserDatabase {

    private static SQLiteDatabase database = null;
    private static Context context = null;

    public static void initialDatabase(Context context) {
        UserDatabase.context = context;
    }

    public static SQLiteDatabase getDatabase() {
        if (UserDatabase.database == null) {
            UserDatabaseHelper dbHelper = new UserDatabaseHelper(UserDatabase.context);
            UserDatabase.database = dbHelper.getWritableDatabase();
        }
        return UserDatabase.database;
    }
}
