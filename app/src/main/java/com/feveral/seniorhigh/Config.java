package com.feveral.seniorhigh;

import java.util.Date;

/**
 * Created by feveral on 2018/8/20.
 */

public class Config {

    public static final String TABLE_NAME_DESIGNATED = "Designated";
    public static final String TABLE_NAME_BASIC = "Basic";
    public static final String TABLE_NAME_UNIFY = "Unify";
    public static final String TABLE_NAME_FAVORITE = "Favorite";

    private static final String FIRST_LATEST_YEAR = "108";
    private static final String SECOND_LATEST_YEAR = "107";

    public static final String DESIGNATED_FIRST_YEAR = "108";
    public static final String DESIGNATED_SECOND_YEAR = "107";

    public static final String BASIC_FIRST_YEAR = "109";
    public static final String BASIC_SECOND_YEAR = "108";

    public static final String UNIFY_FIRST_YEAR = "108";
    public static final String UNIFY_SECOND_YEAR = "107";

    public static final int DESIGNATED_EXAMINE_YEAR = 2021;
    public static final int DESIGNATED_EXAMINE_MONTH = 7; // 1 for January, 2 for February, ...
    public static final int DESIGNATED_EXAMINE_DAY = 1;

    public static final int BASIC_EXAMINE_YEAR = 2021;
    public static final int BASIC_EXAMINE_MONTH = 1; // 1 for January, 2 for February, ...
    public static final int BASIC_EXAMINE_DAY = 23;

    public static final int UNIFY_EXAMINE_YEAR = 2021;
    public static final int UNIFY_EXAMINE_MONTH = 5; // 1 for January, 2 for February, ...
    public static final int UNIFY_EXAMINE_DAY = 1;

    public static final String USER_DB_FILE_NAME = "user.db";
    public static final int USER_DB_VERSION = 1;

    private static final String dataBaseFileName = "grade_2020_0712.db";
    private static final int databaseResourceId = R.raw.grade_2020_0712;

    public static String getFirstLatestYear(){
        return FIRST_LATEST_YEAR;
    }
    public static String getSecondLatestYear(){
        return SECOND_LATEST_YEAR;
    }
    public static String getDataBaseFileName(){
        return dataBaseFileName;
    }
    public static int getDatabaseResourceId(){
        return databaseResourceId;
    }
}
