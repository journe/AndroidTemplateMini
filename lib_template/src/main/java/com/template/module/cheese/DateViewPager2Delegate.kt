package com.template.module.cheese

import androidx.viewpager2.widget.ViewPager2
import com.angcyo.tablayout.DslTabLayout
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.template.module.bean.ui.DateUIBean
import com.template.module.bean.ui.MealUIBean

class DateViewPager2Delegate(
    private val vpager: ViewPager2,
    private val tabLayout: DslTabLayout,
    private val dateUIBeans: List<DateUIBean>,
    private val mealUIBeans: List<MealUIBean>
) : ViewPager2Delegate(vpager, tabLayout, true) {
    init {
        viewPager.registerOnPageChangeCallback(this)
        dslTabLayout?.setupViewPager(this)
    }

    override fun onSetCurrentItem(
        fromIndex: Int,
        toIndex: Int,
        reselect: Boolean,
        fromUser: Boolean
    ) {
//        if (fromUser) {
//            viewPager.setCurrentItem(toIndex, true)
//        }
    }

    override fun onPageScrollStateChanged(state: Int) {
//        dslTabLayout?.onPageScrollStateChanged(state)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//        dslTabLayout?.onPageScrolled(position, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        val dateUI = mealUIBeans[position].date.dateUI
        var pageIndex = 0
//        dateUIBeans.find { dateUIBean -> dateUIBean.dateUI == dateUI }!!.dateIndex
        dateUIBeans.forEachIndexed { index, bean ->
            if (bean.dateUI == dateUI)
                pageIndex = index
        }
        dslTabLayout?.onPageSelected(pageIndex)
    }
}