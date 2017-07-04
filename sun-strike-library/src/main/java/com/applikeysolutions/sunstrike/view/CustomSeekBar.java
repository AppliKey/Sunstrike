package com.applikeysolutions.sunstrike.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.applikeysolutions.sunstrike.SunStrikeResources;
import com.applikeysolutions.sunstrike.R;
import com.applikeysolutions.sunstrike.animation.ColorAnimation;

import android.support.v7.widget.AppCompatSeekBar;

import java.util.ArrayList;
import java.util.List;

public class CustomSeekBar extends AppCompatSeekBar {
    private final static int MAX_PROGRESS = 100;
    private final static int HALF_PROGRESS = 50;
    private final static int MIN_PROGRESS = 0;
    private final static int ANIMATION_DURATION = 200;

    private boolean isLastStateMaxProgress;

    private ColorAnimation colorAnimation;
    private SunStrikeResources sunStrikeResources;
    private List<Callback> callbackList = new ArrayList<>();

    public CustomSeekBar(Context context) {
        this(context, null);
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(sunStrikeResources.getSeekBarWidth(),
                getMeasuredHeight());
    }

    public void setCustomColor(@ColorInt int colorBackground, @ColorInt int colorProgress, @ColorInt int colorCircle) {
        ((GradientDrawable) getContext().getDrawable(R.drawable.seekbar_background)).setColor(colorBackground);

        ((GradientDrawable) getContext().getDrawable(R.drawable.seekbar_progress)).setColor(colorProgress);

        LayerDrawable bgDrawable = (LayerDrawable) getContext().getDrawable(R.drawable.circle);
        ((GradientDrawable) bgDrawable.findDrawableByLayerId(R.id.circle)).setColor(colorProgress);

        setResources();

        sunStrikeResources.setColorBackground(colorBackground);
        sunStrikeResources.setColorChecked(colorCircle);
        sunStrikeResources.setColorProgress(colorProgress);
    }

    private void setResources() {
        sunStrikeResources = new SunStrikeResources(getContext());
        setProgressDrawable(sunStrikeResources.getBackground());
        setThumb(sunStrikeResources.getThumb());
    }

    private void init() {
        colorAnimation = new ColorAnimation(this);
        setResources();

        setOnTouchListener(new OnTouchListener() {
            private final static int MOVE_ACTION_THRESHHOLD = 30;
            private float startX;
            private float startY;
            private boolean moved;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int progress = getProgress();
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if(moved || isMoving(event.getX(), event.getY())){
                            moved = true;
                            for (Callback callback : callbackList) {
                                callback.onMove(progress);
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        if(moved){
                            setProgressAndCallback(progress >= HALF_PROGRESS, progress);
                        } else {
                            setProgressAndCallback(!isLastStateMaxProgress, progress);
                        }
                        clearValues();
                        break;
                }
                return false;
            }

            private boolean isMoving(float endX, float endY) {
                float differenceX = Math.abs(startX - endX);
                float differenceY = Math.abs(startY - endY);
                if (differenceX > MOVE_ACTION_THRESHHOLD || differenceY > MOVE_ACTION_THRESHHOLD) {
                    return true;
                }
                return false;
            }

            private void clearValues(){
                startX = startY = 0;
                moved = false;
            }

            private void setProgressAndCallback(final boolean isMaxValue, int progress){
                setProgress(isMaxValue);
                for (Callback callback : callbackList) {
                    if(isMaxValue){
                        callback.onExpand(progress);
                    } else {
                        callback.onCollapse(progress);
                    }
                }
                int startColor = isMaxValue ? sunStrikeResources.getColorProgress() : sunStrikeResources.getColorChecked();
                int endColor = isMaxValue ? sunStrikeResources.getColorChecked() : sunStrikeResources.getColorProgress();
                colorAnimation.startAnimation(startColor, endColor);
            }
        });
    }

    private void setProgress(final boolean isMaxValue){
        post(new Runnable() { // to fix issue with SeekBar thumb position update
            @Override
            public void run() {
                isLastStateMaxProgress = isMaxValue;
                if(android.os.Build.VERSION.SDK_INT >= 11){
                    playProgressChangeAnimation(isMaxValue ? MAX_PROGRESS : MIN_PROGRESS);
                } else {
                    setProgress(isMaxValue ? MAX_PROGRESS : MIN_PROGRESS);
                }
            }
        });
    }

    private void playProgressChangeAnimation(int progress){
        ObjectAnimator animation = ObjectAnimator.ofInt(CustomSeekBar.this, "progress", progress);
        animation.setDuration(ANIMATION_DURATION);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    public void addCallback(final Callback callback) {
        this.callbackList.add(callback);
    }

    public interface Callback {
        void onMove(int progress);

        void onExpand(int progress);

        void onCollapse(int progress);
    }
}
