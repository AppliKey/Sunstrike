package com.applikeysolutions.sunstrike;

import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.animation.Interpolator;

import com.applikeysolutions.sunstrike.animation.TransitionAnimation;

import java.util.ArrayList;
import java.util.List;

class AnimationController {
    private View firstView;
    private List<View> hiddenViews = new ArrayList<>();
    private List<View> movingViews = new ArrayList<>();
    private TransitionAnimation transitionAnimFirstView;
    private TransitionAnimation transitionAnimHiddenView;
    private TransitionAnimation transitionAnimMovingView;

    private ValueHandler valueHandler;

    AnimationController(View firstView) {
        this.firstView = firstView;
        transitionAnimFirstView = new TransitionAnimation(firstView);
    }

    void addHiddenView(View hiddenView) {
        hiddenViews.add(hiddenView);
    }

    void addMovingView(View moveView) {
        movingViews.add(moveView);
    }

    void generateHandler() {
        valueHandler = new ValueHandler(hiddenViews.size(), firstView.getMeasuredHeight());
        for (int i = 0; i < hiddenViews.size(); i++) {
            valueHandler.incrementHeightHiddenViews(hiddenViews.get(i).getMeasuredHeight());
        }
        firstView.setTranslationZ(valueHandler.getMinZ());
    }

    private void expandHiddenView() {
        for (int i = 0; i < hiddenViews.size(); i++) {
            startAnimationExpand(i,
                    valueHandler.getCurrentZView(i),
                    valueHandler.getMaxHeightHiddenView(i),
                    valueHandler.getScaleXView(i),
                    valueHandler.getScaleYView(i));
        }
    }

    private void collapseHiddenView() {
        for (int i = 0; i < hiddenViews.size(); i++) {
            startAnimationCollapse(i,
                    valueHandler.getCurrentZView(i),
                    0,
                    valueHandler.getMaxScale(),
                    valueHandler.getMaxScale(),
                    valueHandler.getScaleXView(i),
                    valueHandler.getScaleYView(i));
        }
    }

    private void startAnimationExpand(int position, float startZ, float startY, float scaleX, float scaleY) {
        transitionAnimHiddenView = new TransitionAnimation(hiddenViews.get(position));
        transitionAnimHiddenView.setInterpolator(new FastOutLinearInInterpolator());

        transitionAnimHiddenView.startAnimation(startZ,
                0,
                TransitionAnimation.AnimationType.TRANSLATION_Z);

        transitionAnimHiddenView.startAnimation(startY,
                0,
                TransitionAnimation.AnimationType.TRANSLATION_Y);

        transitionAnimHiddenView.startAnimation(scaleX,
                valueHandler.getMaxScale(),
                TransitionAnimation.AnimationType.SCALE_X);

        transitionAnimHiddenView.startAnimation(scaleY,
                valueHandler.getMaxScale(),
                TransitionAnimation.AnimationType.SCALE_Y);
    }

    private void startAnimationCollapse(int position, float startZ, float startY,
                                        float firstScaleX, float firstScaleY,
                                        float scaleX, float scaleY) {
        transitionAnimHiddenView = new TransitionAnimation(hiddenViews.get(position));
        transitionAnimHiddenView.setInterpolator(new LinearOutSlowInInterpolator());

        transitionAnimHiddenView.startAnimation(startZ,
                0,
                TransitionAnimation.AnimationType.TRANSLATION_Z);

        transitionAnimHiddenView.startAnimation(startY,
                valueHandler.getMaxHeightHiddenView(position),
                TransitionAnimation.AnimationType.TRANSLATION_Y);

        transitionAnimHiddenView.startAnimation(firstScaleX,
                scaleX,
                TransitionAnimation.AnimationType.SCALE_X);

        transitionAnimHiddenView.startAnimation(firstScaleY,
                scaleY,
                TransitionAnimation.AnimationType.SCALE_Y);
    }

    void expand() {
        setTransitionFirstViewZ(valueHandler.getPointZFirstView(), valueHandler.getMinZ(),
                new FastOutLinearInInterpolator());
        expandHiddenView();
        moveViews(valueHandler.getStartYMoveViews(), 0, new FastOutLinearInInterpolator());
    }

    void collapse() {
        setTransitionFirstViewZ(valueHandler.getPointZFirstView(), valueHandler.getMinZ(),
                new LinearOutSlowInInterpolator());
        collapseHiddenView();
        moveViews(0, valueHandler.getStartYMoveViews(), new LinearOutSlowInInterpolator());
    }

    void moveWithSeekBar(int progress) {
        firstView.setTranslationZ(valueHandler.getPointZFirstView()
                - valueHandler.getCurrentZFirstView(progress));

        if (progress == 0) {
            firstView.setTranslationZ(valueHandler.getMinZ());
        }

        for (int i = 0; i < hiddenViews.size(); i++) {
            hiddenViews.get(i).setTranslationY(valueHandler.getMaxHeightHiddenView(i)
                    - valueHandler.getCurrentYViewProgress(progress, i));
            hiddenViews.get(i).setTranslationZ(valueHandler.getCurrentZView(i)
                    - valueHandler.getCurrentZView(progress, i));
            hiddenViews.get(i).setScaleX(valueHandler.getScaleXView(progress, i));
            hiddenViews.get(i).setScaleY(valueHandler.getScaleYView(progress, i));

            if (progress == 0) {
                hiddenViews.get(i).setTranslationZ(0);
            }
        }

        for (int i = 0; i < movingViews.size(); i++) {
            movingViews.get(i).setTranslationY(valueHandler.getYMovingViews(progress));
        }
    }

    private void moveViews(float startPoint, float endPoint, Interpolator interpolator) {
        if (movingViews.size() != 0) {
            for (int i = 0; i < movingViews.size(); i++) {
                transitionAnimMovingView = new TransitionAnimation(movingViews.get(i));
                transitionAnimMovingView.setInterpolator(interpolator);
                transitionAnimMovingView.startAnimation(startPoint, endPoint,
                        TransitionAnimation.AnimationType.TRANSLATION_Y);
            }
        }
    }

    private void setTransitionFirstViewZ(float startPoint, float endPoint, Interpolator interpolator) {
        transitionAnimFirstView.setInterpolator(interpolator);
        transitionAnimFirstView.startAnimation(startPoint, endPoint,
                TransitionAnimation.AnimationType.TRANSLATION_Z);
    }

    void endAnimationExpand(int progress) {
        setTransitionFirstViewZ(valueHandler.getCurrentZFirstView(progress),
                valueHandler.getMinZ(), new FastOutLinearInInterpolator());

        moveViews(valueHandler.getYMovingViews(progress), 0, new FastOutLinearInInterpolator());
        for (int i = 0; i < hiddenViews.size(); i++) {
            startAnimationExpand(i,
                    valueHandler.getCurrentZView(progress, i),
                    valueHandler.getMaxHeightHiddenView(i)
                            - valueHandler.getCurrentYViewProgress(progress, i),
                    valueHandler.getScaleXView(progress, i),
                    valueHandler.getScaleYView(progress, i));
        }
    }

    void endAnimationCollapse(int progress) {
        setTransitionFirstViewZ(valueHandler.getCurrentZFirstView(progress),
                valueHandler.getMinZ(), new LinearOutSlowInInterpolator());

        moveViews(valueHandler.getYMovingViews(progress), valueHandler.getStartYMoveViews(),
                new LinearOutSlowInInterpolator());
        for (int i = 0; i < hiddenViews.size(); i++) {
            startAnimationCollapse(i,
                    valueHandler.getCurrentZView(progress, i),
                    valueHandler.getMaxHeightHiddenView(i)
                            - valueHandler.getCurrentYViewProgress(progress, i),
                    valueHandler.getScaleXView(progress, i),
                    valueHandler.getScaleYView(progress, i),
                    valueHandler.getMinScaleX(),
                    0);
        }
    }
}
