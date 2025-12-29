package com.template.module.cheese

import androidx.lifecycle.MutableLiveData
import com.template.base.mvvm.vm.BaseViewModel
import com.template.module.bean.ui.DishUIBean

class CheeseVPItemViewModel : BaseViewModel() {
    val dishUIBeanList = MutableLiveData(mutableListOf<DishUIBean>())

    var tempBeans = mutableListOf<DishUIBean>()
    fun setAllData(dishUIBeans: MutableList<DishUIBean>) {
        tempBeans = dishUIBeans
        dishUIBeanList.postValue(tempBeans)
    }

    fun delDish(name:String){
        val targetBean = tempBeans.firstOrNull { it.name==name }
        tempBeans.remove(targetBean)
        dishUIBeanList.postValue(tempBeans)
    }
}