package com.template.module.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 不可滚动但保留Item点击事件的RecyclerView
 * 核心：禁止滚动但允许触摸事件传递给子View
 */
class NonScrollableRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        // 设置自定义布局管理器（双重保障禁止滚动）
        layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean = false
            override fun canScrollHorizontally(): Boolean = false

            // 让RecyclerView高度自适应内容
            override fun onMeasure(
                recycler: Recycler,
                state: State,
                widthSpec: Int,
                heightSpec: Int
            ) {
                val heightSpec = MeasureSpec.makeMeasureSpec(
                    Int.MAX_VALUE shr 2, // 最大高度限制
                    MeasureSpec.AT_MOST
                )
                super.onMeasure(recycler, state, widthSpec, heightSpec)
            }
        }
    }

    /**
     * 允许触摸事件传递给子View（保证Item点击有效）
     * 但禁止引发滚动的触摸行为
     */
    override fun onTouchEvent(e: MotionEvent): Boolean {
        // 只处理可能触发点击的事件，过滤掉滚动相关事件
        return when (e.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> super.onTouchEvent(e)

            else -> false // 忽略滑动相关事件
        }
    }

    /**
     * 不拦截触摸事件，确保事件能传递到子Item
     */
    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        // 不拦截任何事件，让事件直接传递给子View
        return false
    }

    /**
     * 禁止通过代码触发滚动
     */
    override fun scrollBy(dx: Int, dy: Int) {}
    override fun scrollToPosition(position: Int) {}
    override fun smoothScrollToPosition(position: Int) {}
}