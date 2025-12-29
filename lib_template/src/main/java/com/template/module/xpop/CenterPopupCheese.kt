package com.template.module.xpop

import android.content.Context
import androidx.core.view.postDelayed
import com.template.R
import com.template.databinding.PopupLoadingTextBinding
import com.lxj.xpopup.core.PositionPopupView

class CenterPopupCheese(context: Context, private val popTextStr: String = "") :
	PositionPopupView(context) {
	lateinit var mBinding: PopupLoadingTextBinding

	override fun getImplLayoutId(): Int {
		return R.layout.popup_loading_text
	}

	override fun onCreate() {
		super.onCreate()
		mBinding = PopupLoadingTextBinding.bind(popupImplView)
		mBinding.loadingTv.text = popTextStr
		postDelayed(1000L) { dismiss() }
	}

}