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

class InformationAboutPersonsFragmentViewModel : ViewModel() {
    private val api = NetworkService.retrofitService()

    val specialistsLiveData: MutableLiveData<Event<List<User?>>> = MutableLiveData()

    fun requestGetUsers() {
        specialistsLiveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = suspend { api.getUsers() }.invoke()
                var users = response.execute().body()
                if (users == null) {
                    specialistsLiveData.postValue(Event.error("Специалистов нет"))
                } else {
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