package com.template.module.xpop

import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.lxj.xpopup.animator.PopupAnimator

class PopAlphaAnimator : PopupAnimator() {
    override fun initAnimator() {
        targetView.alpha = 0f
    }

    override fun animateShow() {
        targetView.animate().alpha(1f)
            .setInterpolator(FastOutSlowInInterpolator()).setDuration(360).start()
    }

    override fun animateDismiss() {
        targetView.animate().alpha(0f)
            .setInterpolator(FastOutSlowInInterpolator()).setDuration(360).start()
    }
}