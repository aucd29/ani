package net.sarangnamu.common.ani

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import java.time.Duration

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 9. 27.. <p/>
 */

class Resize {
    companion object {
        fun height(view: View, height: Int, listener: ResizeAnimationListener?, duration: Long = 600) {
            ValueAnimator.ofInt(view.measuredHeight, height).apply {
                addUpdateListener {
                    val value = it.animatedValue as Int
                    when (view.parent) {
                        is LinearLayout   -> view.layoutParams.height = value   // 되는 코드인지 확인해봐야 함
                        is RelativeLayout -> view.layoutParams.height = value
                        is FrameLayout    -> view.layoutParams.height = value
                    }
                }
                addListener(object: Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) { }
                    override fun onAnimationCancel(p0: Animator?) { }
                    override fun onAnimationStart(p0: Animator?) { listener?.onStart() }
                    override fun onAnimationEnd(p0: Animator?) {
                        removeAllUpdateListeners()
                        removeAllListeners()

                        listener?.onEnd()
                    }
                })
                this.duration = duration
            }.start()
        }
    }

    interface ResizeAnimationListener {
        fun onStart()
        fun onEnd()
    }
}
