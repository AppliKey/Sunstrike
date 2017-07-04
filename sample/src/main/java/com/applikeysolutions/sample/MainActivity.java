package com.applikeysolutions.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.applikeysolutions.sunstrike.SunStrike;
import com.applikeysolutions.sunstrike.view.AnimationLayout;
import com.applikeysolutions.sunstrike.view.CustomSeekBar;
import com.applikeysolutions.sunstrike.view.CustomSwitch;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.switchCompat)
    CustomSwitch switchCompat;
    @BindView(R.id.seekBar)
    CustomSeekBar seekBar;
    @BindView(R.id.animationLayout)
    AnimationLayout animationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        int indexMainView = 0;
        int[] indexHiddenViewsRight = {1, 2, 3};
        SunStrike sunStrike = new SunStrike.SunStrikeBuilder(animationLayout, indexMainView)
                .with(seekBar)
                .with(switchCompat)
                .indexHidden(indexHiddenViewsRight)
                .build();
        sunStrike.init();
    }
}
