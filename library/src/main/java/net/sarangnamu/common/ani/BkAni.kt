package net.sarangnamu.common.ani

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 2.. <p/>
 */

class Translation {
    companion object {
        fun startX(view: View, move: Float, f: ((Animator?) -> Unit)? = null) {
            start(view, move, "translationX", f)
        }

        fun startY(view: View, move: Float, f: ((Animator?) -> Unit)? = null) {
            start(view, move, "translationY", f)
        }

        private fun start(view: View, move: Float, type: String, f: ((Animator?) -> Unit)? = null) {
            val value = move * view.context.resources.displayMetrics.density
            ObjectAnimator.ofFloat(view, type, value).apply {
                f?.let { addEndListener(it) }
            }.start()
        }
    }
}

class Fade {
    companion object {
        fun start(hideView: View, showView: View, f: ((Animator?) -> Unit)? = null) {
            ObjectAnimator.ofFloat(hideView, "alpha", 0.25f, 1f, 1f).start()
            ObjectAnimator.ofFloat(showView, "alpha", 1f, 1f, 1f).apply {
                f?.let { this.addEndListener(it) }
            }.start()
        }
    }
}

class FadeColor {
    companion object {
        fun start(view: View, fcolor: Int, scolor: Int, f: ((Animator?) -> Unit)? = null, duration: Long = 500) {
            ObjectAnimator.ofObject(view, "backgroundColor", ArgbEvaluator(), fcolor, scolor).apply {
                this.duration = duration
                f?.let { this.addEndListener(it) }
            }.start()
        }

        fun startResource(view: View, @ColorRes fcolor: Int, @ColorRes scolor: Int, f: ((Animator?) -> Unit)? = null, duration: Long = 500) {
            start(view, view.context.color(fcolor), view.context.color(scolor), f, duration)
        }
    }
}

class FadeStatusBar {
    companion object {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun start(window: Window, fcolor:Int, scolor: Int,  f: ((Animator?) -> Unit)? = null, duration: Long = 500) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            val from = window.context.color(fcolor)
            val to   = window.context.color(scolor)

            ValueAnimator.ofArgb(from, to).apply {
                this.duration = duration
                this.addUpdateListener { window.statusBarColor = it.animatedValue as Int }
                f?.let { this.addEndListener(it) }
            }.start()
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun startResource(window: Window, @ColorRes fcolor: Int, @ColorRes scolor: Int, f: ((Animator?) -> Unit)? = null, duration: Long = 500) {
            start(window, ContextCompat.getColor(window.context, fcolor), ContextCompat.getColor(window.context, scolor), f, duration)
        }
    }
}

class Resize {
    companion object {
        fun height(view: View, height: Int, f: ((Animator?) -> Unit)? = null, duration: Long = 600) {
            ValueAnimator.ofInt(view.measuredHeight, height).apply {
                addUpdateListener {
                    val value = animatedValue as Int
                    when (view.parent) {
                        is LinearLayout   -> view.layoutParams.height = value   // 되는 코드인지 확인해봐야 함
                        is RelativeLayout -> view.layoutParams.height = value
                        is FrameLayout    -> view.layoutParams.height = value
                    }
                }
                addListener(object: Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) { }
                    override fun onAnimationCancel(p0: Animator?) { }
                    override fun onAnimationStart(p0: Animator?) { }
                    override fun onAnimationEnd(animation: Animator?) {
                        removeAllUpdateListeners()
                        removeAllListeners()
                        f?.let { f(animation) }
                    }
                })
                this.duration = duration
            }.start()
        }
    }
}

private inline fun Animator.addEndListener(crossinline f: (Animator?) -> Unit) {
    addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator?) {}
        override fun onAnimationCancel(p0: Animator?) {}
        override fun onAnimationRepeat(p0: Animator?) {}
        override fun onAnimationEnd(animation: Animator?) {
            removeListener(this)
            f(animation)
        }
    })
}

private inline fun Context.color(@ColorRes color:Int): Int = ContextCompat.getColor(this, color)
