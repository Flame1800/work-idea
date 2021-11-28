package com.sielom.idea_working.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielom.idea_working.internet.NetworkService
import com.sielom.idea_working.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ProjectFragmentViewModel : ViewModel() {
    private val api = NetworkService.retrofitService()

    val projectLiveData: MutableLiveData<Event<Project?>> = MutableLiveData()
    val specializationsLiveData: MutableLiveData<Event<List<Category>?>> = MutableLiveData()
    val specialistsLiveData: MutableLiveData<Event<List<User>?>> = MutableLiveData()

    var userId: Int = -1
    var projectId: Int = -1

    fun requestGetProject() {
        projectLiveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = suspend { api.getProjects() }.invoke()
                var projects = response.execute().body()


                if (projects == null || projects.isEmpty()) {
                    projectLiveData.postValue(Event.error("Проекта не существует"))
                    return@launch
                }

                val foundProject = projects.filter {
                    it.id == projectId
                }

                if (foundProject.isEmpty()) {
                    projectLiveData.postValue(Event.error("Проекта не существует"))
                    return@launch
                }

                val project = foundProject[0]

                projectLiveData.postValue(Event.success(project))
            } catch (e: Exception) {
                e.printStackTrace()
                projectLiveData.postValue(Event.error(e.message))
            }
        }
    }

    fun requestGetSpecialization(requiredSpecializationIdList: List<Int>) {
        specializationsLiveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = suspend { api.getSpecializations() }.invoke()
                var specializations = response.execute().body()


                if (specializations == null || specializations.isEmpty()) {
                    specializationsLiveData.postValue(Event.error("Для проекта не указаны специализации"))
                    return@launch
                }

                val foundSpecializations = specializations.filter {
                    it.id in requiredSpecializationIdList
                }

                if (foundSpecializations.isEmpty()) {
                    specializationsLiveData.postValue(Event.error("Для проекта не указаны специализации"))
                    return@launch
                }

                specializationsLiveData.postValue(Event.success(foundSpecializations))
            } catch (e: Exception) {
                e.printStackTrace()
                specializationsLiveData.postValue(Event.error(e.message))
            }
        }
    }

    fun requestGetUsers(participants: List<Participant>) {
        specialistsLiveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = suspend { api.getUsers() }.invoke()
                var users = response.execute().body()
                if (users == null) {
                    specialistsLiveData.postValue(Event.error("Специалистов нет"))
                } else {

                    val userIdList: MutableList<Int> = mutableListOf()
                    for (participant in participants) {
                        userIdList.add(participant.userId ?: -1)
                    }

                    if (users.isNotEmpty()) {
                        users = users.filter {
                            it.id in userIdList && it.id != userId
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