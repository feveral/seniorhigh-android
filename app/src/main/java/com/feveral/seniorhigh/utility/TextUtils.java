package com.feveral.seniorhigh.utility;

import java.util.Set;

/**
 * Created by feveral on 2017/8/14.
 */

public class TextUtils {

    public static String shortStringIfTooLong(String departmentName){
        String newName;
        if(departmentName.length() > 17)
            newName = departmentName.substring(0, 17) + "...";
        else
            return departmentName;
        return newName;
    }

    public static String addPlusToSubjectSet(String subjectSet) {
        String result = subjectSet;
        result = result.replace("國", "國+");
        result = result.replace("數A", "數A+");
        result = result.replace("數B", "數B+");
        if (!result.contains("數A") && !result.contains("數B")) {
            result = result.replace("數", "數+");
        }
        result = result.replace("英", "英+");
        result = result.replace("社", "社+");
        result = result.replace("自", "自+");

        return !result.equals("") && result.charAt(result.length() - 1) == '+'
                ? result.substring(0, result.length() - 1)
                : result;
    }
}
