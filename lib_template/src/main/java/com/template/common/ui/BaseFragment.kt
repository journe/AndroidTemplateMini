package com.template.common.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.template.base.ktx.ViewClickDelay.SPACE_TIME
import com.template.base.mvvm.v.BaseFrameFragment
import com.template.base.mvvm.vm.BaseViewModel
import com.template.module.xpop.PopLoadingText
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Fragment基类
 *
 * @since 8/27/20
 */
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFrameFragment<VB, VM>(),
    IUiView {

    private lateinit var loadingPopup: BasePopupView

    private var lastShowLoading = 0L


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        loadingPopup = XPopup.Builder(requireActivity())
            .dismissOnTouchOutside(false)
            .hasShadowBg(true)
            .enableDrag(false)
            .isCenterHorizontal(true)
            .asCustom(PopLoadingText(requireActivity(),"生成中，请稍等"))
        super.onViewCreated(view, savedInstanceState)
    }

    override fun showLoading() {
        loadingPopup.show()
        lastShowLoading = System.currentTimeMillis()
    }

    override fun dismissLoading() {
        //防止loading限制时间过短
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastShowLoading > SPACE_TIME) {
            loadingPopup.dismiss()
        } else {
            lifecycleScope.launch {
                delay(500L)
                loadingPopup.dismiss()
            }
        }
    }

}