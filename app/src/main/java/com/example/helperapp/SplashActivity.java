package com.example.helperapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIMEOUT = 4000;
    private static final int GENIE_IMAGE_ANIMATION_TIME = 900;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView genieSplashIV = findViewById(R.id.genie_splash_image);
        ImageView genieLogoIV = findViewById(R.id.genie_logo_image);

        Animation slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.genie_img_slide_up);
        Animation scaleUpAnimation = AnimationUtils.loadAnimation(this, R.anim.genie_img_scale_up);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        AnimationSet slideUpScaleUpAnimSet = new AnimationSet(true);
        slideUpScaleUpAnimSet.addAnimation(slideUpAnimation);
        slideUpScaleUpAnimSet.addAnimation(scaleUpAnimation);

        genieSplashIV.startAnimation(slideUpScaleUpAnimSet);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                genieLogoIV.startAnimation(fadeInAnimation);
            }
        }, GENIE_IMAGE_ANIMATION_TIME);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_TIMEOUT);
    }
}