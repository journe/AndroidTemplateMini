package com.template.base.mvvm.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.template.base.utils.UiState

/**
 * ViewModel 基类
 *
 *
 * @since 8/27/20
 */
abstract class BaseViewModel : ViewModel() {

    /**
     * 控制状态视图的LiveData
     */
    val stateViewLD = MutableLiveData<UiState>()

    protected fun changeStateView(state: UiState) {
        stateViewLD.postValue(state)
    }
}