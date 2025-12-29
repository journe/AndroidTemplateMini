package com.template.module.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.blankj.utilcode.util.SizeUtils
import com.template.R
import com.template.databinding.LayoutHeadTabBinding

class CheeseTabView : ConstraintLayout {

    lateinit var mBinding: LayoutHeadTabBinding

    private var isExpanded = false  // 记录当前状态（展开/折叠）
    private val rotationStep = 4f   // 每个View的旋转角度差
    private val rotationYStep = 8f   // 每个View的旋转后y轴偏移量
    private val expandDistance = 54f  // 展开时每个View的垂直间距（dp）

    private var itemviews = emptyList<View>()

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
            LayoutInflater.from(context).inflate(R.layout.layout_head_tab, this, true)
            return
        }
        mBinding = LayoutHeadTabBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setTitleList(titles: List<String>, itemClickListener: ItemClickListener? = null) {
        itemviews = titles.reversed().map { title ->
            CheeseTabItemView(context).apply {
                id = generateViewId()  // 生成唯一ID
                setTitle(title)
                if (itemClickListener != null) {
                    setOnClickListener { itemClickListener.clickItem(title) }
                }

            }
        }
        mBinding.root.removeAllViews()
        itemviews.forEach { view ->
            mBinding.root.addView(view)
            val constraintSet = ConstraintSet().apply {
                clone(mBinding.root)
                connect(
                    view.id,
                    ConstraintSet.TOP,
                    LayoutParams.PARENT_ID,
                    ConstraintSet.TOP,
                    SizeUtils.dp2px(20f)
                )
                connect(
                    view.id,
                    ConstraintSet.START,
                    LayoutParams.PARENT_ID,
                    ConstraintSet.START,
                    0
                )
//                connect(
//                    view.id,
//                    ConstraintSet.END,
//                    LayoutParams.PARENT_ID,
//                    ConstraintSet.END,
//                    SizeUtils.dp2px(8f)
//                )
            }
            // 4. 应用约束
            constraintSet.applyTo(mBinding.root)
        }

    }

    // 初始化堆叠状态
    fun initStackedState() {
        itemviews.reversed().forEachIndexed { index, view ->
            view.animate()
                .translationX(0f)
                .translationY(-rotationYStep * index)
                .rotation(-rotationStep * index)
                .alpha(if (index == 0) 1f else 0.5f - (index * 0.1f))
        }
    }

    fun backStackedState() {
        itemviews.reversed().forEachIndexed { index, view ->
            view.animate().translationY(0f).translationX(0f).rotation(0f)
        }
    }

    // 切换展开/折叠状态
    fun toggleState() {
        if (isExpanded) {
            // 折叠动画：回到堆叠状态
            itemviews.reversed().forEachIndexed { index, view ->
                view.animate()
                    .translationX(0f)
                    .translationY(-rotationYStep * index)
                    .rotation(-rotationStep * index)
                    .alpha(if (index == 0) 1f else 0.5f - (index * 0.1f))
            }
        } else {
            // 展开动画：垂直排列成列表
            itemviews.reversed().forEachIndexed { index, view ->
                // 平移动画：垂直排列（每个View间隔一定距离）
                val distance = SizeUtils.dp2px(expandDistance) * index
                view.animate()
                    .translationX(0f)
                    .translationY(distance.toFloat())
                    .rotation(0f)
                    .alpha(1f)
            }
        }
        // 切换状态标记
        isExpanded = !isExpanded
    }

    interface ItemClickListener {
        fun clickItem(title: String)
    }
}