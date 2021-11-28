package com.sielom.idea_working.model.request_wraper

import com.google.gson.annotations.SerializedName

data class UpdateUserRequestWrapper(
    @SerializedName("About") val about: String
)