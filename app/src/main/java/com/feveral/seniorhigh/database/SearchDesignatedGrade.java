package com.feveral.seniorhigh.database;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import com.feveral.seniorhigh.Model.DesignatedGrade;

import java.util.ArrayList;

/**
 * Created by feveral on 2017/7/30.
 */

public class SearchDesignatedGrade {

    private static final String[] COLUMNS = {"year", "school", "department", "people",
                                       "chinese", "english", "mathAdvance", "mathBasic",
                                       "mathA", "mathB", "physical", "chemistry", "biological",
                                       "geography", "history", "citizen", "society", "nature",
                                       "skill", "allGrade"};

    public ArrayList<String> findSchoolListFromKeyword(String year , String keyWord){
        ArrayList<String> newSchoolList = new ArrayList<>();
        for(String s : getSchoolList(year))
            if(s.contains(keyWord))
                newSchoolList.add(s);
        return newSchoolList;
    }

    public ArrayList<DesignatedGrade> findDepartmentListFromKeyword(String school , String year, String  keyWord){
        ArrayList<DesignatedGrade> newDepartmentList = new ArrayList<>();
        for(DesignatedGrade g : getDepartmentList(school,year))
            if(g.getDepartment().contains(keyWord))
                newDepartmentList.add(g);
        return newDepartmentList;
    }

    public ArrayList<DesignatedGrade> findFirstGroupListFromKeyword(String year , String keyWord){
        ArrayList<DesignatedGrade> newFirstGroupList = new ArrayList<>();
        for(DesignatedGrade g : getFirstGroupList(year))
            if(g.getSchool().contains(keyWord) || g.getDepartment().contains(keyWord))
                newFirstGroupList.add(g);
        return newFirstGroupList;
    }

    public ArrayList<DesignatedGrade> findSecondGroupListFromKeyword(String year ,String keyWord){
        ArrayList<DesignatedGrade> newSecondGroupList = new ArrayList<>();
        for(DesignatedGrade g : getSecondGroupList(year))
            if(g.getSchool().contains(keyWord) || g.getDepartment().contains(keyWord))
                newSecondGroupList.add(g);
        return newSecondGroupList;
    }

    public ArrayList<DesignatedGrade> findThirdGroupListFromKeyword(String year , String keyWord){
        ArrayList<DesignatedGrade> newThirdGroupList = new ArrayList<>();
        for(DesignatedGrade g : getThirdGroupList(year))
            if(g.getSchool().contains(keyWord) || g.getDepartment().contains(keyWord))
                newThirdGroupList.add(g);
        return newThirdGroupList;
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

    private ArrayList<DesignatedGrade> getDepartmentList(String school, String year){
        ArrayList<DesignatedGrade> departmentList = new ArrayList<>();
        Cursor cursor = getSearchDepartmentAndGradeCursor(school,year);
        while(cursor.moveToNext()){
            DesignatedGrade grade = DesignatedGrade.cursorToGrade(cursor);
            departmentList.add(grade);
        }
        return departmentList;
    }

    public ArrayList<DesignatedGrade> getFirstGroupList(String year){
        ArrayList<DesignatedGrade> firstGroupList = new ArrayList<>();
        Cursor cursor = getFirstGroupCursor(year);
        while(cursor.moveToNext()) {
            DesignatedGrade grade = DesignatedGrade.cursorToGrade(cursor);
            firstGroupList.add(grade);
        }
        return firstGroupList;
    }

    public ArrayList<DesignatedGrade> getSecondGroupList(String year){
        ArrayList<DesignatedGrade> secondGroupList = new ArrayList<>();
        Cursor cursor = getSecondGroupCursor(year);
        while(cursor.moveToNext()) {
            DesignatedGrade grade = DesignatedGrade.cursorToGrade(cursor);
            secondGroupList.add(grade);
        }
        return secondGroupList;
    }

    public ArrayList<DesignatedGrade> getThirdGroupList(String year){
        ArrayList<DesignatedGrade> thirdGroupList = new ArrayList<>();
        Cursor cursor = getThirdGroupCursor(year);
        while(cursor.moveToNext()) {
            DesignatedGrade grade = DesignatedGrade.cursorToGrade(cursor);
            thirdGroupList.add(grade);
        }
        return thirdGroupList;
    }

    private Cursor getSearchSchoolCursor(String year){
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String[] toSearch = {"school"};
        String selection = "year=?";
        String[] selectionArgs = {year};
        return database.query("Designated",toSearch,selection,selectionArgs,null,null,null);
    }

    private Cursor getSearchDepartmentAndGradeCursor(String school , String year){
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String selection = "school=? AND year=?";
        String[] selectionArgs = {school,year};
        return database.query("Designated",COLUMNS,selection,selectionArgs,null,null,null);
    }

    private Cursor getFirstGroupCursor(String year){
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String selection = "year=? AND (mathB>0 OR mathBasic>0)";
        String[] selectionArgs = {year};
        return database.query("Designated",COLUMNS,selection,selectionArgs,null,null,null);
    }

    private Cursor getSecondGroupCursor(String year){
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String selection = "year=? AND ((mathA>0 OR mathAdvance>0) AND biological is NULL)";
        String[] selectionArgs = {year};
        return database.query("Designated",COLUMNS,selection,selectionArgs,null,null,null);
    }

    private Cursor getThirdGroupCursor(String year) {
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String selection = "year=? AND biological>0";
        String[] selectionArgs = {year};
        return database.query("Designated", COLUMNS, selection, selectionArgs, null, null, null);
    }

    public DesignatedGrade getExactDepartmentGrade(String year , String school , String department){
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String selection = "year=? AND school=? AND department=?";
        String[] selectionArgs = {year,school,department};
        Cursor cursor = database.query("Designated", COLUMNS, selection, selectionArgs , null , null , null);
        DesignatedGrade grade;
        cursor.moveToNext();
        try {
            grade = DesignatedGrade.cursorToGrade(cursor);
        }catch (CursorIndexOutOfBoundsException ex){
            grade = new DesignatedGrade();
        }
        return grade;
    }
}
