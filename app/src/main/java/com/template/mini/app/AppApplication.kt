package com.template.mini.app

import com.template.base.BaseApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
    }
}