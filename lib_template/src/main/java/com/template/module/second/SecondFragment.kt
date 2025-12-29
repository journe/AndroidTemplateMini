package com.template.module.second

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import com.template.common.ui.BaseFragment
import com.template.databinding.FragmentSecondBinding

@AndroidEntryPoint
class SecondFragment : BaseFragment<FragmentSecondBinding, SecondViewModel>() {
	override val mViewModel: SecondViewModel by viewModels()

	override fun initView() {
		mBinding.apply {
			mBinding.textviewSecond.text = "食材库占位图"
		}

	}

	override fun initObserve() {
	}

	override fun initRequestData() {
	}

}