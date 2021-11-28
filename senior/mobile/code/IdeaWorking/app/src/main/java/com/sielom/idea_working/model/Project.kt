package com.sielom.idea_working.model

import com.google.gson.annotations.SerializedName

data class Project(
    val id: Int,
    @SerializedName("name") val title: String,
    val description: String?,
    @SerializedName("Author") val author: User?,
    @SerializedName("users") val users: List<User>?,
    @SerializedName("category") val category: Category?,
    @SerializedName("project_participants") val participants: List<Participant>?,
    @SerializedName("projectComments") val comments: List<Comment>?,
    @SerializedName("requiredSpecializations") val requiredSpecializations: List<Map<String, String>>
)