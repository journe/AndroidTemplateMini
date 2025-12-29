package com.template.common.retrofit

/**
 * 接口公共地址
 */
internal object NetBaseUrlConstant {

    const val MAIN_URL = "http://example.com/"


    //新增/替换一道菜
    const val DISH_SAVE = "dish/save"

    const val DISH_CHANGE = "dish/change"

    //删除菜品
    const val DISH_DELETE = "dish/delete/{id}"

    //新增
    const val DAILYPLAN_SAVE = "dailyPlan/save"

    //del天纬度菜单列表
    const val DAILYPLAN_DELETE = "dailyPlan/delete/{date}"

    //获取天纬度菜单列表
    const val DAILYPLAN_QUERY = "dailyPlan/queryByDate/{date}"

    //获取天纬度菜单列表-7天
    const val DAILYPLAN_FUTURE = "dailyPlan/queryFuturePlan"

    //保存餐谱（早饭/午饭/晚饭）
    const val MEAL_SAVE = "meal/save"

    //获取餐谱菜单（早饭/午饭/晚饭）
    const val MEAL_QUERY = "meal/query"

    //删除餐谱（早饭/午饭/晚饭）
    const val MEAL_DELETE = "meal/delete"

    const val LOGIN = "user/login"
}