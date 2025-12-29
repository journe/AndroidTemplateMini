package com.template.module.bean.ui

import com.template.module.bean.net.Nutrients
import com.template.module.bean.net.RecommendationReason
import com.template.module.bean.net.SummaryBean

data class CheeseUIBean(
    var date: DateUIBean = DateUIBean(),
    var mealUIBeans: MutableList<MealUIBean> = mutableListOf(),
    var summary: SummaryBean? = SummaryBean(),
    var nowMills: Long = 0L
)

data class MealUIBean(
    val dishes: MutableList<DishUIBean> = mutableListOf(),
    val mealTypeUI: String = "",
    val mealType: String = "",
    var date: DateUIBean = DateUIBean(),
    val summary: SummaryBean = SummaryBean(),
    var mealIndex: Int = 0,
    var nowMills: Long = 0L
)

data class DateUIBean(
    var date: String = "",
    var dateUI: String = "",
    var dateInWeek: String = "",
    var dateIndex: Int = 0,
)

data class DishUIBean(
    var mealType: String = "",
    var name: String = "",
    var imageUrl: String = "",
    val portion: String = "",
    var date: String = "",
    var nowMills: Long = 0L,
    val nutrients: Nutrients = Nutrients(),
    var recommendationReasons: List<RecommendationReason> = emptyList()
)