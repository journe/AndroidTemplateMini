package com.template.module.cheese

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.template.base.ktx.clickDelay
import com.template.base.ktx.gone
import com.template.databinding.ItemCheeseDetailBinding
import com.template.module.action.ActionHelper
import com.template.module.bean.ui.DishUIBean

class DishListAdapter : ListAdapter<DishUIBean, DishListAdapter.UserViewHolder>(DIFF_CALLBACK) {
    inner class UserViewHolder(private val binding: ItemCheeseDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // 绑定数据（支持全量刷新）
        fun bind(bean: DishUIBean) {
            binding.name.text = bean.name
            binding.type.text = bean.portion
            binding.delCard.clickDelay {
//                animateRemove(this, itemPosition)
//                    removeItem(itemPosition)

                ActionHelper.deleteDish(date = bean.date, bean.mealType, bean.name)
            }

            binding.refreshCard.clickDelay {
                ActionHelper.replaceDish(date = bean.date, bean.mealType, bean.name)

//                animateAdd(this@ViewHolder, itemPosition)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemCheeseDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DishUIBean>() {
            override fun areItemsTheSame(
                oldItem: DishUIBean,
                newItem: DishUIBean
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: DishUIBean,
                newItem: DishUIBean
            ): Boolean {
                return oldItem.nowMills == newItem.nowMills
            }

        }
    }
}