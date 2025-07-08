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
}
