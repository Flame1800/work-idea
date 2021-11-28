package com.sielom.idea_working.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielom.idea_working.internet.NetworkService
import com.sielom.idea_working.model.Event
import com.sielom.idea_working.model.User
import com.sielom.idea_working.model.request_wraper.SignInRequestWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SignInFragmentViewModel : ViewModel() {
    private val api = NetworkService.retrofitService()

    var email: String = ""
    var password: String = ""

    val userLiveData: MutableLiveData<Event<User>> = MutableLiveData()

    fun requestSignIn() {
        userLiveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    suspend { api.signIn(SignInRequestWrapper(email, password)) }.invoke()
                val responseWrapper = response.execute().body()
                if (responseWrapper?.user == null) {
                    userLiveData.postValue(Event.error("Не удалось войти"))
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