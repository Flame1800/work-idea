package com.sielom.idea_working.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielom.idea_working.internet.NetworkService
import com.sielom.idea_working.model.Category
import com.sielom.idea_working.model.Event
import com.sielom.idea_working.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SpecialistsFragmentViewModel : ViewModel() {
    private val api = NetworkService.retrofitService()

    var userId: Int = -1

    val specialistsLiveData: MutableLiveData<Event<List<User>?>> = MutableLiveData()
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

    fun requestGetUsers(category: Category?) {
        specialistsLiveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = suspend { api.getUsers() }.invoke()
                var users = response.execute().body()
                if (users == null) {
                    specialistsLiveData.postValue(Event.error("Специалистов нет"))
                } else {
                    if (category != null && category.id != -1) {
                        users = users.filter {
                            it.categories?.let { categories ->
                                category in categories
                            } ?: false
                        }
                    }

                    if (users.isNotEmpty()) {
                        for (user in users) {
                            val foundProjects = user.projects?.filter {
                                it.requestAccepted
                            }

                            user.countProjects = foundProjects?.size ?: 0
                        }

                        specialistsLiveData.postValue(Event.success(users))
                    } else {
                        specialistsLiveData.postValue(Event.error("Специалистов нет"))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                specialistsLiveData.postValue(Event.error(e.message))
            }
        }

    }
}