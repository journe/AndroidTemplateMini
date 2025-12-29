package com.template.module.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.template.R
import com.template.base.ktx.gone
import com.template.base.ktx.visible
import com.template.databinding.LayoutHeadTabItemBinding
import com.hjq.shape.layout.ShapeConstraintLayout

class CheeseTabItemView : ShapeConstraintLayout {

    lateinit var mBinding: LayoutHeadTabItemBinding

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
            LayoutInflater.from(context).inflate(R.layout.layout_head_tab_item, this, true)
            return
        }
        mBinding = LayoutHeadTabItemBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setTitle(title: String) {
        mBinding.titleTv.text = title
    }

    fun setEyeVisible(boolean: Boolean) {
        if (boolean) {
            mBinding.eyesGroup.visible()
        } else {
            mBinding.eyesGroup.gone()
        }
    }
}