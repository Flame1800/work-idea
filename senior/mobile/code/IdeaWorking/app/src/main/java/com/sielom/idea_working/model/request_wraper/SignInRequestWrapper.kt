package com.sielom.idea_working.model.request_wraper

import com.google.gson.annotations.SerializedName

data class SignInRequestWrapper(
    @SerializedName("identifier") val email: String,
    val password: String
)