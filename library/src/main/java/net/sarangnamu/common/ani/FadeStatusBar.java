/*
 * FadeStatusBar.java
 * Copyright 2013 Burke Choi All rights reserved.
 *             http://www.sarangnamu.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sarangnamu.common.ani;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.support.v4.content.ContextCompat;
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

        // https://stackoverflow.com/questions/32643596/i-upgraded-android-from-targetsdk-22-to-23-and-im-getting-a-nosuchmethoderror
        int colorFrom = ContextCompat.getColor(window.getContext(), fColor);
        int colorTo   = ContextCompat.getColor(window.getContext(), sColor);

        ValueAnimator anim = ValueAnimator.ofArgb(colorFrom, colorTo);

        anim.setDuration(duration);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor((int) animation.getAnimatedValue());
                }
            }
        });

        if (l != null) {
            anim.addListener(l);
        }

        anim.start();
    }
}
