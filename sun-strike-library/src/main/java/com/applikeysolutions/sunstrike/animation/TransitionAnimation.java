package com.applikeysolutions.sunstrike.animation;

import android.animation.ObjectAnimator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.Interpolator;

public class TransitionAnimation extends BaseAnimation {

    private View view;
    private float startPoint;
    private float endPoint;
    private AnimationType animationType;
    private Interpolator interpolator;

    public enum AnimationType {
        TRANSLATION_Y("translationY"),
        TRANSLATION_Z("translationZ"),
        SCALE_X("scaleX"),
        SCALE_Y("scaleY");

        private final String text;

        AnimationType(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public TransitionAnimation(View view) {
        this.view = view;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public void startAnimation(float startPoint, float endPoint, AnimationType animationType) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.animationType = animationType;
        onStartAnimation();
    }

    @Override
    void onStartAnimation() {
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, animationType.toString(), startPoint, endPoint);
        if (interpolator != null) {
            objectAnimator.setInterpolator(interpolator);
        } else {
            objectAnimator.setInterpolator(new FastOutSlowInInterpolator());
        }
        objectAnimator.setDuration(LONG_ANIMATION_DURATION);
        objectAnimator.start();
    }
}
