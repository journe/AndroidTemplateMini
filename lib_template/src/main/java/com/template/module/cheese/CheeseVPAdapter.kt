package com.template.module.cheese

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import com.template.common.ui.BaseFragmentStateAdapter
import com.template.module.bean.ui.MealUIBean

class CheeseVPAdapter(fragment: Fragment, data: MutableList<MealUIBean>) :
    BaseFragmentStateAdapter<MealUIBean>(fragment, data) {
    override fun createFragment(
        item: MealUIBean,
        position: Int
    ): Fragment {
        return CheeseVPItemFragment(item,position)
    }

    override fun diffNotifyDataSetChanged(
        oldData: MutableList<MealUIBean>,
        newData: MutableList<MealUIBean>
    ) {
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun getOldListSize(): Int = oldData.size

            override fun getNewListSize(): Int = newData.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldData[oldItemPosition].nowMills == newData[newItemPosition].nowMills

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldData[oldItemPosition].nowMills == newData[newItemPosition].nowMills

        }, true).dispatchUpdatesTo(this)
    }
}