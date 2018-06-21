package com.xolider.shipfighter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.xolider.shipfighter.utils.AdController;

public class AndroidLauncher extends AndroidApplication {

	private InterstitialAd mAd;
	private boolean isAdReady = false;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MobileAds.initialize(this, "ca-app-pub-7855135049708723~2972083867");

		mAd = new InterstitialAd(this);
		mAd.setAdUnitId("ca-app-pub-7855135049708723/7649695470");
		mAd.loadAd(new AdRequest.Builder().build());
		mAd.setAdListener(new AdListener() {

		    @Override
            public void onAdLoaded() {
		        isAdReady = true;
            }

            @Override
            public void onAdClosed() {
		        isAdReady = false;
		        mAd.loadAd(new AdRequest.Builder().build());
            }

        });

        AdController controller = new AdController() {
            @Override
            public void showAd() {
                if(isAdReady) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAd.show();
                        }
                    });
                }
            }
        };

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = true;
		config.hideStatusBar = true;
		config.useImmersiveMode = true;
		initialize(new ShipFighterGame(controller), config);
	}
}
