package com.feveral.seniorhigh.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.feveral.seniorhigh.Config;
import com.feveral.seniorhigh.database.UserDatabase;
import com.feveral.seniorhigh.database.UserDatabaseHelper;

import java.util.ArrayList;

public class Favorite {

    private String _school;
    private String _department;
    private String _examine;

    public Favorite(String examine, String school, String department) {
        this._school = school;
        this._department = department;
        this._examine = examine;
    }

    public String getExamine() {
        return _examine;
    }

    public String getDepartment() {
        return _department;
    }

    public String getSchool() {
        return _school;
    }

    public static Favorite cursorToFavorite(Cursor cursor) {
        return new Favorite(
                cursor.getString(cursor.getColumnIndex("examine")),
                cursor.getString(cursor.getColumnIndex("school")),
                cursor.getString(cursor.getColumnIndex("department"))
        );
    }

    public static ArrayList<Favorite> findByExamine(String examine) {
        SQLiteDatabase database = UserDatabase.getDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + Config.TABLE_NAME_FAVORITE + " WHERE examine = ?",
                new String[] {examine});
        ArrayList<Favorite> favoriteList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Favorite favorite = cursorToFavorite(cursor);
            favoriteList.add(favorite);
        }
        return favoriteList;
    }

    public static boolean isFavoriteExist(Favorite favorite) {
        SQLiteDatabase database = UserDatabase.getDatabase();
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + Config.TABLE_NAME_FAVORITE + " WHERE examine = ? AND school = ? AND department = ?",
                new String [] {favorite.getExamine(), favorite.getSchool(), favorite.getDepartment()});
        boolean isExist = false;
        while(cursor.moveToNext()) {
            isExist = true;
        }
        return isExist;
    }

    public static void delete(Favorite favorite) {
        SQLiteDatabase database = UserDatabase.getDatabase();
        database.execSQL("DELETE FROM " + Config.TABLE_NAME_FAVORITE + " WHERE examine = ? AND school = ? AND department = ?",
                new String [] {favorite.getExamine(), favorite.getSchool(), favorite.getDepartment()});
    }

    public static void save(Favorite favorite) {
        SQLiteDatabase database = UserDatabase.getDatabase();
        database.execSQL("INSERT INTO " + Config.TABLE_NAME_FAVORITE + " VALUES (?,?,?)",
                new String [] {favorite.getExamine(), favorite.getSchool(), favorite.getDepartment()});
    }

    public static void saveOrDelete(Favorite favorite) {
        if (Favorite.isFavoriteExist(favorite)) {
            Favorite.delete(favorite);
        } else {
            Favorite.save(favorite);
        }
    }
}
