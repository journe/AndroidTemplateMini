package com.template.module.cheese

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.TimeUtils
import com.template.base.ktx.launchIO
import com.template.base.mvvm.vm.BaseViewModel
import com.template.base.utils.UiState
import com.template.common.retrofit.parseData
import com.template.module.bean.net.PostDishChangeBody
import com.template.module.bean.net.RespDailyPlanBean
import com.template.module.bean.net.toUIBean
import com.template.module.bean.ui.CheeseUIBean
import com.template.module.bean.ui.DateUIBean
import com.template.module.bean.ui.DishUIBean
import com.template.module.bean.ui.MealUIBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheeseViewModel @Inject constructor(private val repo: CheeseRepository) : BaseViewModel() {

    val dailyPlanBean = MutableLiveData<RespDailyPlanBean>()

    val cheeseUILiveData = MutableLiveData<List<CheeseUIBean>>()
    var dateUIBeans: MutableList<DateUIBean> = mutableListOf()
    var mealUIBeans: MutableList<MealUIBean> = mutableListOf()

    //在本地保存当前所有食谱
    private var cheeseUIBeanList = mutableListOf<CheeseUIBean>()

    private val _cheeseListFlow = MutableStateFlow<MutableList<CheeseUIBean>>(mutableListOf())
    val cheeseListFlow: StateFlow<MutableList<CheeseUIBean>> = _cheeseListFlow.asStateFlow()

    val mealUIlistFlow = cheeseListFlow.map { list ->
        list.flatMap { it.mealUIBeans }.toMutableList()
    }

    //        .asLiveData()
    var currentMealTabUIBean = MutableLiveData<MealUIBean>()
    var currentTitleAlpha = MutableLiveData(0f)

    var isFirstLoadMore = true
    private var firstInit = true

    fun dailyPlanQuery(date: String) {
        changeStateView(UiState.Loading())
        launchIO {
            repo.dailyPlanQuery(date).parseData {
                onSuccess = {
                    dailyPlanBean.postValue(it)
                    changeStateView(UiState.Success())
                }
            }
        }
    }

    fun dailyPlanFuture() {
        if (firstInit) {
            changeStateView(UiState.Loading())
            launchIO {
                repo.dailyPlanFuture().parseData {
                    onSuccess = {
                        handleMealsUiBean(it)
                        changeStateView(UiState.Success())
                        firstInit = false
                    }
                    onComplete = {
                        changeStateView(UiState.Success())
                    }
                }
            }
        }
    }

    fun dailyPlanFutureTest() {
        changeStateView(UiState.Loading())
        launchIO {
            repo.dailyPlanFuture().parseData {
                onSuccess = {
                    handleMealsUiBean(it)
                    changeStateView(UiState.Success())
                    firstInit = false
                }
                onComplete = {
                    changeStateView(UiState.Success())
                }
            }
        }
    }

    private fun handleMealsUiBean(beans: List<RespDailyPlanBean>) {
        //size 7
        val cheeseUIBeans = beans.map { it.toUIBean() }
        cheeseUIBeanList = cheeseUIBeans.toMutableList()
        dateUIBeans = cheeseUIBeans.mapIndexed { index, bean ->
            bean.date.apply { this.dateIndex = index }
        }.toMutableList()
        //size 7*3
        mealUIBeans = cheeseUIBeans.flatMap { it.mealUIBeans }.toMutableList()
        mealUIBeans.forEachIndexed { index, bean -> bean.mealIndex = index }
        cheeseUILiveData.postValue(cheeseUIBeanList)

        launchIO {
            _cheeseListFlow.emit(cheeseUIBeanList)
        }

    }

    fun findDateIndex(date: String): Int {
        return dateUIBeans.indexOfFirst { it.date == date }
    }


    fun findCheeseIndex(date: String): Int {
        return _cheeseListFlow.value.indexOfFirst { it.date.date == date }
    }

    fun dateIndexFindMealIndex(toIndex: Int): Int {
        val dateUIBean = dateUIBeans[toIndex]
        val mealIndex =
            mealUIBeans.indexOfFirst { mealUIBean -> mealUIBean.date.dateUI == dateUIBean.dateUI }
        return mealIndex
    }

    fun deleteDate(date: String) {
        val index = findCheeseIndex(date)
        if (index < 0 || index >= _cheeseListFlow.value.size) {
            return
        }

        val currentList = _cheeseListFlow.value
        val newList = currentList.toMutableList().apply {
            removeAt(index)
        }
        _cheeseListFlow.value = newList
    }

    fun copyDate(date: String, targetDate: String) {
        val index = findCheeseIndex(date)
        if (index < 0 || index >= _cheeseListFlow.value.size) {
            return
        }
        val targetIndex = findCheeseIndex(targetDate)
        if (targetIndex < 0 || targetIndex >= _cheeseListFlow.value.size) {
            return
        }

        val newList = _cheeseListFlow.value.toMutableList()
//        val newList = currentList.toMutableList().apply {
//            this[targetIndex].mealUIBeans = this[index].mealUIBeans.toMutableList()
//        }

        val oldCheese = newList[targetIndex]

        CheeseUIBean(
            date = oldCheese.date,
            nowMills = TimeUtils.getNowMills(),
            mealUIBeans = newList[index].mealUIBeans.toMutableList()
        )
//        val newCheese = oldCheese.copy(mealUIBeans = newList[index].mealUIBeans.toMutableList())
        newList[targetIndex] = CheeseUIBean(
            date = oldCheese.date,
            nowMills = TimeUtils.getNowMills(),
            mealUIBeans = newList[index].mealUIBeans.toMutableList()
        )

//        newList[targetIndex] = newList[index].copy()
        _cheeseListFlow.value = newList
    }

    fun replaceDate(date: String, payload: CheeseUIBean) {
        val index = findCheeseIndex(date)
        if (index < 0 || index >= _cheeseListFlow.value.size) {
            return
        }

        val currentList = _cheeseListFlow.value
        val newList = currentList.toMutableList().apply {
            this[index] = payload
        }
        _cheeseListFlow.value = newList
    }

    fun replaceMeal(date: String, mealType: String, payload: MealUIBean) {
        val index = findCheeseIndex(date)
        if (index < 0 || index >= _cheeseListFlow.value.size) {
            return
        }
        val newList = _cheeseListFlow.value.toMutableList()
        val oldCheese = newList[index]
        val updatedMealList = oldCheese.mealUIBeans.map {
            if (it.mealType == mealType)
                payload
            else
                it
        }.toMutableList()
        val newCheese = oldCheese.copy(mealUIBeans = updatedMealList)
        newList[index] = newCheese
        _cheeseListFlow.value = newList
    }

    fun findMealIndex(date: String, mealType: String): Int {
        return mealUIBeans.indexOfFirst { it.date.date == date && it.mealType == mealType }

    }

    fun deleteMeal(date: String, mealType: String) {

        val targetIndex = findCheeseIndex(date)
        if (targetIndex == -1) return // 处理索引无效的情况

        val newList = _cheeseListFlow.value.toMutableList()
        val oldCheese = newList[targetIndex]
        val updatedMealList = oldCheese.mealUIBeans.toMutableList().apply {
            removeIf { it.mealType == mealType }
        }
        val newCheese = oldCheese.copy(mealUIBeans = updatedMealList)

        newList[targetIndex] = newCheese
        _cheeseListFlow.value = newList
    }

    fun deleteDish(date: String, mealType: String, name: String) {
        val targetIndex = findCheeseIndex(date)
        if (targetIndex == -1) return // 处理索引无效的情况

        val newList = _cheeseListFlow.value.toMutableList()
        val oldCheese = newList[targetIndex]
        val updatedMealList = oldCheese.mealUIBeans.map { mealBean ->
            if (mealBean.mealType == mealType) {
                val newDishList = mealBean.dishes.toMutableList().apply {
                    removeIf { it.name == name }
                }
                mealBean.copy(dishes = newDishList)
            } else
                mealBean
        }.toMutableList()

        val newCheese = oldCheese.copy(mealUIBeans = updatedMealList)
        newList[targetIndex] = newCheese
        _cheeseListFlow.value = newList
    }

    fun replaceDishUser(date: String, mealType: String, name: String) {
        viewModelScope.launch {
            changeStateView(UiState.Loading())
            repo.replaceDish(PostDishChangeBody(name, date, mealType)).parseData {
                onSuccess = {
                    replaceDish(date, mealType, name, it.toUIBean())
                }
                onComplete = {
                    changeStateView(UiState.Success())
                }
            }
        }
    }

    fun replaceDish(date: String, mealType: String, name: String, payload: DishUIBean) {
        val index = findCheeseIndex(date)
        if (index < 0 || index >= _cheeseListFlow.value.size) {
            return
        }
        val newList = _cheeseListFlow.value.toMutableList()
        val oldCheese = newList[index]
        val updatedMealList = oldCheese.mealUIBeans.map { mealBean ->
            if (mealBean.mealType == mealType) {
                val updatedDishList = mealBean.dishes.map {
                    if (it.name == name)
                        payload
                    else
                        it
                }.toMutableList()
                mealBean.copy(dishes = updatedDishList)
            } else
                mealBean
        }.toMutableList()
        val newCheese = oldCheese.copy(mealUIBeans = updatedMealList)
        newList[index] = newCheese
        _cheeseListFlow.value = newList
    }

    fun updateDish(date: String, mealType: String, name: String, payload: DishUIBean) {
        val index = findCheeseIndex(date)
        if (index < 0 || index >= _cheeseListFlow.value.size) {
            return
        }
        val newList = _cheeseListFlow.value.toMutableList()
        val oldCheese = newList[index]
        val updatedMealList = oldCheese.mealUIBeans.map { mealBean ->
            if (mealBean.mealType == mealType) {
                val updatedDishList = mealBean.dishes.map {
                    if (it.name == name)
                        payload.apply {
                            if (nowMills == 0L) {
                                nowMills = TimeUtils.getNowMills()
                            }
                            if (payload.name.isEmpty()) {
                                payload.name = it.name
                            }
                            if (recommendationReasons.isEmpty())
                                recommendationReasons = it.recommendationReasons
                        }
                    else
                        it
                }.toMutableList()
                mealBean.copy(dishes = updatedDishList)
            } else
                mealBean
        }.toMutableList()
        val newCheese = oldCheese.copy(mealUIBeans = updatedMealList)
        newList[index] = newCheese
        _cheeseListFlow.value = newList
    }

    fun addDish(date: String, mealType: String, payload: DishUIBean) {
        val targetIndex = findCheeseIndex(date)
        if (targetIndex == -1) return // 处理索引无效的情况

        val newList = _cheeseListFlow.value.toMutableList()
        val oldCheese = newList[targetIndex]
        val updatedMealList = oldCheese.mealUIBeans.map { mealBean ->
            if (mealBean.mealType == mealType) {
                val newDishList = mealBean.dishes.toMutableList().apply {
                    add(payload)
                }
                mealBean.copy(dishes = newDishList)
            } else
                mealBean
        }.toMutableList()

        val newCheese = oldCheese.copy(mealUIBeans = updatedMealList)
        newList[targetIndex] = newCheese
        _cheeseListFlow.value = newList
    }
}