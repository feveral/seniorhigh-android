package com.feveral.seniorhigh.widget;

import android.util.Log;
import android.widget.Toast;

import com.feveral.seniorhigh.Config;

import java.util.Date;

public class CountDownManager {

    private String _examine;
    private Date _examineDate;

    public CountDownManager(String examine) {
        this._examine = examine;
        this.setExamineDate();
    }

    private void setExamineDate() {
        if (this._examine.equals(Config.TABLE_NAME_DESIGNATED)) {
            this._examineDate = new Date(Config.DESIGNATED_EXAMINE_YEAR-1900,
                                        Config.DESIGNATED_EXAMINE_MONTH-1,
                                                Config.DESIGNATED_EXAMINE_DAY);
        } else if (this._examine.equals(Config.TABLE_NAME_BASIC)) {
            this._examineDate = new Date(Config.BASIC_EXAMINE_YEAR-1900,
                                        Config.BASIC_EXAMINE_MONTH-1,
                                                Config.BASIC_EXAMINE_DAY);
        } else if (this._examine.equals(Config.TABLE_NAME_UNIFY)) {
            this._examineDate = new Date(Config.UNIFY_EXAMINE_YEAR-1900,
                                        Config.UNIFY_EXAMINE_MONTH-1,
                                                Config.UNIFY_EXAMINE_DAY);
        }
    }

    public int remainDays() {
        long todayTimestamp = new Date().getTime();
        long examineTimestamp = this._examineDate.getTime();
        return (int)( (examineTimestamp - todayTimestamp) / (86400 * 1000));
    }

    public String getRemainText() {
        String remainText = "";
        if (_examine.equals(Config.TABLE_NAME_DESIGNATED)) {
            remainText = "指考";
        } else if (_examine.equals(Config.TABLE_NAME_BASIC)) {
            remainText = "學測";
        } else if (_examine.equals(Config.TABLE_NAME_UNIFY)) {
            remainText = "統測";
        }
        remainText += (remainDays() < 0) ? "已過" : "剩下";
        remainText += " " + String.valueOf(Math.abs(remainDays())) + " 天";
        return remainText;
    }
}
