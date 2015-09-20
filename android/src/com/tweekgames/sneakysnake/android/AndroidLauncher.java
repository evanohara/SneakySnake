package com.tweekgames.sneakysnake.android;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.tweekgames.sneakysnake.SneakySnakeGame;
import com.tweekgames.sneakysnake.util.ActionResolver;

public class AndroidLauncher extends AndroidApplication implements ActionResolver {

    private static final String AD_UNIT_ID_BANNER = "ca-app-pub-6916351754834612/9855033027";
    private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-3940256099942544/6300978111";
    private static final String GOOGLE_PLAY_URL = "https://play.google.com/store/apps/developer?id=TheInvader360";
    protected AdView adView;
    protected View gameView;
    private InterstitialAd interstitialAd;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        //initialize(new SneakySnakeGame(), config);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        RelativeLayout layout = new RelativeLayout(this);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);

        AdView admobView = createAdView();
        layout.addView(admobView);
        View gameView = createGameView(config);
        layout.addView(gameView);

        setContentView(layout);
        startAdvertising(admobView);


        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(AD_UNIT_ID_INTERSTITIAL);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Finished Loading Interstitial",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded() {
                Toast.makeText(getApplicationContext(), "Closed Interstitial",
                        Toast.LENGTH_SHORT).show();
            }
        });


	}

    private AdView createAdView() {
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(AD_UNIT_ID_BANNER);
        adView.setId(R.id.terrain);
        LayoutParams params =
                new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        adView.setLayoutParams(params);
        adView.setBackgroundColor(Color.BLACK);
        return adView;
    }

    private View createGameView(AndroidApplicationConfiguration cfg) {
        gameView = initializeForView(new SneakySnakeGame(this), cfg);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.BELOW, adView.getId());
        gameView.setLayoutParams(params);
        return gameView;
    }

    private void startAdvertising(AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }


    @Override
    public void showOrLoadInterstitial() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                        Toast.makeText(getApplicationContext(),"Showing Interstitial", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        AdRequest interstitialRequest = new AdRequest.Builder().build();
                        interstitialAd.loadAd(interstitialRequest);
                        Toast.makeText(getApplicationContext(),"Loading Interstitial", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e){

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) adView.resume();
    }

    @Override
    public void onPause() {
        if (adView != null)
            adView.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (adView != null)
            adView.destroy();
        super.onDestroy();
    }

//    @Override
//    public void onBackPressed() {
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        LinearLayout ll = new LinearLayout(this);
//        ll.setOrientation(LinearLayout.VERTICAL);
//
//        Button b1 = new Button(this);
//        b1.setText("Quit");
//        b1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        ll.addView(b1);
//
//        Button b2 = new Button(this);
//        b2.setText("TheInvader360");
//        b2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_URL)));
//                dialog.dismiss();
//            }
//        });
//        ll.addView(b2);
//
//        dialog.setContentView(ll);
//        dialog.show();
//    }
}
