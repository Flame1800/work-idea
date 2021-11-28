package com.sielom.idea_working.model

import com.google.gson.annotations.SerializedName

data class Category(val id: Int, @SerializedName("name") val title: String) {
    override fun toString(): String {
        return title
    }
}