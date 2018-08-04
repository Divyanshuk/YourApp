package com.example.divyanshukumar.yourapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.divyanshukumar.yourapp.data.SharedPrefClass;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AdPopUp extends AppCompatActivity implements RewardedVideoAdListener {


    private RewardedVideoAd mRewardedVideoAd;

//    boolean seenAd = false;

//    public PopUp(boolean mSeenAd){
//
//        seenAd = mSeenAd;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_pop_up);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544/5224354917");

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;

        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());


        Button reward_button = (Button) findViewById(R.id.reward_button);

        reward_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }

            }
        });
    }


    @Override
    public void onRewardedVideoAdLoaded() {

        Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRewardedVideoAdClosed() {
        Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
//
//        if(seenAd == true){
//            enabled.setText("DarkTheme Enabled");
//        }
//
//        else{
//            enabled.setText("darkTheme not enabled");
//        }

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        Toast.makeText(this, rewardItem.getType(), Toast.LENGTH_LONG).show();
//        seenAd = true;

//        PreferenceClass preferenceClass = new PreferenceClass(getApplicationContext()).setDefaults("seenAds", true);

        SharedPrefClass sharedPrefClass = new SharedPrefClass(getApplicationContext());

        sharedPrefClass.setDefaults("seenAd", true);

//        Intent mainIntent = new Intent(AdPopUp.this, MainActivity.class);
//        startActivity(mainIntent);

//        Toast.makeText(this, "Turned on dark theme", Toast.LENGTH_LONG).show();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        Intent i = new Intent(AdPopUp.this,MainActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(this, "onRewardedVideoAdLeftApplication",
                Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();

    }

}
