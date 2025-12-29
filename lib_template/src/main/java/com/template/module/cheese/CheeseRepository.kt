package com.template.module.cheese

import com.template.base.mvvm.m.BaseRepository
import com.template.common.retrofit.ApiService
import com.template.module.bean.net.PostDishChangeBody
import javax.inject.Inject

class CheeseRepository @Inject constructor() : BaseRepository() {

    @Inject
    lateinit var mApi: ApiService

    suspend fun dailyPlanQuery(date: String) = executeHttp { mApi.dailyPlanQuery(date) }

    suspend fun dailyPlanFuture() = executeHttp { mApi.dailyPlanFuture() }

    suspend fun replaceDish(body: PostDishChangeBody) = executeHttp { mApi.dishChange(body) }


    suspend fun dailyPlanDelete(date: String) = executeHttp { mApi.dailyPlanDelete(date) }


    //mealType: breakfast/lunch/dinner
    suspend fun mealQuery(
        date: String,
        mealType: String
    ) = executeHttp { mApi.mealQuery(date, mealType) }

//    suspend fun getTestData() = flow {
//        val result = listOf(
//            CheeseUIBean(
//                "早餐", "11", listOf(
//                    CheeseUIDataBean("早餐", "肉类", R.drawable.img_cheese_item1),
//                    CheeseUIDataBean("早餐", "蔬菜", R.drawable.img_cheese_item2),
//                    CheeseUIDataBean("牛奶", "主食"),
//                )
//            ),
//            CheeseUIBean(
//                "午餐", "11", listOf(
//                    CheeseUIDataBean("牛排", "肉类", R.drawable.img_cheese_item1),
//                    CheeseUIDataBean("菠菜", "蔬菜", R.drawable.img_cheese_item2),
//                    CheeseUIDataBean("米饭", "主食"),
//                )
//            ),
//            CheeseUIBean(
//                "晚餐", "11", listOf(
//                    CheeseUIDataBean("晚餐", "肉类", R.drawable.img_cheese_item1),
//                    CheeseUIDataBean("晚餐", "蔬菜", R.drawable.img_cheese_item2),
//                    CheeseUIDataBean("米饭", "主食"),
//                )
//            ),
//        )
//
//        emit(result)
//    }
}