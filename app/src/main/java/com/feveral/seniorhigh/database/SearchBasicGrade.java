package com.feveral.seniorhigh.database;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import com.feveral.seniorhigh.Model.BasicGrade;
import com.feveral.seniorhigh.utility.TextUtils;

import java.util.ArrayList;

/**
 * Created by feveral on 2017/8/15.
 */

public class SearchBasicGrade {

    public String _tableName = "Basic";

    public ArrayList<String> findSchoolListFromKeyword(String year , String  keyWord){
        ArrayList<String> newSchoolList = new ArrayList<>();
        for(String s : getSchoolList(year))
            if(TextUtils.isFuzzyMatch(s, keyWord))
                newSchoolList.add(s);
        return newSchoolList;
    }

    public ArrayList<BasicGrade> findDepartmentListFromKeyword(String school , String year, String  keyWord){
        ArrayList<BasicGrade> newDepartmentList = new ArrayList<>();
        for(BasicGrade g : getDepartmentList(school,year))
            if(TextUtils.isFuzzyMatch(g.getDepartment(), keyWord) || TextUtils.isFuzzyMatch(g.getSchool() + g.getDepartment(), keyWord))
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

    public ArrayList<BasicGrade> getFirstGroupList(String year) {
        ArrayList<BasicGrade> list = new ArrayList<>();
        Cursor cursor = getFirstGroupCursor(year);
        while (cursor.moveToNext()) {
            list.add(BasicGrade.cursorToGrade(cursor));
        }
        return list;
    }

    public ArrayList<BasicGrade> getSecondGroupList(String year) {
        ArrayList<BasicGrade> list = new ArrayList<>();
        Cursor cursor = getSecondGroupCursor(year);
        while (cursor.moveToNext()) {
            list.add(BasicGrade.cursorToGrade(cursor));
        }
        return list;
    }

    private static final String HAS_MATH_A =
            "(firstOrder LIKE '%數A%' OR secondOrder LIKE '%數A%' OR thirdOrder LIKE '%數A%' OR fourthOrder LIKE '%數A%' OR fifthOrder LIKE '%數A%')";
    private static final String HAS_MATH_B =
            "(firstOrder LIKE '%數B%' OR secondOrder LIKE '%數B%' OR thirdOrder LIKE '%數B%' OR fourthOrder LIKE '%數B%' OR fifthOrder LIKE '%數B%')";
    private static final String HAS_SOCIAL =
            "(firstOrder LIKE '%社%' OR secondOrder LIKE '%社%' OR thirdOrder LIKE '%社%' OR fourthOrder LIKE '%社%' OR fifthOrder LIKE '%社%')";
    private static final String HAS_NATURE =
            "(firstOrder LIKE '%自然%' OR secondOrder LIKE '%自然%' OR thirdOrder LIKE '%自然%' OR fourthOrder LIKE '%自然%' OR fifthOrder LIKE '%自然%')";
    // 優先歸一類組的科系名稱關鍵字
    private static final String IS_FIRST_GROUP_DEPT =
            "(department LIKE '%金融%' OR department LIKE '%經濟%' OR department LIKE '%財務%' OR department LIKE '%會計%')";

    private Cursor getFirstGroupCursor(String year) {
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String[] columns = {"school", "year", "department", "firstOrder", "firstGrade", "secondOrder", "secondGrade",
                "thirdOrder", "thirdGrade", "fourthOrder", "fourthGrade", "fifthOrder", "fifthGrade", "people"};
        // 金融/經濟/財務/會計科系優先歸一類；數B → 一類；無數A且有社會 → 一類
        String selection = "year=? AND (" + IS_FIRST_GROUP_DEPT +
                " OR " + HAS_MATH_B +
                " OR (NOT " + HAS_MATH_A + " AND " + HAS_SOCIAL + "))";
        String[] selectionArgs = {year};
        return database.query(_tableName, columns, selection, selectionArgs, null, null, null);
    }

    private Cursor getSecondGroupCursor(String year) {
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String[] columns = {"school", "year", "department", "firstOrder", "firstGrade", "secondOrder", "secondGrade",
                "thirdOrder", "thirdGrade", "fourthOrder", "fourthGrade", "fifthOrder", "fifthGrade", "people"};
        // 金融/經濟/財務/會計科系排除；數A → 二三類；無數B且有自然 → 二三類
        String selection = "year=? AND NOT " + IS_FIRST_GROUP_DEPT +
                " AND (" + HAS_MATH_A +
                " OR (NOT " + HAS_MATH_B + " AND " + HAS_NATURE + "))";
        String[] selectionArgs = {year};
        return database.query(_tableName, columns, selection, selectionArgs, null, null, null);
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
