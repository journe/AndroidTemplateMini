package com.template.module

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import com.template.common.ui.BaseFragment
import com.template.databinding.FragmentSecondBinding

@AndroidEntryPoint
class DFragment : BaseFragment<FragmentSecondBinding, DViewModel>() {

    override val mViewModel: DViewModel by viewModels()

    override fun initView() {
//        mBinding.buttonSecond.setOnClickListener {
//        }
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        mViewModel.getData()
    }


}