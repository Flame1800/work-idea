package com.sielom.idea_working.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val username: String,
    val email: String,
    @SerializedName("About") val about: String?,
    @SerializedName("categories_of_specializations") val categories: List<Category>?,
    @SerializedName("participationInTheProjectRequests") var projects: List<ParticipationInTheProjectRequests>?,
    var countProjects: Int?
)