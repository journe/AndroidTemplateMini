package com.template.module.cheese

import android.graphics.Typeface
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.template.base.ktx.setAnimation
import com.template.base.utils.UiState
import com.template.common.ui.BaseFragment
import com.template.databinding.FragmentCheeseUilibBinding
import com.template.databinding.ItemCheeseDateBinding
import com.template.databinding.ItemCheeseMealTabBinding
import com.template.module.bean.ui.DateUIBean
import com.template.module.bean.ui.MealUIBean
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.collections.map
import kotlin.math.abs

@AndroidEntryPoint
class CheeseFragment : BaseFragment<FragmentCheeseUilibBinding, CheeseViewModel>() {
    override val mViewModel: CheeseViewModel by activityViewModels()

    private lateinit var cheeseVPAdapter: CheeseVPAdapter
    override fun initView() {
        mBinding.cheeseVp.setAnimation()
        cheeseVPAdapter = CheeseVPAdapter(this, mutableListOf())
        mBinding.cheeseVp.adapter = cheeseVPAdapter
        mBinding.cheeseVp.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                mViewModel.currentMealTabUIBean.postValue(mViewModel.mealUIBeans[position])
                super.onPageSelected(position)
            }

        })
    }

    override fun initObserve() {
        mViewModel.stateViewLD.observe(this) {
            it as UiState
            when (it) {
                is UiState.Empty -> {}
                is UiState.Error -> {
                    dismissLoading()
                }

                is UiState.Loading -> {
                    showLoading()
                }

                is UiState.Success -> {
                    dismissLoading()
                }
            }
        }

        mViewModel.currentMealTabUIBean.observe(this) {
            mBinding.mealUItv.text = it.mealTypeUI
        }

        lifecycleScope.launch {
            launch {
                mViewModel.mealUIlistFlow.collect {
//                    "mealUIlistFlow".d()
                    mealsViewpagerInit(it)
                    cheeseVPAdapter.setNewData(it)
                }
            }

            launch {
                mViewModel.cheeseListFlow.collect { cheeseList ->
//                    "cheeseListFlow".d()
                    if (cheeseList.isEmpty()) {
                        return@collect
                    }
                    val dateUIBeans = cheeseList.mapIndexed { index, bean ->
                        bean.date.apply { this.dateIndex = index }
                    }.toMutableList()
                    val mealUIBeans = cheeseList.flatMap { it.mealUIBeans }.toMutableList()
                    mealUIBeans.forEachIndexed { index, bean -> bean.mealIndex = index }
                    mViewModel.dateUIBeans = dateUIBeans
                    mViewModel.mealUIBeans = mealUIBeans
                    dateTabInit(dateUIBeans, mealUIBeans)
                }
            }

        }
    }

    override fun initRequestData() {
        mViewModel.dailyPlanFuture()

    }

    private fun dateTabInit(
        dateUIBeans: List<DateUIBean>,
        mealUIBeans: List<MealUIBean>
    ) {
        //日期tab
        mBinding.dateDslTab.removeAllViews()
        val dateTabViews = dateUIBeans.map {
            ItemCheeseDateBinding.inflate(layoutInflater).apply {
                dateItemTv.text = it.dateUI
                weekItemTv.text = it.dateInWeek
            }
        }
        dateTabViews.forEach {
            mBinding.dateDslTab.addView(it.root)
        }
        DateViewPager2Delegate(
            mBinding.cheeseVp,
            mBinding.dateDslTab,
            dateUIBeans,
            mealUIBeans
        )

        mBinding.dateDslTab.observeIndexChange { fromIndex, toIndex, reselect, fromUser ->
            //日期tab样式
            dateTabViews.forEachIndexed { index, binding ->
                binding.root.apply {
                    alpha = 1 - abs(toIndex - index) * 0.1f
                }
                if (index == toIndex) {
                    binding.dateItemTv.typeface = Typeface.DEFAULT_BOLD
                    binding.weekItemTv.typeface = Typeface.DEFAULT_BOLD
                } else {
                    binding.dateItemTv.typeface = Typeface.DEFAULT
                    binding.weekItemTv.typeface = Typeface.DEFAULT
                }
            }

            //用户点击事件关联vp
            if (fromUser) {
                changeMealVPCurrentItem(mViewModel.dateIndexFindMealIndex(toIndex))
            }
        }
    }

    private fun changeMealVPCurrentItem(toIndex: Int) {
        mBinding.cheeseVp.currentItem = toIndex
    }

    //mealType和View pager绑定
    private fun mealsViewpagerInit(mealUIBeans: MutableList<MealUIBean>) {
        //食谱tab

        val mealTabViews = mealUIBeans.map {
            ItemCheeseMealTabBinding.inflate(layoutInflater).apply {
                mealTypeTabTv.text = it.mealTypeUI
            }
        }
        mBinding.mealDslTab.removeAllViews()
        mealTabViews.forEach {
            mBinding.mealDslTab.addView(it.root)
        }
        ViewPager2Delegate.install(mBinding.cheeseVp, mBinding.mealDslTab)

        mBinding.mealDslTab.observeIndexChange { fromIndex, toIndex, reselect, fromUser ->
            mViewModel.currentMealTabUIBean.postValue(mealUIBeans[toIndex])
        }
    }
}