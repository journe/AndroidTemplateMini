package com.template.module.bean.net

import com.blankj.utilcode.util.TimeUtils
import com.template.module.bean.ui.DishUIBean

data class RespDishBean(
    val id: String? = null,
    val date: String? = null,
    val mealType: String? = null,
    val name: String? = null,
    val imageUrl: String? = null,
    val portion: String? = null,
    val nutrients: Nutrients? = null,
    val recommendationReasons: List<RecommendationReason>? = null
)

fun RespDishBean.toUIBean(): DishUIBean {
    val dish = this
    return DishUIBean(
        mealType = dish.mealType ?: "",
        name = dish.name ?: "",
        imageUrl = dish.imageUrl ?: "",
        portion = dish.portion ?: "",
        nutrients = dish.nutrients ?: Nutrients(),
        date = dish.date ?: "",
        nowMills = TimeUtils.getNowMills(),
        recommendationReasons = dish.recommendationReasons ?: emptyList()
    )
}