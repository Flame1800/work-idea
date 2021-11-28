package com.sielom.idea_working.model

import com.google.gson.annotations.SerializedName

data class Participant(
    val id: Int?,
    @SerializedName("specialization") val specializationId: Int?,
    @SerializedName("user") val userId: Int?
)