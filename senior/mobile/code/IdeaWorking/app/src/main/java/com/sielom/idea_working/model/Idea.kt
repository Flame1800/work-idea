package com.sielom.idea_working.model

import com.google.gson.annotations.SerializedName

data class Idea(
    val id: Int,
    @SerializedName("Author") val user: User?,
    @SerializedName("minifiedDescription") val title: String = "",
    @SerializedName("fullDescription") val description: String = ""
)