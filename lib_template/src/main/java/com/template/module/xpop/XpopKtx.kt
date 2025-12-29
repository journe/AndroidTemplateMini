package com.template.module.xpop

import android.content.Context
import com.lxj.xpopup.XPopup

fun toastCenter(context: Context, popTextStr: String = "") {
    XPopup.Builder(context)
        .dismissOnTouchOutside(false)
        .hasShadowBg(true)
        .enableDrag(false)
        .isCenterHorizontal(true)
        .asCustom(CenterPopupCheese(context, popTextStr))
        .show()
}

fun toastLoadingText(context: Context, popTextStr: String = "生成中,请稍等") {
    XPopup.Builder(context)
        .dismissOnTouchOutside(false)
        .hasShadowBg(true)
        .enableDrag(false)
        .isCenterHorizontal(true)
        .asCustom(PopLoadingText(context, popTextStr))
        .show()
}