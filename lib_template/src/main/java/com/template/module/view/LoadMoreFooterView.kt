package com.template.module.view

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.DecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import com.template.R
import com.template.databinding.LayoutLoadMoreFooterBinding
import kotlin.math.sin

//自定义view示例
class LoadMoreFooterView : ConstraintLayout {

    lateinit var mBinding: LayoutLoadMoreFooterBinding

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    private fun init(context: Context) {
        if (isInEditMode) {
            LayoutInflater.from(context).inflate(R.layout.layout_load_more_footer, this, true)
            return
        }
        mBinding = LayoutLoadMoreFooterBinding.inflate(LayoutInflater.from(context), this, true)
        startShakeAnimation()
    }

    private var shakeAnimator: ValueAnimator? = null

    private fun startShakeAnimation(amplitude: Int = 20, duration: Long = 1000) {

        // 创建值动画，控制抖动偏移量
        shakeAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            this.duration = duration
            interpolator = DecelerateInterpolator()
            repeatCount = ValueAnimator.INFINITE // 无限循环
            repeatMode = ValueAnimator.REVERSE // 反转模式

            addUpdateListener { animator ->
                val progress = animator.animatedValue as Float
                // 计算Y轴偏移量（使用正弦函数使抖动更自然）
                val offset = amplitude * sin(progress * Math.PI * 2).toFloat()
                // 应用偏移到View
                mBinding.root.translationY = offset
            }
        }

        shakeAnimator?.start()
    }

}