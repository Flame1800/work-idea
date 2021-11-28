package com.sielom.idea_working.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielom.idea_working.internet.NetworkService
import com.sielom.idea_working.model.Event
import com.sielom.idea_working.model.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PersonProjectsFragmentViewModel : ViewModel() {
    var userId: Int = -1

    private val api = NetworkService.retrofitService()

    val projectsLiveData: MutableLiveData<Event<List<Project>?>> = MutableLiveData()

    fun requestGetProjects() {
        projectsLiveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = suspend { api.getProjects() }.invoke()
                val projects = response.execute().body()
                if (projects == null || projects.isEmpty()) {
                    projectsLiveData.postValue(Event.error("У вас нет проектов"))
                    return@launch
                }

                val personProjects = mutableListOf<Project>()

                var foundProjects = projects.filter {
                    it.author?.id == userId
                }

                personProjects.addAll(foundProjects)

                foundProjects = projects.filter {
                    val foundUsers = it.users?.filter { user ->
                        user.id == userId
                    }

                    foundUsers?.isNotEmpty() == true
                }

                personProjects.addAll(foundProjects)

                if (personProjects.isEmpty()) {
                    projectsLiveData.postValue(Event.error("У вас нет проектов"))
                    return@launch
                }

                projectsLiveData.postValue(Event.success(personProjects))
            } catch (e: Exception) {
                e.printStackTrace()
                projectsLiveData.postValue(Event.error(e.message))
            }
        }
    }
}