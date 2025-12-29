package com.template

import com.blankj.utilcode.util.SPUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import com.template.base.ktx.launchIO
import com.template.base.mvvm.vm.BaseViewModel
import com.template.common.retrofit.parseData
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MainRepository) : BaseViewModel() {

    val tabTitleList = mutableListOf("今日食谱", "食材库", "饮食记录", "热量报告")

    fun login() {
        launchIO {
            repo.login("13100000003", "123").parseData {
                onSuccess = {
//                    dailyPlanBean.postValue(it)
//                    changeStateView(UiState.Success())
                    CheeseConstant.userToken = it.token ?: ""
                    SPUtils.getInstance().put(CheeseConstant.TOKEN_KEY,it.token)
                }
            }
        }
    }
}