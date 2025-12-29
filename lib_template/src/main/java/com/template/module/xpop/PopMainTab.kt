package com.template.module.xpop

import android.content.Context
import com.template.R
import com.template.databinding.PopupMainTabBinding
import com.template.module.action.ActionHelper
import com.template.module.view.CheeseTabView
import com.lxj.xpopup.core.PositionPopupView

class PopMainTab(
    context: Context,
    private val titles: List<String>,
    private val callback: () -> Unit
) :
    PositionPopupView(context) {
    lateinit var mBinding: PopupMainTabBinding

    override fun getImplLayoutId(): Int {
        return R.layout.popup_main_tab
    }

    override fun onCreate() {
        super.onCreate()
        mBinding = PopupMainTabBinding.bind(popupImplView)
        mBinding.moduleNameIv.apply {
            setTitleList(titles, object : CheeseTabView.ItemClickListener {
                override fun clickItem(title: String) {
                    dismissWith {
                        when (title) {
                            "今日食谱" -> {
                                ActionHelper.naviMain()
                            }

                            "食材库" -> {
                                ActionHelper.naviHolder()
                            }
                        }
                    }
                }
            })
            toggleState()
        }
    }

    override fun onDismiss() {
        callback()
        super.onDismiss()
    }

    override fun beforeDismiss() {
        mBinding.moduleNameIv.toggleState()
        super.beforeDismiss()
    }
}