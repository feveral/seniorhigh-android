package com.feveral.seniorhigh.utility;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Arrays;

public class AdUtility {

    public static void initialize(Context context) {
        MobileAds.initialize(context, initializationStatus -> {});
    }

    public static void loadAd(AdView adView) {
        adView.post(() -> {
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        });
    }

    public static void setTestDeviceIds() {
        RequestConfiguration config = new RequestConfiguration.Builder()
                .setTestDeviceIds(Arrays.asList("B3EEABB8EE11C2BE770B684D95219ECB"))
                .build();
        MobileAds.setRequestConfiguration(config);
    }
}
