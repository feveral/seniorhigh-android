package com.feveral.seniorhigh.database;

/**
 * Created by feveral on 2017/8/10.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.feveral.seniorhigh.Config;

public class GradeDatabase {

    public static final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = Config.getDataBaseFileName();
    public static final String PACKAGE_NAME = "com.feveral.seniorhigh";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;
    private static SQLiteDatabase database = null;
    private static Context context;

    public static SQLiteDatabase getDatabase() {
        if (GradeDatabase.database == null) {
            GradeDatabase.database = GradeDatabase.openDatabase(DB_PATH + "/" + DB_NAME);
        }
        return GradeDatabase.database;
    }

    public static void initialDatabase(Context context) {
        GradeDatabase.context = context;
    }

    public static SQLiteDatabase openDatabase(String dbfile) {
        try {
            if (!(new File(dbfile).exists())) {
                InputStream is = context.getResources().openRawResource(Config.getDatabaseResourceId());
                FileOutputStream fos = new FileOutputStream(dbfile);

                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,null);
            return db;
        }
        catch (Exception e) {
            Log.e("Database", "GetDatabase Fail");
            e.printStackTrace();
        }
        return null;
    }
}