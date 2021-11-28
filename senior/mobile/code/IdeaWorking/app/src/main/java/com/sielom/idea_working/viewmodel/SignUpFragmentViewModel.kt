package com.sielom.idea_working.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielom.idea_working.internet.NetworkService
import com.sielom.idea_working.model.Event
import com.sielom.idea_working.model.User
import com.sielom.idea_working.model.request_wraper.SignUpRequestWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SignUpFragmentViewModel : ViewModel() {
    private val api = NetworkService.retrofitService()

    val userLiveData: MutableLiveData<Event<User>> = MutableLiveData()

    var email: String = ""
    var username: String = ""
    var password: String = ""

    fun requestSignUp() {
        userLiveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    suspend { api.signUp(SignUpRequestWrapper(username, email, password)) }.invoke()
                val responseWrapper = response.execute().body()
                if (responseWrapper?.user == null) {
                    userLiveData.postValue(Event.error("Не заренистрирован"))
                } else {
                    userLiveData.postValue(Event.success(responseWrapper.user))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                userLiveData.postValue(Event.error(e.message))
            }

        }
    }
}