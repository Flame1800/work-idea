package com.sielom.idea_working.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielom.idea_working.internet.NetworkService
import com.sielom.idea_working.model.Category
import com.sielom.idea_working.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class AllCategoriesFragmentViewModel: ViewModel() {
    private val api = NetworkService.retrofitService()

    val categoryIdList: MutableList<Int> = mutableListOf()
    val categoriesLiveData: MutableLiveData<Event<List<Category>?>> = MutableLiveData()

    fun requestGetCategories() {
        categoriesLiveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = suspend { api.getCategories() }.invoke()
                val categories = response.execute().body()
                if (categories == null || categories.isEmpty()) {
                    categoriesLiveData.postValue(Event.error("Нет доступных категорий"))
                    return@launch
                }

                categoriesLiveData.postValue(Event.success(categories))
            } catch (e: Exception) {
                e.printStackTrace()
                categoriesLiveData.postValue(Event.error(e.message))
            }
        }
    }
}