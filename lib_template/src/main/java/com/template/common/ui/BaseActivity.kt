package com.template.common.ui

import android.util.Log
import androidx.viewbinding.ViewBinding
import com.template.base.mvvm.v.BaseFrameActivity
import com.template.base.mvvm.vm.BaseViewModel
import com.template.base.utils.ActivityStackManager
import com.template.base.utils.BarUtils

/**
 * Activity基类
 *
 *
 * @since 8/27/20
 */
abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : BaseFrameActivity<VB, VM>(),
	IUiView {

	/**
	 * 设置状态栏
	 * 子类需要自定义时重写该方法即可
	 * @return Unit
	 */
	override fun setStatusBar() {
		BarUtils.transparentStatusBar(this)
		BarUtils.setStatusBarLightMode(this, true)
	}

	override fun onResume() {
		super.onResume()
		Log.d("ActivityLifecycle", "ActivityStack: ${ActivityStackManager.activityStack}")
	}

	override fun showLoading() {
	}

	override fun dismissLoading() {
	}

}