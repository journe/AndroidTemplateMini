package com.template.common.retrofit

import com.blankj.utilcode.util.SPUtils
import com.template.BuildConfig
import com.template.CheeseConstant
import com.template.base.constant.VersionStatus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 全局作用域的网络层的依赖注入模块c
 *
 *
 * @since 6/4/21 8:58 AM
 */
@Module
@InstallIn(SingletonComponent::class)
class DINetworkModule {

    /**
     * [OkHttpClient]依赖提供方法
     *
     * @return OkHttpClient
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        // 日志拦截器部分
        val level = if (BuildConfig.VERSION_TYPE != VersionStatus.RELEASE) BODY else NONE
        val logInterceptor = HttpLoggingInterceptor(HttpLogger()).setLevel(level)

        val builder = OkHttpClient.Builder()
            .connectTimeout(55L * 1000L, TimeUnit.MILLISECONDS)
            .readTimeout(55L * 1000L, TimeUnit.MILLISECONDS)
            .addInterceptor(logInterceptor)
        
        if (SPUtils.getInstance().getBoolean(CheeseConstant.MOCK_KEY))
            builder.addInterceptor(MockApiInterceptor())

        return builder
            .addInterceptor {
                val original = it.request()
                val req =
                    original.newBuilder()
                        .addHeader("Content-Type", "application/json")//添加头部
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", CheeseConstant.userToken)
                        .method(original.method, original.body)
                        .build()
                it.proceed(req)
            }
            .retryOnConnectionFailure(true)
            .build()
    }

    /**
     * 项目主要服务器地址的[Retrofit]依赖提供方法
     *
     * @param okHttpClient OkHttpClient OkHttp客户端
     * @return Retrofit
     */
    @Singleton
    @Provides
    fun provideMainRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetBaseUrlConstant.MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}