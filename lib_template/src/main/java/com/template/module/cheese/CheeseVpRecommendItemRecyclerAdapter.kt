package com.template.module.cheese

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.template.databinding.ItemCheeseRecommendBinding
import com.template.module.bean.net.RecommendationReason

class CheeseVpRecommendItemRecyclerAdapter(private val data: List<RecommendationReason>) :
    RecyclerView.Adapter<CheeseVpRecommendItemRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemCheeseRecommendBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder, position: Int
    ) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ItemCheeseRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bean: RecommendationReason) {
            binding.recommendTv.apply {
                text = " · " + bean.reason
                if (bean.isHighlight == true) {
                    isSelected = true
                    typeface = Typeface.DEFAULT_BOLD
                }

            }
        }
    }


}
