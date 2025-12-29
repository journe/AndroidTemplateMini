package com.template.module.second

import dagger.hilt.android.lifecycle.HiltViewModel
import com.template.base.mvvm.vm.BaseViewModel
import com.template.common.model.db.AccountBean
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(private val mRepository: SecondRepository) :
	BaseViewModel() {



	fun insertData(bean: AccountBean) {
	}


}