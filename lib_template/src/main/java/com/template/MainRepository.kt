package com.template

import com.template.base.mvvm.m.BaseRepository
import com.template.common.retrofit.ApiService
import com.template.module.bean.net.PostLoginBody
import kotlinx.coroutines.delay
import javax.inject.Inject

class MainRepository @Inject constructor() : BaseRepository() {

    @Inject
    lateinit var mApi: ApiService

    suspend fun login(name: String, pass: String) =
        executeHttp { mApi.login(PostLoginBody(name, pass)) }

}