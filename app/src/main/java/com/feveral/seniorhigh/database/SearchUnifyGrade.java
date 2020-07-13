package com.feveral.seniorhigh.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.feveral.seniorhigh.Model.UnifyGrade;

import java.util.ArrayList;

/**
 * Created by feveral on 2017/8/16.
 */

public class SearchUnifyGrade {

    private String tableName = "Unify";

    public ArrayList<String> findSchoolListFromKeyword(String year , String  keyWord){
        ArrayList<String> newSchoolList = new ArrayList<>();
        for(String s : getSchoolList(year))
            if(s.contains(keyWord))
                newSchoolList.add(s);
        return newSchoolList;
    }

    public ArrayList<UnifyGrade> findDepartmentListFromKeyword(String school , String year, String  keyWord){
        ArrayList<UnifyGrade> newDepartmentList = new ArrayList<>();
        for(UnifyGrade g : getDepartmentList(school,year))
            if(g.getDepartment().contains(keyWord))
                newDepartmentList.add(g);
        return newDepartmentList;
    }

    public ArrayList<UnifyGrade> findDepartmentGroupListFromKeyword(String year, String  departmentGroup,String  keyWord){
        ArrayList<UnifyGrade> newDepartmentGroupList = new ArrayList<>();
        for(UnifyGrade g : getDepartmentGroupList(year,departmentGroup))
            if(g.getDepartment().contains(keyWord) || g.getSchool().contains(keyWord))
                newDepartmentGroupList.add(g);
        return newDepartmentGroupList;
    }

    private ArrayList<String> getSchoolList(String year){
        ArrayList<String> schoolList = new ArrayList<>();
        Cursor cursor = getSearchSchoolCursor(year);
        while(cursor.moveToNext()){
            if(!schoolList.contains(cursor.getString( cursor.getColumnIndex("school"))))
                schoolList.add(cursor.getString( cursor.getColumnIndex("school")));
        }
        return schoolList;
    }

    private ArrayList<UnifyGrade> getDepartmentList(String school, String year){
        ArrayList<UnifyGrade> departmentList = new ArrayList<>();
        Cursor cursor = getSearchDepartmentAndGradeCursor(school,year);
        while(cursor.moveToNext()){
            UnifyGrade grade = UnifyGrade.cursorToGrade(cursor);
            departmentList.add(grade);
        }
        return departmentList;
    }

    private ArrayList<UnifyGrade> getDepartmentGroupList(String year, String departmentGroup){
        ArrayList<UnifyGrade> departmentGroupList = new ArrayList<>();
        Cursor cursor = getSearchDepartmentGroupAndGradeCursor(year,departmentGroup);
        while(cursor.moveToNext()){
            UnifyGrade grade = UnifyGrade.cursorToGrade(cursor);
            departmentGroupList.add(grade);
        }
        return departmentGroupList;
    }

    private Cursor getSearchDepartmentAndGradeCursor(String school , String year){
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String[] columns = {"_id","school","year","department","grade","departmentGroup"};
        String selection = "school=? AND year=?";
        String[] selectionArgs = {school,year};
        return database.query(tableName,columns,selection,selectionArgs,null,null,null);
    }

    private Cursor getSearchSchoolCursor(String year){
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String[] toSearch = {"school"};
        String selection = "year=?";
        String[] selectionArgs = {year};
        return database.query(tableName,toSearch,selection,selectionArgs,null,null,null);
    }

    private Cursor getSearchDepartmentGroupAndGradeCursor(String year,String departmentGroup){
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String[] columns = {"_id","school","year","department","grade","departmentGroup"};
        String selection = "year=? AND departmentGroup=?";
        String[] selectionArgs = {year,departmentGroup};
        return database.query(tableName,columns,selection,selectionArgs,null,null,null);
    }
}
