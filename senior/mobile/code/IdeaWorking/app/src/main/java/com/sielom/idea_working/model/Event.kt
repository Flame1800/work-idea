package com.sielom.idea_working.model

data class Event<T>(val status: Status, val error: String?, val data: T?) {
    companion object {
        fun <T> loading(): Event<T> {
            return Event(Status.LOADING, null, null)
        }

        fun <T> error(message: String?): Event<T> {
            return Event(Status.ERROR, message, null)
        }

        fun <T> success(data: T?): Event<T> {
            return Event(Status.SUCCESS, null, data)
        }
    }
}