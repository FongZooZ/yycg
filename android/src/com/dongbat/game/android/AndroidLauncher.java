package com.dongbat.game.android;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.dongbat.game.YCG;
import com.dongbat.game.ad.AdProvider;
import com.dongbat.game.util.AdUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication implements AdProvider {
    private static final String AD_UNIT_ID = "ca-app-pub-8112894826901791/3921073665";
    private YCG game;
    private AdView admobView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        RelativeLayout layout = new RelativeLayout(this);
        game = new YCG();
        AdUtil.setProvider(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        View gameView = initializeForView(game, config);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        layout.addView(gameView);
        admobView = createAdView();
        RelativeLayout.LayoutParams adParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layout.addView(admobView, adParams);
        setContentView(layout);
        startAdvertising(admobView);
    }

    private void startAdvertising(AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(
                "B3E056B0616487FEA0CA11C4AA91340C").build();
        adView.loadAd(adRequest);
    }

    private AdView createAdView() {
        final AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(AD_UNIT_ID);
        return adView;
    }

    @Override
    public void showAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                admobView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                admobView.setVisibility(View.GONE);
            }
        });
    }
}
