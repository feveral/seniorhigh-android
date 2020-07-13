package com.feveral.seniorhigh.animation;

import androidx.recyclerview.widget.DefaultItemAnimator;

/**
 * Created by feveral on 2017/8/16.
 */

public class MyAnimation {

    public static DefaultItemAnimator getDefaultAnimator(){
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(250);
        defaultItemAnimator.setRemoveDuration(250);
        return defaultItemAnimator;
    }
}
