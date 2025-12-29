package com.template.module.debug

import com.template.module.bean.net.RecommendationReason
import com.template.module.bean.ui.CheeseUIBean
import com.template.module.bean.ui.DateUIBean
import com.template.module.bean.ui.DishUIBean
import com.template.module.bean.ui.MealUIBean
import kotlin.random.Random

object DebugHelper {
    fun addDay(dateString: String): CheeseUIBean {
        return genDay(dateString)
    }

    fun addMeal(mealType: String): MealUIBean {
        return genMeal(mealType)
    }

    fun addDish(): DishUIBean {
        return genDish()
    }

    private fun genDay(dateString: String): CheeseUIBean {

        val dateUI = dateString.split("-")[2]

        return CheeseUIBean(
            date = DateUIBean(date = dateString, dateUI = dateUI), mealUIBeans = mutableListOf(
                genMeal("breakfast"),
                genMeal("lunch"),
                genMeal("dinner"),
            )
        )
    }

    private fun genMeal(mealType: String): MealUIBean {
        val typeUI = when (mealType) {
            "breakfast" -> "早餐"
            "lunch" -> "午餐"
            "dinner" -> "晚餐"
            "extraMeal" -> "加餐"
            else -> "未知"
        }
        return MealUIBean(
            mealTypeUI = typeUI,
            mealType = mealType, dishes = mutableListOf(
                genDish(), genDish(), genDish()
            )
        )
    }

    private fun genDish(): DishUIBean {
        return DishUIBean(
            name = "测试生成菜品${Random.nextInt(100)}",
            imageUrl = "https://ts1.tc.mm.bing.net/th/id/OIP-C.-yb7Tdb_vKz5y_ntpSsgLAHaE8",
            recommendationReasons = listOf(
                RecommendationReason(
                    isHighlight = true, reason = "测试生成高亮理由"
                ),
                RecommendationReason(
                    isHighlight = false, reason = "测试生成理由"
                ),
                RecommendationReason(
                    isHighlight = false, reason = "测试生成理由"
                ),
            )
        )
    }
}