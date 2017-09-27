package net.sarangnamu.common.ani

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 9. 27.. <p/>
 */

class Fade {
    companion object {
        fun start(hideView: View, showView: View, listener: Animator.AnimatorListener?) {
            ObjectAnimator.ofFloat(hideView, "alpha", 0.25f, 1f, 1f).start()
            ObjectAnimator.ofFloat(showView, "alpha", 1f, 1f, 1f).apply {
                listener?.let { addListener(it) }
            }.start()
        }
    }
}