package com.template.module.xpop

import android.content.Context
import com.template.R
import com.template.databinding.PopupLoadingTextBinding
import com.lxj.xpopup.core.PositionPopupView

class PopLoadingText(context: Context, private val loadingString: String = "") :
	PositionPopupView(context) {
	lateinit var mBinding: PopupLoadingTextBinding

	override fun getImplLayoutId(): Int {
		return R.layout.popup_loading_text
	}

	override fun onCreate() {
		super.onCreate()
		mBinding = PopupLoadingTextBinding.bind(popupImplView)
		if (loadingString.isNotEmpty()) {
			mBinding.loadingTv.text = loadingString
		}
	}

}