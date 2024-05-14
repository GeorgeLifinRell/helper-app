package com.example.helperapp.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.helperapp.R;

public class LottieProgressBar extends FrameLayout {
    private LottieAnimationView lottieAnimationView;
    public LottieProgressBar(@NonNull Context context) {
        super(context);
        init(context);
    }
    public LottieProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LottieProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_lottie_progress_bar, this, true);
        lottieAnimationView = findViewById(R.id.lottie_animation_view);
        lottieAnimationView.setAnimation(R.raw.loading_hand_lottie_anim);
        lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
    }

    public void playAnimation() {
        lottieAnimationView.playAnimation();
    }

    public void stopAnimation() {
        lottieAnimationView.cancelAnimation();
    }
}
