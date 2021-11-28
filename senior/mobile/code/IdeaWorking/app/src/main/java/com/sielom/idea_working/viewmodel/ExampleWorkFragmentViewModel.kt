package com.sielom.idea_working.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielom.idea_working.internet.NetworkService
import com.sielom.idea_working.model.Category
import com.sielom.idea_working.model.Event
import com.sielom.idea_working.model.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ExampleWorkFragmentViewModel: ViewModel() {
    private val api = NetworkService.retrofitService()

    val projectsLiveData: MutableLiveData<Event<List<Project>?>> = MutableLiveData()

    fun requestGetProjects() {
        projectsLiveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = suspend { api.getProjects() }.invoke()
                var projects = response.execute().body()

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
}