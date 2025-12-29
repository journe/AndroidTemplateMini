package com.template.module.second

import com.template.base.mvvm.m.BaseRepository
import com.template.common.retrofit.ApiService
import javax.inject.Inject

class SecondRepository @Inject constructor() : BaseRepository() {
	@Inject
	lateinit var mApi: ApiService

}