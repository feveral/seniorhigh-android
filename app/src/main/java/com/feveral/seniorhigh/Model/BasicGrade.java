package com.feveral.seniorhigh.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.feveral.seniorhigh.database.GradeDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by feveral on 2017/8/15.
 */

public class BasicGrade{

    private String school = "";
    private String year = "";
    private String department = "";
    private String firstOrder = "";
    private double firstGrade = 0;
    private String secondOrder = "";
    private double secondGrade = 0;
    private String thirdOrder = "";
    private double thirdGrade = 0;
    private String forthOrder = "";
    private double forthGrade = 0;
    private String fifthOrder = "";
    private double fifthGrade = 0;
    private int people = 0;


    public BasicGrade(String year , String school , String department){
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

    public void setFourthOrder(String forthOrder) {
        this.forthOrder = forthOrder;
    }

    public void setFifthOrder(String fifthOrder) {
        this.fifthOrder = fifthOrder;
    }

    public void setFirstGrade(double firstGrade) {
        this.firstGrade = firstGrade;
    }

    public void setSecondGrade(double secondGrade) {
        this.secondGrade = secondGrade;
    }

    public void setThirdGrade(double thirdGrade) {
        this.thirdGrade = thirdGrade;
    }

    public void setFourthGrade(double forthGrade) {
        this.forthGrade = forthGrade;
    }

    public void setFifthGrade(double fifthGrade) {
        this.fifthGrade = fifthGrade;
    }

    public String getSchool() {
        return school;
    }

    public String getDepartment() {
        return department;
    }

    public double getFirstGrade() {
        return firstGrade;
    }

    public String getFirstOrder() {
        return firstOrder;
    }

    public double getFifthGrade() {
        return fifthGrade;
    }

    public double getSecondGrade() {
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

    public double getFourthGrade() {
        return forthGrade;
    }

    public String getFourthOrder() {
        return forthOrder;
    }

    public double getThirdGrade() {
        return thirdGrade;
    }

    public int getPeople() {
        return people;
    }

    private static boolean isNormalSubject(String order) {
        if (order == null || order.isEmpty()) return false;
        return order.contains("國") || order.contains("英") || order.contains("數") ||
               order.contains("社") || order.contains("自");
    }

    public double getLastGrade() {
        if (fifthGrade > 0 && isNormalSubject(fifthOrder)) return fifthGrade;
        if (forthGrade > 0 && isNormalSubject(forthOrder)) return forthGrade;
        if (thirdGrade > 0 && isNormalSubject(thirdOrder)) return thirdGrade;
        if (secondGrade > 0 && isNormalSubject(secondOrder)) return secondGrade;
        if (firstGrade > 0 && isNormalSubject(firstOrder)) return firstGrade;
        return 0;
    }

    public String getLastOrder() {
        if (fifthGrade > 0 && isNormalSubject(fifthOrder)) return fifthOrder;
        if (forthGrade > 0 && isNormalSubject(forthOrder)) return forthOrder;
        if (thirdGrade > 0 && isNormalSubject(thirdOrder)) return thirdOrder;
        if (secondGrade > 0 && isNormalSubject(secondOrder)) return secondOrder;
        if (firstGrade > 0 && isNormalSubject(firstOrder)) return firstOrder;
        return "";
    }

    public int getSubjectCount() {
        int count = 0;
        if (firstGrade > 0 && isNormalSubject(firstOrder)) count++;
        if (secondGrade > 0 && isNormalSubject(secondOrder)) count++;
        if (thirdGrade > 0 && isNormalSubject(thirdOrder)) count++;
        if (forthGrade > 0 && isNormalSubject(forthOrder)) count++;
        if (fifthGrade > 0 && isNormalSubject(fifthOrder)) count++;
        return count;
    }

    private static int countSubjectsInOrder(String order) {
        if (order == null || order.isEmpty()) return 1;
        int count = 0;
        if (order.contains("國")) count++;
        if (order.contains("英")) count++;
        if (order.contains("數")) count++;
        if (order.contains("社")) count++;
        if (order.contains("自")) count++;
        return Math.max(count, 1);
    }

    public double getAverageLastGrade() {
        double sum = 0;
        int count = 0;
        if (firstGrade > 0 && isNormalSubject(firstOrder)) {
            sum += firstGrade / (double) countSubjectsInOrder(firstOrder);
            count++;
        }
        if (secondGrade > 0 && isNormalSubject(secondOrder)) {
            sum += secondGrade / (double) countSubjectsInOrder(secondOrder);
            count++;
        }
        if (thirdGrade > 0 && isNormalSubject(thirdOrder)) {
            sum += thirdGrade / (double) countSubjectsInOrder(thirdOrder);
            count++;
        }
        if (forthGrade > 0 && isNormalSubject(forthOrder)) {
            sum += forthGrade / (double) countSubjectsInOrder(forthOrder);
            count++;
        }
        if (fifthGrade > 0 && isNormalSubject(fifthOrder)) {
            sum += fifthGrade / (double) countSubjectsInOrder(fifthOrder);
            count++;
        }
        if (count == 0) return 0;
        return sum / count;
    }

    public String getNormalSubjectsString() {
        return getLastOrder().replace("+", "");
    }

    public String getLastGradeString() {
        DecimalFormat df = new DecimalFormat("0.##");
        return getNormalSubjectsString() + "\n" + df.format(getLastGrade());
    }

    public static void sortHighToLow(ArrayList<BasicGrade> list) {
        Collections.sort(list, (a, b) -> Double.compare(b.getAverageLastGrade(), a.getAverageLastGrade()));
    }

    public static BasicGrade cursorToGrade(Cursor cursor){
        BasicGrade grade = new BasicGrade(
                cursor.getString(cursor.getColumnIndex("year"))
                , cursor.getString(cursor.getColumnIndex("school"))
                , cursor.getString(cursor.getColumnIndex("department")));
        grade.setFirstOrder(cursor.getString(cursor.getColumnIndex("firstOrder")));
        grade.setFirstGrade(cursor.getDouble(cursor.getColumnIndex("firstGrade")));
        grade.setSecondOrder(cursor.getString(cursor.getColumnIndex("secondOrder")));
        grade.setSecondGrade(cursor.getDouble(cursor.getColumnIndex("secondGrade")));
        grade.setThirdOrder(cursor.getString(cursor.getColumnIndex("thirdOrder")));
        grade.setThirdGrade(cursor.getDouble(cursor.getColumnIndex("thirdGrade")));
        grade.setFourthOrder(cursor.getString(cursor.getColumnIndex("fourthOrder")));
        grade.setFourthGrade(cursor.getDouble(cursor.getColumnIndex("fourthGrade")));
        grade.setFifthOrder(cursor.getString(cursor.getColumnIndex("fifthOrder")));
        grade.setFifthGrade(cursor.getDouble(cursor.getColumnIndex("fifthGrade")));
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
