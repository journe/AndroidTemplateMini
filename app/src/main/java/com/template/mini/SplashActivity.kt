package com.template.mini

import android.content.Intent
import androidx.activity.viewModels
import com.template.CheeseMainActivity
import com.template.base.ktx.clickDelay
import com.template.base.mvvm.vm.EmptyViewModel
import com.template.common.ui.BaseActivity
import com.template.mini.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding, EmptyViewModel>() {
	override val mViewModel: EmptyViewModel by viewModels()

	override fun initView() {
		mBinding.splashIv.clickDelay {
			startActivity(Intent(this, CheeseMainActivity::class.java))
		}
		startActivity(Intent(this, CheeseMainActivity::class.java))

	}

	override fun initObserve() {
	}

	override fun initRequestData() {
	}
}