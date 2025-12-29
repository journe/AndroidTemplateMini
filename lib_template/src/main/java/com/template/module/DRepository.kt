package com.template.module

import com.template.base.mvvm.m.BaseRepository
import com.template.common.retrofit.ApiService
import javax.inject.Inject

class DRepository @Inject constructor() : BaseRepository() {

    @Inject
    lateinit var mApi: ApiService

}