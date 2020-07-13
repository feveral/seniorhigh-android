package com.feveral.seniorhigh.Model;

/**
 * Created by feveral on 2018/8/22.
 */

public class BaseGrade {

    protected String school = "";
    protected String department = "";

    public BaseGrade(String school, String department){
        this.school = school;
        this.department = department;
    }

    public BaseGrade(){

    }

    public String getSchool() {
        return school;
    }

    public String getDepartment() {
        return department;
    }
}
