package com.feveral.seniorhigh.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.feveral.seniorhigh.database.GradeDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by feveral on 2017/7/25.
 */

public class DesignatedGrade  extends BaseGrade {
    private String year = "";
    private String department = "";
    private double chinese = 0;
    private double english = 0;
    private double mathAdvance = 0;
    private double mathBasic = 0;
    private double mathA = 0;
    private double mathB = 0;
    private double physical = 0;
    private double chemistry = 0;
    private double biological = 0;
    private double geography = 0;
    private double history = 0;
    private double citizen = 0;
    private double society = 0;
    private double nature = 0;
    private double skill = 0;
    private double allGrade = 0;
    private int people = 0;

    public DesignatedGrade(String year , String school , String department){
        this.year = year;
        this.school = school;
        this.department = department;
    }

    public DesignatedGrade(String school , String department){
        super(school,department);
    }

    public DesignatedGrade(){

    }

    public void setChinese(double chinese) {
        this.chinese = chinese;
    }

    public void setEnglish(double english) {
        this.english = english;
    }

    public void setMathA(double mathA) {
        this.mathA = mathA;
    }

    public void setMathB(double mathB) {
        this.mathB = mathB;
    }

    public void setMathAdvance(double mathAdvance) {
        this.mathAdvance = mathAdvance;
    }

    public void setMathBasic(double mathBasic) {
        this.mathBasic = mathBasic;
    }

    public void setPhysical(double physical) {
        this.physical = physical;
    }

    public void setChemistry(double chemistry) {
        this.chemistry = chemistry;
    }

    public void setBiological(double biological) {
        this.biological = biological;
    }

    public void setGeography(double geography) {
        this.geography = geography;
    }

    public void setHistory(double history) {
        this.history = history;
    }

    public void setCitizen(double citizen) {
        this.citizen = citizen;
    }

    public void setSkill(double skill){
        this.skill = skill;
    }

    public void setAllGrade(double allGrade){
        this.allGrade = allGrade;
    }

    public void setPeople(int people){
        this.people = people;
    }


    public String getDepartment() {
        return department;
    }

    public String getYear() {
        return year;
    }

    public double getChinese() {
        return chinese;
    }

    public double getEnglish() {
        return english;
    }

    public double getMathA() {
        return mathA;
    }

    public double getMathB() {
        return mathB;
    }

    public double getPhysical() {
        return physical;
    }

    public double getChemistry() {
        return chemistry;
    }

    public double getBiological() {
        return biological;
    }

    public double getGeography() {
        return geography;
    }

    public double getHistory() {
        return history;
    }

    public double getCitizen() {
        return citizen;
    }

    public void setSociety(double society) {
        this.society = society;
    }

    public void setNature(double nature) {
        this.nature = nature;
    }

    public double getSociety() {
        return society;
    }

    public double getNature() {
        return nature;
    }

    public double getSkill() {
        return skill;
    }

    public double getAllGrade() {
        return allGrade;
    }

    public int getPeople() {
        return people;
    }

    public double getBalance(){
        double weight = chinese + english + mathAdvance + mathBasic +
                        mathA + mathB + physical + chemistry + biological +
                        geography + history + citizen + society + nature + skill;
        return allGrade / weight ;
    }

    public String getYearString(){
        return "----- " + year + "年度 -----";
    }

    public String getWeightString(){
        String weightString = "";
        String chineseString = "國 " + String.valueOf(chinese) + " ";
        String englishString = "英 " + String.valueOf(english)+ " ";
        String mathAdvanceString = "數甲 " + String.valueOf(mathAdvance)+ " ";
        String mathBasicString = "數乙 " + String.valueOf(mathBasic)+ " ";
        String mathAString = "數A " + String.valueOf(mathA)+ " ";
        String mathBString = "數乙 " + String.valueOf(mathB)+ " ";
        String physicalString = "物 " + String.valueOf(physical)+ " ";
        String chemistryString = "化 " + String.valueOf(chemistry)+ " ";
        String biologicalString = "生 " + String.valueOf(biological)+ " ";
        String geographyString = "地 " + String.valueOf(geography)+ " ";
        String historyString = "歷 " + String.valueOf(history)+ " ";
        String citizenString = "公 " + String.valueOf(citizen)+ " ";
        String societyString = "公 " + String.valueOf(society)+ " ";
        String natureString = "公 " + String.valueOf(nature)+ " ";
        String skillString = "術 " + String.valueOf(skill)+ " ";

        if(chinese!=0)
            weightString += chineseString;
        if(english!=0)
            weightString += englishString;
        if(mathAdvance != 0)
            weightString += mathAdvanceString;
        if(mathBasic != 0)
            weightString += mathBasicString;
        if(mathA!=0)
            weightString += mathAString;
        if(mathB!=0)
            weightString += mathBString;
        if(physical!=0)
            weightString += physicalString;
        if(chemistry!=0)
            weightString += chemistryString;
        if(biological!=0)
            weightString += biologicalString;
        if(geography!=0)
            weightString += geographyString;
        if(history!=0)
            weightString += historyString;
        if(citizen!=0)
            weightString += citizenString;
        if(society!=0)
            weightString += societyString;
        if(nature!=0)
            weightString += natureString;
        if(skill!=0)
            weightString += skillString;
        return weightString;
    }

    public String getBalanceString(){
        return String.format("%.2f",getBalance());
    }

    public String getPeopleString(){
        return String.valueOf(getPeople());
    }

    public static ArrayList<DesignatedGrade> sortHighToLow(ArrayList<DesignatedGrade> notSort){

        ArrayList<DesignatedGrade> sorted = notSort;
        Collections.sort(sorted, new Comparator<DesignatedGrade>() {
            @Override
            public int compare(DesignatedGrade grade1, DesignatedGrade grade2) {
                if (grade1.getBalance() < grade2.getBalance())
                    return 1;
                else if(grade1.getBalance() > grade2.getBalance())
                    return -1;
                else
                    return 0;
            }
        });
        return sorted;
    }

    public static DesignatedGrade cursorToGrade(Cursor cursor){
        DesignatedGrade grade = new DesignatedGrade(
                cursor.getString(cursor.getColumnIndex("year"))
                , cursor.getString(cursor.getColumnIndex("school"))
                , cursor.getString(cursor.getColumnIndex("department")));
        grade.setChinese(cursor.getDouble(cursor.getColumnIndex("chinese")));
        grade.setEnglish(cursor.getDouble(cursor.getColumnIndex("english")));
        grade.setMathAdvance(cursor.getDouble(cursor.getColumnIndex("mathAdvance")));
        grade.setMathBasic(cursor.getDouble(cursor.getColumnIndex("mathBasic")));
        grade.setMathA(cursor.getDouble(cursor.getColumnIndex("mathA")));
        grade.setMathB(cursor.getDouble(cursor.getColumnIndex("mathB")));
        grade.setPhysical(cursor.getDouble(cursor.getColumnIndex("physical")));
        grade.setChemistry(cursor.getDouble(cursor.getColumnIndex("chemistry")));
        grade.setBiological(cursor.getDouble(cursor.getColumnIndex("biological")));
        grade.setGeography(cursor.getDouble(cursor.getColumnIndex("geography")));
        grade.setHistory(cursor.getDouble(cursor.getColumnIndex("history")));
        grade.setCitizen(cursor.getDouble(cursor.getColumnIndex("citizen")));
        grade.setSociety(cursor.getDouble(cursor.getColumnIndex("society")));
        grade.setNature(cursor.getDouble(cursor.getColumnIndex("nature")));
        grade.setSkill(cursor.getDouble(cursor.getColumnIndex("skill")));
        grade.setAllGrade(cursor.getDouble(cursor.getColumnIndex("allGrade")));
        grade.setPeople(cursor.getInt(cursor.getColumnIndex("people")));
        return grade;
    }


    public static ArrayList<DesignatedGrade> findByKeyWord(String year, String keyWord) {
        if (keyWord.length() == 0) {
            return new ArrayList<>();
        }
        SQLiteDatabase database = GradeDatabase.getDatabase();
        String keyWordPattern = "%";
        for(int i = 0 ; i < keyWord.length(); i++) {
            keyWordPattern = keyWordPattern + (keyWord.charAt(i) + "%");
        }
        Cursor cursor = database.rawQuery("SELECT * FROM Designated WHERE year = ? AND (school LIKE ? OR department LIKE ?)",
                new String[] {year, keyWordPattern, keyWordPattern});
        ArrayList<DesignatedGrade> designatedGradeList = new ArrayList<>();
        while(cursor.moveToNext()) {
            DesignatedGrade grade = DesignatedGrade.cursorToGrade(cursor);
            designatedGradeList.add(grade);
        }
        return designatedGradeList;
    }

    public static DesignatedGrade find(String year, String school, String department) {
        SQLiteDatabase database = GradeDatabase.getDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Designated WHERE year = ? AND school = ? AND department = ?",
                new String[] {year, school, department});
        while(cursor.moveToNext()) {
            return DesignatedGrade.cursorToGrade(cursor);
        }
        return new DesignatedGrade();
    }
}
