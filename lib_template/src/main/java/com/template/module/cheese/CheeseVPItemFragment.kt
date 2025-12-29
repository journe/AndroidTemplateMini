package com.template.module.cheese

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.template.base.ktx.clickDelay
import com.template.base.ktx.gone
import com.template.common.ui.BaseFragment
import com.template.databinding.FragmentCheeseVpItemBinding
import com.template.databinding.ItemCheeseDetailBinding
import com.template.module.action.ActionDishEvent
import com.template.module.action.ActionDishInfo
import com.template.module.bean.ui.DishUIBean
import com.template.module.bean.ui.MealUIBean
import com.template.module.debug.DebugHelper
import com.template.module.xpop.toastCenter
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.getValue

class CheeseVPItemFragment(private val item: MealUIBean, private val vpPosition: Int) :
    BaseFragment<FragmentCheeseVpItemBinding, CheeseVPItemViewModel>() {
    override val mViewModel: CheeseVPItemViewModel by viewModels()

    private val cheeseViewModel: CheeseViewModel by activityViewModels()

    private var cheeseRvAdapter = CheeseVpRecyclerAdapter(mutableListOf())

    private val dishListAdapter = DishListAdapter()

    private val actionObserver = Observer<ActionDishEvent> { value ->
        when (value.action) {
            is ActionDishInfo.AddDish -> {
                toastCenter(requireContext(), "收到AI指令 增加菜品${value.action.payload.name}")
                actionDishAdd(
                    value.action.date,
                    value.action.mealType,
                    value.action.payload
                )
            }

            is ActionDishInfo.DeleteDish -> {
                toastCenter(requireContext(), "收到AI指令 删除菜品 ${value.action.name}")
                actionDishDelete(
                    value.action.date,
                    value.action.mealType,
                    value.action.name,
                )
            }

            is ActionDishInfo.ReplaceDish -> {

                if (value.action.payload.name.isEmpty()) {
                    toastCenter(
                        requireContext(),
                        "收到用户手动指令 替换菜品 ${value.action.name}"
                    )
                    actionDishReplaceUser(
                        value.action.date,
                        value.action.mealType,
                        value.action.name
                    )
                } else {
                    toastCenter(requireContext(), "收到AI指令 替换菜品 ${value.action.name}")
                    actionDishReplace(
                        value.action.date,
                        value.action.mealType,
                        value.action.name,
                        value.action.payload
                    )
                }
            }

            is ActionDishInfo.SearchDish -> {
                toastCenter(requireContext(), "收到AI指令 搜索菜品 ${value.action.mealName}")

            }

            is ActionDishInfo.UpdateDish -> {
                toastCenter(requireContext(), "收到AI指令 更新菜品 ${value.action.name}")
                actionDishUpdate(
                    value.action.date,
                    value.action.mealType,
                    value.action.name,
                    value.action.payload
                )
            }
        }
    }

    override fun initView() {
//        mBinding.cheeseRv.adapter = cheeseRvAdapter
        mBinding.cheeseRv.adapter = dishListAdapter
        mBinding.cheeseRv.itemAnimator = ItemRecyclerViewAnimator()

    }

    override fun initObserve() {
        lifecycleScope.launch {
            cheeseViewModel.mealUIlistFlow.collect {
//                "mealUIlistFlow".d()
                if (vpPosition < it.size)
                    dishListAdapter.submitList(it[vpPosition].dishes)
            }
        }
        mViewModel.dishUIBeanList.observe(this) {
//            dishListAdapter.submitList(item.dishes)
        }
    }

    override fun initRequestData() {
//        mViewModel.setAllData(item.dishes)
    }

    private fun actionDishAdd(
        date: String,
        mealType: String,
        payload: DishUIBean
    ) {
        cheeseViewModel.addDish(date, mealType, payload)

    }

    private fun actionDishReplace(
        date: String,
        mealType: String,
        name: String,
        payload: DishUIBean
    ) {
        cheeseViewModel.replaceDish(date, mealType, name, payload)
    }

    private fun actionDishReplaceUser(
        date: String,
        mealType: String,
        name: String,
    ) {
        cheeseViewModel.replaceDishUser(date, mealType, name)
    }

    private fun actionDishDelete(date: String, mealType: String, name: String) {
        cheeseViewModel.deleteDish(date, mealType, name)
    }

    private fun actionDishUpdate(
        date: String,
        mealType: String,
        name: String,
        payload: DishUIBean
    ) {
        cheeseViewModel.updateDish(date, mealType, name, payload)

    }

    private fun aiActions() {
        LiveEventBus
            .get(ActionDishEvent::class.java).observeForever(actionObserver)
    }

    private fun removeActions() {
        LiveEventBus
            .get(ActionDishEvent::class.java).removeObserver(actionObserver)
    }


    override fun onResume() {
        super.onResume()
        aiActions()
    }

    override fun onPause() {
        super.onPause()
        removeActions()
    }

    // 添加Item并显示动画
    private fun addItem(position: Int, item: String) {
//        itemList.add(position, item)
        // 通知适配器Item已添加
        cheeseRvAdapter.notifyItemInserted(position)
        // 滚动到添加的位置
//        binding.recyclerView.scrollToPosition(position)
    }

    // 删除Item并显示动画
    private fun removeItem(position: Int) {
//        dishUIBeans.removeAt(position)
        // 通知适配器Item已删除
        cheeseRvAdapter.notifyItemRemoved(position)
    }

    inner class ItemRecyclerViewAnimator() : DefaultItemAnimator() {
        override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
            // 自定义添加动画逻辑
            holder!!.itemView.animate()
                .alpha(1f)
                .translationX(0f)
                .setDuration(300)
                .setInterpolator(DecelerateInterpolator())
                .start()
            return super.animateAdd(holder)
        }

        override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
            // 自定义删除动画逻辑
            holder!!.itemView.animate()
                .alpha(0f)
                .translationX(holder.itemView.width.toFloat())
                .setDuration(300)
                .setInterpolator(DecelerateInterpolator())
                .start()
            return super.animateRemove(holder)
        }
    }


    inner class CheeseVpRecyclerAdapter(private val data: MutableList<DishUIBean>) :
        RecyclerView.Adapter<CheeseVpRecyclerAdapter.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ): ViewHolder {
            return ViewHolder(
                ItemCheeseDetailBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        override fun onBindViewHolder(
            holder: ViewHolder, position: Int
        ) {
            holder.bind(data[position], position)

        }

        // 重写此方法以支持删除动画
        override fun onViewRecycled(holder: ViewHolder) {
            super.onViewRecycled(holder)
            // 清除动画，避免复用问题
            holder.itemView.clearAnimation()
        }

        fun animateAdd(holder: ViewHolder, itemposition: Int) {
            holder.itemView.animate()
                .alpha(0f)
                .translationX(holder.itemView.width.toFloat())
                .setDuration(300)
                .setInterpolator(DecelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        data[itemposition] = DebugHelper.addDish()
//                        notifyItemChanged(itemposition)
//                        notifyDataSetChanged()
//                        val position = itemposition
                        lifecycleScope.launch {
                            delay(64)
//                            data[position] = testDish
//                            delay(16)
//                            notifyItemChanged(position)

                            animateShow(holder, itemposition)
                        }

                    }
                })
                .start()

        }

        fun animateShow(holder: ViewHolder, itemposition: Int) {
            holder.itemView.animate()
                .alpha(1f)
                .translationX(0f)
                .setDuration(300)
                .setInterpolator(DecelerateInterpolator())
                .start()
            holder.itemView.clearAnimation()
        }

        // 自定义删除动画
        fun animateRemove(holder: ViewHolder, itemposition: Int) {
            holder.itemView.animate()
                .alpha(0f)
                .translationX(holder.itemView.width.toFloat())
                .setDuration(300)
                .setInterpolator(DecelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        // 动画结束后通知适配器
                        val position = itemposition
                        lifecycleScope.launch {
//                            delay(16)
//                            data.removeAt(position)
//                            delay(16)
//                            notifyItemRemoved(position)
//                            notifyDataSetChanged()
                        }

                    }
                })
                .start()
        }

        override fun getItemCount(): Int {
            return data.size
        }

        inner class ViewHolder(private val binding: ItemCheeseDetailBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(bean: DishUIBean, itemPosition: Int) {
                binding.name.text = bean.name
//                binding.type.text = bean.portion
                binding.delCard.clickDelay {
                    animateRemove(this, itemPosition)
//                    removeItem(itemPosition)
                }

                binding.refreshCard.clickDelay {
                    animateAdd(this@ViewHolder, itemPosition)
//                    lifecycleScope.launch {
//                        animateRemove(this@ViewHolder,itemposition)
//                        delay(320)
//                        animateAdd(this@ViewHolder,itemposition)
//                    }
                }
                if (bean.imageUrl.isNotEmpty()) {
                    binding.image.load(bean.imageUrl)
                } else {
                    binding.image.gone()
                }
                if (!bean.recommendationReasons.isNullOrEmpty()) {
                    binding.recommendRV.adapter =
                        CheeseVpRecommendItemRecyclerAdapter(bean.recommendationReasons)
                }
            }
        }
    }

}