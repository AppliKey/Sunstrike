package com.applikeysolutions.sunstrike.animation;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.support.annotation.ColorRes;

import com.applikeysolutions.sunstrike.view.CustomSeekBar;

public class ColorAnimation extends BaseAnimation {
    private CustomSeekBar customSeekBar;
    @ColorRes
    private int startColor;
    @ColorRes
    private int endColor;

    public ColorAnimation(CustomSeekBar customSeekBar) {
        this.customSeekBar = customSeekBar;
    }

    public void startAnimation(@ColorRes int startColor, @ColorRes int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        onStartAnimation();
    }

    @Override
    void onStartAnimation() {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
        colorAnimation.setDuration(SHORT_ANIMATION_DURATION);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                customSeekBar.getThumb().setTint((int) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }
}
