package com.feveral.seniorhigh.utility;

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

    public static boolean isFuzzyMatch(String text, String keyWord) {
        int start = 0;
        for (int i = 0; i < keyWord.length(); i++) {
            int index = text.indexOf(keyWord.charAt(i), start);
            if (index < 0) {
                return false;
            }
            start = index + 1;
        }
        return true;
    }
}
