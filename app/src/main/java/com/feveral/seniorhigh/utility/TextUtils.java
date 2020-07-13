package com.feveral.seniorhigh.utility;

/**
 * Created by feveral on 2017/8/14.
 */

public class TextUtils {

    public static String shortStringIfTooLong(String departmentName){
        String newName;
        if(departmentName.length() > 12)
            newName = departmentName.substring(0, 12) + "...";
        else
            return departmentName;
        return newName;
    }

    public static String addPlusToSubjectSet(String subjectSet){
        String newString = "";
        for(int i=0;i<subjectSet.length();i++){
            newString += subjectSet.substring(i,i+1);
            if(! (i==subjectSet.length()-1) )
                newString += "+" ;
        }
        return newString;
    }
}
