package com.template

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.blankj.utilcode.util.SPUtils
import com.template.base.ktx.clickDelay
import com.template.base.ktx.swap
import com.template.common.ui.BaseActivity
import com.template.databinding.ActivityMainUilibBinding
import com.template.module.action.ActionNaviEvent
import com.template.module.action.ActionNaviInfo
import com.template.module.cheese.CheeseViewModel
import com.template.module.debug.ToastEvent
import com.template.module.xpop.DebugPopup
import com.template.module.xpop.PopAlphaAnimator
import com.template.module.xpop.PopMainTab
import com.template.module.xpop.toastCenter
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lxj.xpopup.XPopup
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class CheeseMainActivity : BaseActivity<ActivityMainUilibBinding, MainViewModel>() {

    /**
     * 通过 viewModels() + Hilt 获取 ViewModel 实例
     */
    override val mViewModel by viewModels<MainViewModel>()
    private val cheeseViewModel by viewModels<CheeseViewModel>()
    private lateinit var navController: NavController

    override fun initView() {
        val host = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment

        navController = host.navController

        mBinding.avatarIv.clickDelay {
            if (BuildConfig.DEBUG) {
                startActivity(Intent(this, DebugActivity::class.java))
            }
//            navController.navigate(R.id.action_to_SecondFragment)
        }

        mBinding.debugFab.clickDelay {
            XPopup.Builder(this@CheeseMainActivity)
                .dismissOnTouchOutside(true)
                .hasShadowBg(true)
                .isDestroyOnDismiss(false)
//                .customAnimator(PopAlphaAnimator())
                .asCustom(DebugPopup(this@CheeseMainActivity))
                .show()
        }

    }

    override fun initObserve() {
//		observeLiveData(mViewModel.data, ::processData)
        cheeseViewModel.currentMealTabUIBean.observe(this) {
            mBinding.toolbarTitleTv.text = it.mealTypeUI
        }
//        mBinding.toolbarTitleTv.translationY = 200f

        cheeseViewModel.currentTitleAlpha.observe(this) {
            mBinding.toolbarTitleTv.alpha = it
//            mBinding.toolbarTitleTv.translationY = 200f - it * 200

        }
        aiActions()
        LiveEventBus.get<String>("login").observe(this) {
            mViewModel.login()
        }
        LiveEventBus.get(ToastEvent::class.java).observe(this){
            toastCenter(this,it.msg)
        }

    }

    private fun processData(data: String) {
//		toast(data)
//		toastCenter(this, data)
    }

    override fun initRequestData() {
//		 模拟获取数据
//		mViewModel.getData()
        CheeseConstant.userToken = SPUtils.getInstance().getString(CheeseConstant.TOKEN_KEY)

        mBinding.moduleNameIv.apply {
            setTitleList(mViewModel.tabTitleList)
            initStackedState()
            clickDelay {
                backStackedState()
                XPopup.Builder(this@CheeseMainActivity)
                    .dismissOnTouchOutside(true)
                    .hasShadowBg(true)
                    .shadowBgColor("#ccffffff".toColorInt())
                    .isLightStatusBar(true)
                    .isDestroyOnDismiss(false)
                    .enableDrag(false)
                    .customAnimator(PopAlphaAnimator())
                    .asCustom(PopMainTab(this@CheeseMainActivity, mViewModel.tabTitleList) {
                        initStackedState()
                    })
                    .show()
            }
        }
    }

    private fun aiActions() {
        LiveEventBus
            .get(ActionNaviEvent::class.java).observe(this) {
                when (it.action) {
                    is ActionNaviInfo.NavigateHolderFragment -> {
                        mViewModel.tabTitleList.swap(0, 1)
                        mBinding.moduleNameIv.apply {
                            setTitleList(mViewModel.tabTitleList)
                            initStackedState()
                        }
                        navController.navigate(R.id.action_to_SecondFragment)
                    }

                    is ActionNaviInfo.NavigateMainFragment -> {
                        mViewModel.tabTitleList.swap(0, 1)
                        mBinding.moduleNameIv.apply {
                            setTitleList(mViewModel.tabTitleList)
                            initStackedState()
                        }
                        navController.navigate(R.id.action_to_MainFragment)

                    }
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}