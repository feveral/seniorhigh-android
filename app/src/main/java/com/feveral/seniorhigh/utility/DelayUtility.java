package com.feveral.seniorhigh.utility;

import android.os.Handler;
import android.os.Looper;

public class DelayUtility {

    public static void delay(Integer second, Runnable runnable) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            runnable.run();
        }, second * 1000);
    }
}
