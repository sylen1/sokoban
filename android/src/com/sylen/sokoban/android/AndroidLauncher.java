package com.sylen.sokoban.android;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.sylen.sokoban.MySokoban;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new MySokoban(), config);
		
		// Create the layout
        RelativeLayout layout = new RelativeLayout(this);

        // Do the stuff that initialize() would do for you
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        // Create the libgdx View
        View gameView = initializeForView(new MySokoban(),config);

        // Create and setup the AdMob view
        AdView adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-5349485631786323/7514318091");
        adView.setAdSize(AdSize.BANNER);
        adView.loadAd(new AdRequest.Builder()
        .build());

        // Add the libgdx view
        layout.addView(gameView);

        // Add the AdMob view
        RelativeLayout.LayoutParams adParams = 
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
            adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        layout.addView(adView, adParams);

        // Hook it all up
        setContentView(layout);
	}
}
