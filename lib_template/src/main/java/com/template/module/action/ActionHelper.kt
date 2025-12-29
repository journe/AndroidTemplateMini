package com.template.module.action

import com.template.module.bean.ui.CheeseUIBean
import com.template.module.bean.ui.DishUIBean
import com.template.module.bean.ui.MealUIBean
import com.jeremyliao.liveeventbus.LiveEventBus

object ActionHelper {
    fun naviMain() {
        LiveEventBus
            .get(ActionNaviEvent::class.java)
            .post(ActionNaviEvent(ActionNaviInfo.NavigateMainFragment()))
    }

    fun naviHolder() {
        LiveEventBus
            .get(ActionNaviEvent::class.java)
            .post(ActionNaviEvent(ActionNaviInfo.NavigateHolderFragment()))
    }

    fun viewDay(date: String) {
        LiveEventBus
            .get(ActionDayEvent::class.java)
            .post(ActionDayEvent(ActionDayInfo.ViewDay(date)))
    }

    fun replaceDay(date: String, payload: CheeseUIBean = CheeseUIBean()) {
        LiveEventBus
            .get(ActionDayEvent::class.java)
            .post(ActionDayEvent(ActionDayInfo.ReplaceDay(date, payload)))
    }

    fun deleteDay(date: String) {
        LiveEventBus
            .get(ActionDayEvent::class.java)
            .post(ActionDayEvent(ActionDayInfo.DeleteDay(date)))
    }

    fun copyDay(date: String, targetDate: String) {
        LiveEventBus
            .get(ActionDayEvent::class.java)
            .post(ActionDayEvent(ActionDayInfo.CopyDay(date, targetDate)))
    }

    fun viewMeal(date: String, mealType: String) {
        LiveEventBus
            .get(ActionMealEvent::class.java)
            .post(ActionMealEvent(ActionMealInfo.ViewMeal(date, mealType)))
    }

    fun replaceMeal(
        date: String, mealType: String,
        payload: MealUIBean = MealUIBean()
    ) {
        LiveEventBus
            .get(ActionMealEvent::class.java)
            .post(ActionMealEvent(ActionMealInfo.ReplaceMeal(date, mealType, payload)))
    }

    fun deleteMeal(date: String, mealType: String) {
        LiveEventBus
            .get(ActionMealEvent::class.java)
            .post(ActionMealEvent(ActionMealInfo.DeleteMeal(date, mealType)))
    }

    fun addMeal(
        date: String, mealType: String,
        payload: MealUIBean = MealUIBean()
    ) {
        LiveEventBus
            .get(ActionMealEvent::class.java)
            .post(ActionMealEvent(ActionMealInfo.AddMeal(date, mealType)))
    }

    fun searchDish(mealName: String) {
        LiveEventBus
            .get(ActionDishEvent::class.java)
            .post(ActionDishEvent(ActionDishInfo.SearchDish(mealName)))
    }

    fun replaceDish(
        date: String,
        mealType: String,
        name: String,
        payload: DishUIBean = DishUIBean()
    ) {
        LiveEventBus
            .get(ActionDishEvent::class.java)
            .post(ActionDishEvent(ActionDishInfo.ReplaceDish(date, mealType, name, payload)))
    }

    fun deleteDish(date: String, mealType: String, name: String) {
        LiveEventBus
            .get(ActionDishEvent::class.java)
            .post(ActionDishEvent(ActionDishInfo.DeleteDish(date, mealType, name)))
    }

    fun updateDish(
        date: String,
        mealType: String,
        name: String,
        payload: DishUIBean = DishUIBean()
    ) {
        LiveEventBus
            .get(ActionDishEvent::class.java)
            .post(ActionDishEvent(ActionDishInfo.UpdateDish(date, mealType, name,payload)))
    }

    fun addDish(date: String, mealType: String, payload: DishUIBean = DishUIBean()) {
        LiveEventBus
            .get(ActionDishEvent::class.java)
            .post(ActionDishEvent(ActionDishInfo.AddDish(date, mealType, payload)))
    }
}