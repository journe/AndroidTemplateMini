package com.template.base.utils

import androidx.annotation.StringRes
import com.template.base.BaseApplication.Companion.application as app

fun getString(@StringRes stringRes: Int): String {
    return app.getString(stringRes)
}