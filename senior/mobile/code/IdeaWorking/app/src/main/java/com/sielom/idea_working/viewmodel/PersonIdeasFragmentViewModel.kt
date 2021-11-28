package com.sielom.idea_working.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielom.idea_working.internet.NetworkService
import com.sielom.idea_working.model.Event
import com.sielom.idea_working.model.Idea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PersonIdeasFragmentViewModel : ViewModel() {
    private val api = NetworkService.retrofitService()

    var userId = -1

    val ideasLiveData: MutableLiveData<Event<List<Idea>>> = MutableLiveData()

    fun requestGetIdeas() {
        ideasLiveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = suspend { api.getIdeas() }.invoke()
                val ideas = response.execute().body()
                if (ideas == null || ideas.isEmpty()) {
                    ideasLiveData.postValue(Event.success(null))
                } else {
                    val foundIdeas = ideas.filter {
                        it.user?.id == userId
                    }

                    ideasLiveData.postValue(Event.success(foundIdeas))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ideasLiveData.postValue(Event.error(e.message))
            }
        }
    }
}