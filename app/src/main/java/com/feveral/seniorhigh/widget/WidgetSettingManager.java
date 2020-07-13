package com.feveral.seniorhigh.widget;


import android.content.Context;
import android.content.SharedPreferences;

import com.feveral.seniorhigh.Config;

import static android.content.Context.MODE_PRIVATE;

public class WidgetSettingManager {

    private SharedPreferences localSaver;
    private final String MODE = "mode";

    public WidgetSettingManager(Context context) {
        localSaver = context.getSharedPreferences("widget", MODE_PRIVATE);
    }

    public void changeMode(String mode) {
        if (mode.equals(Config.TABLE_NAME_DESIGNATED) ||
                mode.equals(Config.TABLE_NAME_BASIC) ||
                mode.equals(Config.TABLE_NAME_UNIFY)) {
            localSaver.edit().putString(MODE, mode).commit();
        }
    }

    public String getMode() {
        return localSaver.getString(MODE, Config.TABLE_NAME_DESIGNATED);
    }


}
