package com.template.module.bean.net

import com.blankj.utilcode.util.TimeUtils
import com.template.module.bean.ui.CheeseUIBean
import com.template.module.bean.ui.DateUIBean
import com.template.module.bean.ui.DishUIBean
import com.template.module.bean.ui.MealUIBean


data class RespDailyPlanBean(
    val date: String? = "",
    val dateInWeek: String? = "",
    val meals: List<RespMealBean>? = listOf(),
    val summary: SummaryBean? = SummaryBean()
)

data class SummaryBean(
    val carbs: Int? = null,
    val fat: Int? = null,
    val protein: Int? = null,
    val totalCalories: Int? = null
)

data class Nutrients(
    val calories: Float? = null,
    val carbs: Float? = null,
    val fat: Float? = null,
    val protein: Float? = null
)

data class RecommendationReason(
    val isHighlight: Boolean? = null,
    val reason: String? = null
)

fun RespDailyPlanBean.toUIBean(): CheeseUIBean {
    val mealList = this.meals
    val nowMills = TimeUtils.getNowMills()
    val dateUIBean = DateUIBean(this.date ?: "", "", this.dateInWeek ?: "")
    if (!this.date.isNullOrEmpty()) {
        dateUIBean.dateUI = this.date.split("-")[2]
    }
    if (!mealList.isNullOrEmpty()) {
        val uiBeanlist = mealList.map {
            val typeUI = when (it.mealType) {
                "breakfast" -> "早餐"
                "lunch" -> "午餐"
                "dinner" -> "晚餐"
                "extraMeal" -> "加餐"
                else -> "未知"
            }
            var dishUIList = mutableListOf<DishUIBean>()
            if (!it.dishes.isNullOrEmpty()) {
                dishUIList = it.dishes.map { dish ->
                    DishUIBean(
                        mealType = it.mealType ?: "",
                        name = dish.name ?: "",
                        imageUrl = dish.imageUrl ?: "",
                        portion = dish.portion ?: "",
                        nutrients = dish.nutrients ?: Nutrients(),
                        date = dateUIBean.date,
                        nowMills = nowMills,
                        recommendationReasons = dish.recommendationReasons ?: emptyList()
                    )
                } as MutableList<DishUIBean>
            }

            MealUIBean(
                dishUIList,
                typeUI,
                mealType = it.mealType ?: "",
                date = dateUIBean,
                it.summary ?: SummaryBean(),
                nowMills = nowMills
            )
        }.toMutableList()
        return CheeseUIBean(dateUIBean, uiBeanlist, this.summary, nowMills = nowMills)
    }
    return CheeseUIBean(dateUIBean, mutableListOf(), this.summary, nowMills = nowMills)
}