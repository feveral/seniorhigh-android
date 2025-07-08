package com.feveral.seniorhigh.database;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import com.feveral.seniorhigh.Model.BasicGrade;

import java.util.ArrayList;

/**
 * Created by feveral on 2017/8/15.
 */

public class SearchBasicGrade {

    public String _tableName = "Basic";

    public ArrayList<String> findSchoolListFromKeyword(String year , String  keyWord){
        ArrayList<String> newSchoolList = new ArrayList<>();
        for(String s : getSchoolList(year))
            if(s.contains(keyWord))
                newSchoolList.add(s);
        return newSchoolList;
    }

    public ArrayList<BasicGrade> findDepartmentListFromKeyword(String school , String year, String  keyWord){
        ArrayList<BasicGrade> newDepartmentList = new ArrayList<>();
        for(BasicGrade g : getDepartmentList(school,year))
            if(g.getDepartment().contains(keyWord))
                newDepartmentList.add(g);
        return newDepartmentList;
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

    private ArrayList<BasicGrade> getDepartmentList(String school, String year){
        ArrayList<BasicGrade> departmentList = new ArrayList<>();
        Cursor cursor = getSearchDepartmentAndGradeCursor(school,year);
        while(cursor.moveToNext()){
            BasicGrade grade = BasicGrade.cursorToGrade(cursor);
            departmentList.add(grade);
        }
        return departmentList;
    }

    private Cursor getSearchSchoolCursor(String year){
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String[] toSearch = {"school"};
        String selection = "year=?";
        String[] selectionArgs = {year};
        return database.query(_tableName,toSearch,selection,selectionArgs,null,null,null);
    }

    private Cursor getSearchDepartmentAndGradeCursor(String school , String year){
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String[] columns = {"school","year","department","firstOrder","firstGrade","secondOrder","secondGrade",
                "thirdOrder","thirdGrade", "fourthOrder","fourthGrade","fifthOrder","fifthGrade","people"};
        String selection = "school=? AND year=?";
        String[] selectionArgs = {school,year};
        return database.query(_tableName,columns,selection,selectionArgs,null,null,null);
    }

    public BasicGrade getExactDepartmentGrade(String year , String school , String department){
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String[] columns = {"school","year","department","firstOrder","firstGrade","secondOrder","secondGrade",
                "thirdOrder","thirdGrade", "fourthOrder","fourthGrade","fifthOrder","fifthGrade","people"};
        String selection = "year=? AND school=? AND department=?";
        String[] selectionArgs = {year,school,department};
        Cursor cursor = database.query(_tableName,columns,selection,selectionArgs,null,null,null);
        BasicGrade grade;
        cursor.moveToNext();
        try {
            grade = BasicGrade.cursorToGrade(cursor);
        } catch (CursorIndexOutOfBoundsException ex){
            grade = new BasicGrade();
        }
        return grade;
    }
}