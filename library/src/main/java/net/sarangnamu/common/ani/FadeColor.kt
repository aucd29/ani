package net.sarangnamu.common.ani

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.animation.Animation

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 9. 27.. <p/>
 */

open class FadeColor {
    companion object {
        fun start(view: View, fcolor: Int, scolor: Int, listener: Animator.AnimatorListener?, duration: Long = 500) {
            ObjectAnimator.ofObject(view, "backgroundColor", ArgbEvaluator(), fcolor, scolor).apply {
                this.duration = duration
                listener?.let { this.addListener(it) }
            }.start()
        }

        fun startResource(view: View, @ColorRes fcolor: Int, @ColorRes scolor: Int, listener: Animator.AnimatorListener?, duration: Long = 500) {
            start(view, ContextCompat.getColor(view.context, fcolor), ContextCompat.getColor(view.context, scolor), listener, duration)
        }
    }
}