package com.template.common.retrofit

import com.template.base.BaseApplication
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okio.Buffer
import java.io.IOException

class MockApiInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toString()

        if (url.contains("dailyPlan/queryByDate")) {
            val json = readJsonFromAssets("queryByDate.json")
            return mockResp(json, request)
        }

        if (url.contains("dailyPlan/queryFuturePlan")) {
            val json = readJsonFromAssets("queryFuture2.json")
            return mockResp(json, request)
        }

        if (url.contains("meal/query")) {
            val json = readJsonFromAssets("mealquery.json")
            return mockResp(json, request)
        }

        // 非目标接口则正常请求
        return chain.proceed(request)
    }

    private fun mockResp(json: String, request: Request): Response {
        return Response.Builder()
            .code(200)
            .message(json)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .body(ResponseBody.create("application/json".toMediaTypeOrNull(), json))
            .addHeader("content-type", "application/json")
            .build()
    }

    // 从assets目录读取JSON文件
    private fun readJsonFromAssets(fileName: String): String {
        return try {
            val inputStream = BaseApplication.context.assets.open(fileName)
            val buffer = Buffer()
            buffer.readFrom(inputStream)
            buffer.readUtf8()
        } catch (e: Exception) {
            e.message ?: ""
        }
    }
}
    