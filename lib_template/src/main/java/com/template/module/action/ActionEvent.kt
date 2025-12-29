package com.template.module.action

import com.template.module.bean.ui.CheeseUIBean
import com.template.module.bean.ui.DishUIBean
import com.template.module.bean.ui.MealUIBean
import com.jeremyliao.liveeventbus.core.LiveEvent

data class ActionNaviEvent(val action: ActionNaviInfo) : LiveEvent

sealed class ActionNaviInfo(code: Int) {
    class NavigateMainFragment : ActionNaviInfo(0)
    class NavigateHolderFragment : ActionNaviInfo(1)
//    class Loading : ActionInfo(2)
//    data class Error(
//        val message: String = "",
//        val cause: Throwable? = null,
//        val errorCode: Int? = null
//    ) : ActionInfo(3)
//    class Empty : ActionInfo(4)
}

data class ActionDayEvent(val action: ActionDayInfo) : LiveEvent
sealed class ActionDayInfo(code: Int) {
    data class ViewDay(val date: String) : ActionDayInfo(0)
    data class ReplaceDay(val date: String, val payload: CheeseUIBean) : ActionDayInfo(1)
    data class DeleteDay(val date: String) : ActionDayInfo(2)
    data class CopyDay(val date: String, val targetDate: String) : ActionDayInfo(3)
}

data class ActionMealEvent(val action: ActionMealInfo) : LiveEvent
sealed class ActionMealInfo(code: Int) {
    data class ViewMeal(val date: String, val mealType: String) : ActionMealInfo(0)
    data class ReplaceMeal(val date: String, val mealType: String, val payload: MealUIBean) :
        ActionMealInfo(1)

    data class DeleteMeal(val date: String, val mealType: String) : ActionMealInfo(2)
    data class AddMeal(val date: String, val mealType: String) : ActionMealInfo(3)
}

data class ActionDishEvent(val action: ActionDishInfo) : LiveEvent
sealed class ActionDishInfo(code: Int) {
    data class SearchDish(val mealName: String) :
        ActionDishInfo(0)

    data class AddDish(val date: String, val mealType: String, val payload: DishUIBean) :
        ActionDishInfo(1)

    data class DeleteDish(val date: String, val mealType: String, val name: String) :
        ActionDishInfo(2)

    data class ReplaceDish(
        val date: String,
        val mealType: String,
        val name: String,
        val payload: DishUIBean
    ) :
        ActionDishInfo(3)

    data class UpdateDish(
        val date: String,
        val mealType: String,
        val name: String,
        val payload: DishUIBean
    ) :
        ActionDishInfo(4)
}