package com.sielom.idea_working.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielom.idea_working.internet.NetworkService
import com.sielom.idea_working.model.Event
import com.sielom.idea_working.model.Project
import com.sielom.idea_working.model.User
import com.sielom.idea_working.model.request_wraper.UpdateUserRequestWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PersonDataFragmentViewModel : ViewModel() {
    private val api = NetworkService.retrofitService()

    var userId: Int = -1
    var about: String = ""
    var categoryIdList: MutableList<Int> = mutableListOf()

    val userLiveData: MutableLiveData<Event<User?>> = MutableLiveData()
    val saveUserLiveData: MutableLiveData<Event<String>> = MutableLiveData()
    val countProjectLiveData: MutableLiveData<Event<Int>> = MutableLiveData()

    fun requestGetUserInformation() {
        userLiveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = suspend { api.getUsers() }.invoke()
                val users = response.execute().body()
                if (users == null) {
                    userLiveData.postValue(Event.error("Пользователь не найден"))
                } else {
                    val foundedUsers = users.filter {
                        it.id == userId
                    }

                    if (foundedUsers.isNotEmpty()) {
                        userLiveData.postValue(Event.success(foundedUsers[0]))
                    } else {
                        userLiveData.postValue(Event.error("Пользователь не найден"))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                userLiveData.postValue(Event.error(e.message))
            }
        }
    }

    fun requestSaveUserInformation() {
        saveUserLiveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = suspend { api.updateUser(userId, UpdateUserRequestWrapper(about)) }.invoke()
                val v = response.execute().body()

                saveUserLiveData.postValue(Event.success("success"))

            } catch (e: Exception) {
                e.printStackTrace()
                saveUserLiveData.postValue(Event.error(e.message))
            }
        }
    }

    fun requestGetCountProjects() {
        countProjectLiveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = suspend { api.getProjects() }.invoke()
                val projects = response.execute().body()
                if (projects == null || projects.isEmpty()) {
                    countProjectLiveData.postValue(Event.error("У вас нет проектов"))
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
                    countProjectLiveData.postValue(Event.error("У вас нет проектов"))
                    return@launch
                }

                countProjectLiveData.postValue(Event.success(personProjects.size))
            } catch (e: Exception) {
                e.printStackTrace()
                countProjectLiveData.postValue(Event.error(e.message))
            }
        }
    }

}