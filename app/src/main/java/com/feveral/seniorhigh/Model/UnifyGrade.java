package com.feveral.seniorhigh.Model;

/**
 * Created by feveral on 2017/8/16.
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.feveral.seniorhigh.database.GradeDatabase;
import com.feveral.seniorhigh.unify.UnifyFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UnifyGrade {
    private String _id = "";
    private String year = "";
    private String departmentGroup = "";
    private String school = "";
    private String department = "";
    private double grade;

    public UnifyGrade(String id , String year , String school , String department){
        this._id = id;
        this.year = year;
        this.school = school;
        this.department = department;
    }

    public UnifyGrade(){

    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public void setDepartmentGroup(String departmentGroup){
        this.departmentGroup = departmentGroup;
    }

    public String get_id() {
        return _id;
    }

    public String getYear() {
        return year;
    }

    public String getSchool() {
        return school;
    }

    public String getDepartment() {
        return department;
    }

    public String getDepartmentGroup() {
        return departmentGroup;
    }

    public double getGrade() {
        return grade;
    }

    public static ArrayList<UnifyGrade> sortHighToLow(ArrayList<UnifyGrade> notSort){

        ArrayList<UnifyGrade> sorted = notSort;
        Collections.sort(sorted, new Comparator<UnifyGrade>() {
            @Override
            public int compare(UnifyGrade grade1, UnifyGrade grade2) {
                if (grade1.getGrade() < grade2.getGrade())
                    return 1;
                else if(grade1.getGrade() > grade2.getGrade())
                    return -1;
                else
                    return 0;
            }
        });
        return sorted;
    }

    public static UnifyGrade cursorToGrade(Cursor cursor){
        UnifyGrade grade = new UnifyGrade(
                cursor.getString(cursor.getColumnIndex("_id"))
                , cursor.getString(cursor.getColumnIndex("year"))
                , cursor.getString(cursor.getColumnIndex("school"))
                , cursor.getString(cursor.getColumnIndex("department")));
        grade.setGrade(cursor.getDouble(cursor.getColumnIndex("grade")));
        grade.setDepartmentGroup(cursor.getString(cursor.getColumnIndex("departmentGroup")));
        return grade;
    }

    public static ArrayList<UnifyGrade> findByKeyWord(String year, String keyWord) {
        if (keyWord.length() == 0) {
            return new ArrayList<>();
        }
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String keyWordPattern = "%";
        for(int i = 0 ; i < keyWord.length(); i++) {
            keyWordPattern = keyWordPattern + (keyWord.charAt(i) + "%");
        }
        Cursor cursor = database.rawQuery("SELECT * FROM Unify WHERE year = ? AND (school LIKE ? OR department LIKE ?)", new String[] {year, keyWordPattern, keyWordPattern});
        ArrayList<UnifyGrade> unifyGradeList = new ArrayList<>();
        while(cursor.moveToNext()) {
            UnifyGrade grade = UnifyGrade.cursorToGrade(cursor);
            unifyGradeList.add(grade);
        }
        UnifyGrade.sortHighToLow(unifyGradeList);
        return unifyGradeList;
    }

    public static ArrayList<String> findDepartments(String year, String school) {
        SQLiteDatabase database = GradeDatabase.getDatabase();
        Cursor cursor = database.rawQuery("SELECT DISTINCT department FROM Unify WHERE year = ? AND school = ?",
                new String[] {year, school});
        ArrayList<String> departmentList = new ArrayList<>();
        while(cursor.moveToNext()) {
            departmentList.add(cursor.getString(cursor.getColumnIndex("department")));
        }
        return departmentList;
    }

    public static ArrayList<UnifyGrade> find(String year, String school, String department) {
        SQLiteDatabase database = GradeDatabase.getDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Unify WHERE year = ? AND school = ? AND department = ?",
                new String[] {year, school, department});
        ArrayList<UnifyGrade> gradeList = new ArrayList<>();
        while(cursor.moveToNext()) {
            gradeList.add(UnifyGrade.cursorToGrade(cursor));
        }
        return gradeList;
    }

    public static UnifyGrade find(String year, String school, String department, String groupName) {
        SQLiteDatabase database = GradeDatabase.getDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Unify WHERE year = ? AND school = ? AND department = ? AND departmentGroup= ?",
                new String[] {year, school, department, groupName});
        while(cursor.moveToNext()) {
            return UnifyGrade.cursorToGrade(cursor);
        }
        return new UnifyGrade();
    }
}

