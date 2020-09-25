package com.feveral.seniorhigh;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.Random;

public class AdManager {

    Context _context;
    InterstitialAd _interstitialAd;
    boolean _isInterstitialAdLoaded = false;
    String _testDeviceId = "6633B1331969C9F2E7AF6635A1445304";

    public AdManager(Context context) {
        this._context = context;
        MobileAds.initialize(context, context.getResources().getString(R.string.ad_application_id)); //AdMob 應用程式編號
    }

    public void loadInterstitialAd() {
        this._interstitialAd = new InterstitialAd(_context);
        this._interstitialAd.setAdUnitId(_context.getResources().getString(R.string.ad_interstitial_id));
        this._interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                _isInterstitialAdLoaded = true;
            }
        });
        this._interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(_testDeviceId).build());
    }

    public void loadNativeAd() {
        final AdLoader adLoader = new AdLoader.Builder(_context, _context.getResources().getString(R.string.native_ad_id))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Handle the failure by logging, altering the UI, and so on.
                        Log.i("onAdFailedToLoad", Integer.toString(errorCode));
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();
        adLoader.loadAds(new AdRequest.Builder().build(), 3);
    }

    public void showInterstitialAd() {
        if (this._interstitialAd.isLoaded()) {
            this._interstitialAd.show();
        } else {
            Log.d("AdInformation", "Interstitial Ad is not ready");
        }
    }

    public void showDelayedInterstitialAd() {
        int delayTime = 7000;
        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    showInterstitialAd();
                }
            },delayTime);
    }
}
