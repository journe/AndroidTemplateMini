package com.template

import androidx.activity.viewModels
import com.template.base.ktx.clickDelay
import com.template.base.mvvm.vm.EmptyViewModel
import com.template.common.ui.BaseActivity
import com.template.databinding.ActivityDebugBinding
import com.template.module.xpop.toastCenter
import com.template.module.xpop.toastLoadingText

class DebugActivity : BaseActivity<ActivityDebugBinding, EmptyViewModel>() {
    override val mViewModel: EmptyViewModel by viewModels()

    override fun initView() {
        mBinding.apply {
            toastBtn.clickDelay {
                toastLoadingText(this@DebugActivity)
            }
            normalToast.clickDelay {
                toastCenter(this@DebugActivity, "通用土司测试")
            }

        }

    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }
}