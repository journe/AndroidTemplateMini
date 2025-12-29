package com.template.module

import dagger.hilt.android.lifecycle.HiltViewModel
import com.template.base.ktx.launchIO
import com.template.base.mvvm.vm.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class DViewModel @Inject constructor(private val mRepository: DRepository) : BaseViewModel() {

	fun getData() {
		launchIO {
		}
	}
}