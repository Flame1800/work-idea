package com.sielom.idea_working.model

import com.google.gson.annotations.SerializedName

data class Comment(val id: Int, @SerializedName("comment") val text: String?)