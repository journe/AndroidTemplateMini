package com.template.module.bean.net

data class RespMealBean(
    val dishes: List<RespDishBean>? = null,
    val mealType: String? = null,
    val summary: SummaryBean? = null
)
