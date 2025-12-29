package com.template.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.template.BuildConfig
import com.template.base.app.ActivityLifecycleCallbacksImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * Application 基类
 *
 * @since 4/24/21 5:30 PM
 */
open class BaseApplication : Application() {

	private val mCoroutineScope by lazy(mode = LazyThreadSafetyMode.NONE) { MainScope() }

	companion object {
		// 全局Context
		@SuppressLint("StaticFieldLeak")
		lateinit var context: Context

		@SuppressLint("StaticFieldLeak")
		lateinit var application: BaseApplication
	}

	override fun attachBaseContext(base: Context) {
		super.attachBaseContext(base)
		context = base
		application = this
	}

	override fun onCreate() {
		super.onCreate()
		// 全局监听 Activity 生命周期
		registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImpl())
		// 策略初始化第三方依赖
		initDepends()
	}

	/**
	 * 初始化第三方依赖
	 */
	private fun initDepends() {
		// 开启一个 Default Coroutine 进行初始化不会立即使用的第三方
		mCoroutineScope.launch(Dispatchers.Default) {
			initLogger()
		}

		Log.d("BaseApplication", "初始化完成")
	}

	override fun onTerminate() {
		super.onTerminate()
		mCoroutineScope.cancel()
	}

	private fun initLogger(): String {
		Logger.addLogAdapter(object :
			AndroidLogAdapter(
				PrettyFormatStrategy.newBuilder().tag("logger").build()
			) {
			override fun isLoggable(priority: Int, tag: String?): Boolean {
				return BuildConfig.DEBUG
			}
		})
		return "Logger -->> init complete"
	}

}