package com.applikeysolutions.sunstrike;

import java.util.ArrayList;
import java.util.List;

class ValueHandler {
    private final static int MAX_PROGRESS = 100;
    private final static float MIN_Z = 0.1f;
    private final static float MAX_SCALE = 1.f;
    private final static float MIN_SCALE_X = 0.5f;
    private final static float MIN_SCALE_Y = 0.3f;
    private final static float DIFFERENCE_SCALE = 0.2f;
    private final static float POINT_Z = 20f;
    private final int countHiddenViews;
    private final float pointZFirstView;
    private final int viewHeight;
    private float heightHiddenViews;
    private List<Float> listHeightHiddenViews = new ArrayList<>();

    ValueHandler(int countHiddenViews, int viewHeight) {
        this.countHiddenViews = countHiddenViews;
        pointZFirstView = POINT_Z * countHiddenViews;
        this.viewHeight = viewHeight;
    }

    float getMinZ() {
        return MIN_Z;
    }

    float getMaxScale() {
        return MAX_SCALE;
    }

    float getMinScaleX() {
        return MIN_SCALE_X;
    }

    void incrementHeightHiddenViews(float height) {
        heightHiddenViews += height;
        listHeightHiddenViews.add(height);
    }

    float getPointZFirstView() {
        return pointZFirstView;
    }

    float getStartYMoveViews() {
        return -heightHiddenViews;
    }

    float getCurrentZFirstView(int progress) {
        return (pointZFirstView * progress) / MAX_PROGRESS;
    }

    float getMaxHeightHiddenView(int position) {
        float end = viewHeight;
        if (position == 0) {
            return -end;
        } else {
            int positionPreviousViews = position;
            while (positionPreviousViews != 0) {
                end += listHeightHiddenViews.get(positionPreviousViews);
                positionPreviousViews--;
            }
        }
        return -end;
    }

    float getCurrentYViewProgress(int progress, int position) {
        return (getMaxHeightHiddenView(position) * progress) / MAX_PROGRESS;
    }


    float getCurrentZView(int position) {
        return (countHiddenViews - (position + 1)) * POINT_Z;
    }

    float getCurrentZView(int progress, int position) {
        return (getCurrentZView(position) * progress) / MAX_PROGRESS;
    }

    float getScaleXView(int position) {
        float currentScaleX = MAX_SCALE - ((float) (position + 1) / (countHiddenViews + 1));
        if (currentScaleX < MIN_SCALE_X) {
            currentScaleX = MIN_SCALE_X;
        }
        return currentScaleX;
    }

    float getScaleXView(int progress, int position) {
        float currentX = (MAX_SCALE * progress) / MAX_PROGRESS;
        if (currentX < getScaleXView(position)) {
            currentX = getScaleXView(position);
        }
        return currentX;
    }

    float getScaleYView(int position) {
        float currentScaleY = getScaleXView(position) - DIFFERENCE_SCALE;
        if (currentScaleY < MIN_SCALE_Y) {
            currentScaleY = MIN_SCALE_Y;
        }

        if (heightHiddenViews != viewHeight * countHiddenViews) {
            currentScaleY = 0;
        }
        return currentScaleY;
    }

    float getScaleYView(int progress, int position) {
        float currentY = (MAX_SCALE * progress) / MAX_PROGRESS;
        if (currentY < getScaleYView(position)) {
            currentY = getScaleYView(position);
        }
        return currentY;
    }

    float getYMovingViews(int progress) {
        return getStartYMoveViews() - (getStartYMoveViews() * progress) / MAX_PROGRESS;
    }
}
