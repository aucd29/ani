/*
 * Copyright (C) Hanwha S&C Ltd., 2016. All rights reserved.
 *
 * This software is covered by the license agreement between
 * the end user and Hanwha S&C Ltd., and may be
 * used and copied only in accordance with the terms of the
 * said agreement.
 *
 * Hanwha S&C Ltd., assumes no responsibility or
 * liability for any errors or inaccuracies in this software,
 * or any consequential, incidental or indirect damage arising
 * out of the use of the software.
 */

package net.sarangnamu.common.ani;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2016. 9. 12.. <p/>
 */
public class FadeStatusBar {
    private static final int DURATION = 500;

    public static void start(Window window, int fColor, int sColor, Animator.AnimatorListener l) {
        start(window, fColor, sColor, DURATION, l);
    }

    // http://stackoverflow.com/questions/2614545/animate-change-of-view-background-color-in-android
    public static void start(final Window window, int fColor, int sColor, int duration, Animator.AnimatorListener l) {
        if (window == null) {
            return ;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return ;
        }

        int colorFrom = window.getContext().getResources().getColor(fColor, null);
        int colorTo   = window.getContext().getResources().getColor(sColor, null);

        ValueAnimator anim = ValueAnimator.ofArgb(colorFrom, colorTo);

        anim.setDuration(duration);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                window.setStatusBarColor((int) animation.getAnimatedValue());
            }
        });

        if (l != null) {
            anim.addListener(l);
        }

        anim.start();
    }
}
