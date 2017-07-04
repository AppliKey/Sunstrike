package com.applikeysolutions.sunstrike.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class AnimationLayout extends LinearLayout {
    private boolean isFree = true;

    public AnimationLayout(Context context) {
        this(context, null);
    }

    public AnimationLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AnimationLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOrientation();
    }

    private void setOrientation() {
        setOrientation(VERTICAL);
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
