package com.applikeysolutions.sunstrike;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;

public class SunStrikeResources {
    @ColorInt
    private int colorBackground;

    @ColorInt
    private int colorProgress;

    @ColorInt
    private int colorChecked;

    @ColorInt
    private final int colorTransparent;
    private final int seekBarWidth;

    private final Drawable thumb;
    private final Drawable background;

    public SunStrikeResources(Context context) {
        colorBackground = ContextCompat.getColor(context, R.color.colorBackground);
        colorProgress = ContextCompat.getColor(context, R.color.colorProgress);
        colorChecked = ContextCompat.getColor(context, R.color.colorCircle);
        colorTransparent = Color.TRANSPARENT;
        background = ContextCompat.getDrawable(context, R.drawable.seekbar);
        thumb = ContextCompat.getDrawable(context, R.drawable.circle);
        seekBarWidth = (int) context.getResources().getDimension(R.dimen.width_seekbar);
    }

    public int getColorBackground() {
        return colorBackground;
    }

    public int getColorProgress() {
        return colorProgress;
    }

    public int getColorChecked() {
        return colorChecked;
    }

    public Drawable getThumb() {
        return thumb;
    }

    public Drawable getBackground() {
        return background;
    }

    @ColorInt
    public int getColorTransparent() {
        return colorTransparent;
    }

    @ColorInt
    public int getSeekBarWidth() {
        return seekBarWidth;
    }

    public void setColorBackground(int colorBackground) {
        this.colorBackground = colorBackground;
    }

    public void setColorProgress(int colorProgress) {
        this.colorProgress = colorProgress;
    }

    public void setColorChecked(int colorChecked) {
        this.colorChecked = colorChecked;
    }
}
