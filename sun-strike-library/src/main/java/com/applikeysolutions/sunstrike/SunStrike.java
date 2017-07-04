package com.applikeysolutions.sunstrike;

import android.view.View;

import com.applikeysolutions.sunstrike.view.AnimationLayout;
import com.applikeysolutions.sunstrike.view.CustomSeekBar;
import com.applikeysolutions.sunstrike.view.CustomSwitch;

import java.util.Arrays;

public class SunStrike {
    private AnimationController animationController;
    private final AnimationLayout animationLayout;
    private final int indexMainView;
    private final CustomSeekBar customSeekBar;
    private final CustomSwitch customSwitch;
    private final int[] indexHiddenViews;
    private View mainView;

    private SunStrike(SunStrikeBuilder builder) {
        this.animationLayout = builder.animationLayout;
        this.indexMainView = builder.indexMainView;
        this.customSeekBar = builder.customSeekBar;
        this.customSwitch = builder.customSwitch;
        this.indexHiddenViews = builder.indexHiddenViews;
    }

    private boolean isCorrectIndexMainView() {
        return indexMainView <= animationLayout.getChildCount() - 1;
    }

    private boolean isCorrectIndexHiddenView() {
        if (indexHiddenViews.length == 0) {
            return false;
        }
        for (int indexHiddenView : indexHiddenViews) {
            if (animationLayout.getChildCount() - 1 < indexHiddenView) {
                return false;
            }
            if (indexMainView >= indexHiddenView) {
                return false;
            }
        }
        return true;
    }

    private boolean isCorrectSequence() {
        for (int i = 0; i < indexHiddenViews.length - 1; i++) {
            if ((indexHiddenViews[i] + 1) != indexHiddenViews[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private boolean isLayoutFree() {
        return animationLayout.isFree();
    }

    public void init() {
        animationLayout.post(new Runnable() {
            @Override
            public void run() {
                if (indexHiddenViews != null) {
                    Arrays.sort(indexHiddenViews);
                    if (isCorrectIndexMainView() && isCorrectIndexHiddenView() && isCorrectSequence() && isLayoutFree()) {
                        animationLayout.setFree(false);
                        mainView = animationLayout.getChildAt(indexMainView);
                        animationController = new AnimationController(mainView);
                        switchListener();
                        seekBarListener();
                        addHiddenViews();
                        findMovingLayout();
                    }
                }
            }
        });
    }

    private void addHiddenViews() {
        for (int indexHiddenView : indexHiddenViews) {
            View hiddenView = animationLayout.getChildAt(indexHiddenView);
            hiddenView.setX(mainView.getX());
            hiddenView.setY(mainView.getY());
            animationController.addHiddenView(hiddenView);
        }
        animationController.generateHandler();
    }

    private void findMovingLayout() {
        int positionMove = indexHiddenViews[indexHiddenViews.length - 1] + 1;
        for (int i = positionMove; i < animationLayout.getChildCount(); i++) {
            View view = animationLayout.getChildAt(i);
            if (i == positionMove) {
                view.setY(mainView.getY() + mainView.getMeasuredHeight());
            } else {
                int positionHiddenView = i;
                float height = mainView.getMeasuredHeight();
                while (positionHiddenView != positionMove) {
                    height += animationLayout.getChildAt(positionHiddenView).getMeasuredHeight();
                    positionHiddenView--;
                }
                view.setY(mainView.getY() + height);
            }
            animationController.addMovingView(view);
        }
    }

    private void switchListener() {
        if (customSwitch != null) {
            customSwitch.setCallbackList(new CustomSwitch.Callback() {
                @Override
                public void onChecked() {
                    animationController.expand();
                }

                @Override
                public void onUnchecked() {
                    animationController.collapse();
                }
            });
        }
    }

    private void seekBarListener() {
        if (customSeekBar != null) {
            customSeekBar.addCallback(new CustomSeekBar.Callback() {
                @Override
                public void onMove(int progress) {
                    animationController.moveWithSeekBar(progress);
                }

                @Override
                public void onExpand(int progress) {
                    animationController.endAnimationExpand(progress);
                }

                @Override
                public void onCollapse(int progress) {
                    animationController.endAnimationCollapse(progress);
                }
            });
        }
    }

    public static class SunStrikeBuilder {
        private final AnimationLayout animationLayout;
        private final int indexMainView;
        private CustomSeekBar customSeekBar;
        private CustomSwitch customSwitch;
        private int[] indexHiddenViews;

        public SunStrikeBuilder(AnimationLayout animationLayout, int indexMainView) {
            this.animationLayout = animationLayout;
            this.indexMainView = indexMainView;
        }

        public SunStrikeBuilder with(CustomSeekBar customSeekBar) {
            this.customSeekBar = customSeekBar;
            return this;
        }

        public SunStrikeBuilder with(CustomSwitch customSwitch) {
            this.customSwitch = customSwitch;
            return this;
        }

        public SunStrikeBuilder indexHidden(int[] indexHiddenViews) {
            this.indexHiddenViews = indexHiddenViews;
            return this;
        }

        public SunStrike build() {
            return new SunStrike(this);
        }
    }
}
