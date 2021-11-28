package com.sielom.idea_working.viewmodel

import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.sielom.idea_working.R
import com.sielom.idea_working.internet.NetworkService
import com.sielom.idea_working.model.Category
import com.sielom.idea_working.model.Event
import com.sielom.idea_working.model.Project
import com.sielom.idea_working.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ProjectsFragmentViewModel : ViewModel() {
    private val api = NetworkService.retrofitService()

    var userId: Int = -1

    val projectsLiveData: MutableLiveData<Event<List<Project>?>> = MutableLiveData()
    val categoriesLiveData: MutableLiveData<Event<List<Category>?>> = MutableLiveData()

    fun requestGetProjects(category: Category?) {
        projectsLiveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = suspend { api.getProjects() }.invoke()
                var projects = response.execute().body()

                if (category != null && category.id != -1) {
                    projects = projects?.filter {
                        it.category == category
                    }
                }

                if (projects == null || projects.isEmpty()) {
                    projectsLiveData.postValue(Event.error("Нет проектов"))
                    return@launch
                }

                projectsLiveData.postValue(Event.success(projects))
            } catch (e: Exception) {
                e.printStackTrace()
                projectsLiveData.postValue(Event.error(e.message))
            }
        }
    }

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