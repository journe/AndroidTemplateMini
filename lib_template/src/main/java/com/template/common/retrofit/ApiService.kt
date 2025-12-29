package com.template.common.retrofit

import com.template.module.bean.net.PostDailyPlanSaveBody
import com.template.module.bean.net.PostDishChangeBody
import com.template.module.bean.net.PostDishSaveBody
import com.template.module.bean.net.PostLoginBody
import com.template.module.bean.net.RespDailyPlanBean
import com.template.module.bean.net.RespDishBean
import com.template.module.bean.net.RespLoginBean
import com.template.module.bean.net.RespMealBean
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST(NetBaseUrlConstant.DISH_SAVE)
    suspend fun dishSave(@Body body: PostDishSaveBody)

    @POST(NetBaseUrlConstant.DISH_CHANGE)
    suspend fun dishChange(@Body body: PostDishChangeBody): ApiResponse<RespDishBean>

    @DELETE(NetBaseUrlConstant.DISH_DELETE)
    suspend fun dishDelete(@Path("id") id: Int)

    @POST(NetBaseUrlConstant.DAILYPLAN_SAVE)
    suspend fun dailyPlan(@Body body: PostDailyPlanSaveBody)

    @POST(NetBaseUrlConstant.LOGIN)
    suspend fun login(@Body body: PostLoginBody): ApiResponse<RespLoginBean>

    @DELETE(NetBaseUrlConstant.DAILYPLAN_DELETE)
    suspend fun dailyPlanDelete(@Path("date") date: String): ApiResponse<RespDailyPlanBean>

    @GET(NetBaseUrlConstant.DAILYPLAN_QUERY)
    suspend fun dailyPlanQuery(@Path("date") date: String): ApiResponse<RespDailyPlanBean>

    @GET(NetBaseUrlConstant.DAILYPLAN_FUTURE)
    suspend fun dailyPlanFuture(): ApiResponse<List<RespDailyPlanBean>>

    @GET(NetBaseUrlConstant.MEAL_SAVE)
    suspend fun mealSave(): ApiResponse<RespMealBean>

    @GET(NetBaseUrlConstant.MEAL_DELETE)
    suspend fun mealDelete(
        @Query("date") date: String,
        @Query("mealType") mealType: String
    ): ApiResponse<RespMealBean>

    @GET(NetBaseUrlConstant.MEAL_QUERY)
    suspend fun mealQuery(
        @Query("date") date: String,
        @Query("mealType") mealType: String
    ): ApiResponse<RespMealBean>
}