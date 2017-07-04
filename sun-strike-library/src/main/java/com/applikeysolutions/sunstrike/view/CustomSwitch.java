package com.applikeysolutions.sunstrike.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import com.applikeysolutions.sunstrike.SunStrikeResources;

import java.util.ArrayList;
import java.util.List;

public class CustomSwitch extends SwitchCompat {
    private SunStrikeResources sunStrikeResources;
    private List<Callback> callbackList = new ArrayList<>();

    public CustomSwitch(Context context) {
        this(context, null);
    }

    public CustomSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setCustomColor(@ColorInt int colorBackground, @ColorInt int colorProgress, @ColorInt int colorCircle) {
        DrawableCompat.setTintList(DrawableCompat.wrap(getTrackDrawable()), new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                },
                new int[]{
                        colorProgress,
                        colorBackground
                }
        ));

        DrawableCompat.setTintList(DrawableCompat.wrap(getThumbDrawable()), new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                },
                new int[]{
                        colorCircle,
                        colorProgress
                }
        ));
    }

    private void init() {
        sunStrikeResources = new SunStrikeResources(getContext());

        setBackgroundColor(sunStrikeResources.getColorTransparent());
        setCustomColor(sunStrikeResources.getColorBackground(),
                sunStrikeResources.getColorProgress(),
                sunStrikeResources.getColorChecked());


        this.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (callbackList.size() != 0) {
                    for (Callback callback : callbackList) {
                        if (isChecked) {
                            callback.onChecked();
                        } else {
                            callback.onUnchecked();
                        }
                    }
                }
            }
        });
    }

    public void setCallbackList(final Callback callback) {
        this.callbackList.add(callback);
    }

    public interface Callback {
        void onChecked();

        void onUnchecked();
    }
}
