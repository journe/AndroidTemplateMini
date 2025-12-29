package com.template.module.bean.net

data class PostDailyPlanSaveBody(
    val date: String? = null,
    val meals: List<Meal?>? = null,
    val summary: SummaryBean? = null
)

data class Meal(
    val dishes: List<RespDishBean>? = null,
    val mealType: String? = null,
    val summary: SummaryBean? = null
)
