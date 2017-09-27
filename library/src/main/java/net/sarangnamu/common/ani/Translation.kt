package net.sarangnamu.common.ani

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 9. 27.. <p/>
 */

class Translation {
    companion object {
        fun startX(view: View, move: Float, listener: Animator.AnimatorListener?) {
            start(view, move, listener)
        }

        fun startY(view: View, move: Float, listener: Animator.AnimatorListener?) {
            start(view, move, listener, "translationY")
        }

        private fun start(view: View, move: Float, listener: Animator.AnimatorListener?, type: String = "translationX") {
            val value = move * view.context.resources.displayMetrics.density
            ObjectAnimator.ofFloat(view, type, value).apply {
                listener?.let { addListener(it) }
            }.start()
        }
    }
}