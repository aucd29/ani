package net.sarangnamu.common.ani

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.os.Build
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 9. 27.. <p/>
 *
 * unsafe cast operator : https://kotlinlang.org/docs/reference/typecasts.html
 */

class FadeStatusBar {
    companion object {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun start(window: Window, fcolor:Int, scolor: Int, listener: Animator.AnimatorListener?, duration: Long = 500) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            val from = ContextCompat.getColor(window.context, fcolor)
            val to   = ContextCompat.getColor(window.context, scolor)

            ValueAnimator.ofArgb(from, to).apply {
                this.duration = duration
                this.addUpdateListener { window.statusBarColor = it.animatedValue as Int }
                listener?.let { this.addListener(it) }
            }.start()
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun startResource(window: Window, @ColorRes fcolor: Int, @ColorRes scolor: Int, listener: Animator.AnimatorListener?, duration: Long = 500) {
            start(window, ContextCompat.getColor(window.context, fcolor), ContextCompat.getColor(window.context, scolor), listener, duration)
        }
    }
}