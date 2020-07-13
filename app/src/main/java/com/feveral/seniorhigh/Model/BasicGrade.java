package com.feveral.seniorhigh.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.feveral.seniorhigh.database.GradeDatabase;

import java.util.ArrayList;

/**
 * Created by feveral on 2017/8/15.
 */

public class BasicGrade{

    private String _id = "";
    private String school = "";
    private String year = "";
    private String department = "";
    private String firstOrder = "";
    private int firstGrade = 0;
    private String secondOrder = "";
    private int secondGrade = 0;
    private String thirdOrder = "";
    private int thirdGrade = 0;
    private String forthOrder = "";
    private int forthGrade = 0;
    private String fifthOrder = "";
    private int fifthGrade = 0;
    private int people = 0;


    public BasicGrade(String id , String year , String school , String department){
        this._id = id;
        this.year = year;
        this.school = school;
        this.department = department;
    }

    public BasicGrade(){

    }

    public void setPeople(int people) {
        this.people = people;
    }

    public void setFirstOrder(String firstOrder) {
        this.firstOrder = firstOrder;
    }

    public void setSecondOrder(String secondOrder) {
        this.secondOrder = secondOrder;
    }

    public void setThirdOrder(String thirdOrder) {
        this.thirdOrder = thirdOrder;
    }

    public void setForthOrder(String forthOrder) {
        this.forthOrder = forthOrder;
    }

    public void setFifthOrder(String fifthOrder) {
        this.fifthOrder = fifthOrder;
    }

    public void setFirstGrade(int firstGrade) {
        this.firstGrade = firstGrade;
    }

    public void setSecondGrade(int secondGrade) {
        this.secondGrade = secondGrade;
    }

    public void setThirdGrade(int thirdGrade) {
        this.thirdGrade = thirdGrade;
    }

    public void setForthGrade(int forthGrade) {
        this.forthGrade = forthGrade;
    }

    public void setFifthGrade(int fifthGrade) {
        this.fifthGrade = fifthGrade;
    }

    public String getSchool() {
        return school;
    }

    public String get_id() {
        return _id;
    }

    public String getDepartment() {
        return department;
    }

    public int getFirstGrade() {
        return firstGrade;
    }

    public String getFirstOrder() {
        return firstOrder;
    }

    public int getFifthGrade() {
        return fifthGrade;
    }

    public int getSecondGrade() {
        return secondGrade;
    }

    public String getSecondOrder() {
        return secondOrder;
    }

    public String getYear() {
        return year;
    }

    public String getThirdOrder() {
        return thirdOrder;
    }

    public String getFifthOrder() {
        return fifthOrder;
    }

    public int getForthGrade() {
        return forthGrade;
    }

    public String getForthOrder() {
        return forthOrder;
    }

    public int getThirdGrade() {
        return thirdGrade;
    }

    public int getPeople() {
        return people;
    }

    public static BasicGrade cursorToGrade(Cursor cursor){
        BasicGrade grade = new BasicGrade(
                cursor.getString(cursor.getColumnIndex("_id"))
                , cursor.getString(cursor.getColumnIndex("year"))
                , cursor.getString(cursor.getColumnIndex("school"))
                , cursor.getString(cursor.getColumnIndex("department")));
        grade.setFirstOrder(cursor.getString(cursor.getColumnIndex("firstOrder")));
        grade.setFirstGrade(cursor.getInt(cursor.getColumnIndex("firstGrade")));
        grade.setSecondOrder(cursor.getString(cursor.getColumnIndex("secondOrder")));
        grade.setSecondGrade(cursor.getInt(cursor.getColumnIndex("secondGrade")));
        grade.setThirdOrder(cursor.getString(cursor.getColumnIndex("thirdOrder")));
        grade.setThirdGrade(cursor.getInt(cursor.getColumnIndex("thirdGrade")));
        grade.setForthOrder(cursor.getString(cursor.getColumnIndex("forthOrder")));
        grade.setForthGrade(cursor.getInt(cursor.getColumnIndex("forthGrade")));
        grade.setFifthOrder(cursor.getString(cursor.getColumnIndex("fifthOrder")));
        grade.setFifthGrade(cursor.getInt(cursor.getColumnIndex("fifthGrade")));
        grade.setPeople(cursor.getInt(cursor.getColumnIndex("people")));
        return grade;
    }

    public static ArrayList<BasicGrade> findByKeyWord(String year, String keyWord) {
        if (keyWord.length() == 0) {
            return new ArrayList<>();
        }
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String keyWordPattern = "%";
        for(int i = 0 ; i < keyWord.length(); i++) {
            keyWordPattern = keyWordPattern + (keyWord.charAt(i) + "%");
        }
        Cursor cursor = database.rawQuery("SELECT * FROM Basic WHERE year = ? AND (school LIKE ? OR department LIKE ?)", new String[] {year, keyWordPattern, keyWordPattern});
        ArrayList<BasicGrade> basicGradeList = new ArrayList<>();
        while(cursor.moveToNext()) {
            BasicGrade grade = BasicGrade.cursorToGrade(cursor);
            basicGradeList.add(grade);
        }
        return basicGradeList;
    }

    public static BasicGrade find(String year, String school, String department) {
        SQLiteDatabase database = GradeDatabase.getDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Basic WHERE year = ? AND school = ? AND department = ?",
                new String[] {year, school, department});
        while(cursor.moveToNext()) {
            return BasicGrade.cursorToGrade(cursor);
        }
        return new BasicGrade();
    }
}
