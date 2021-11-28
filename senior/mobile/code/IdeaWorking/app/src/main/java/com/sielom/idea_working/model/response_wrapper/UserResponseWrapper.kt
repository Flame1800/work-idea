package com.sielom.idea_working.model.response_wrapper

import com.sielom.idea_working.model.User

data class UserResponseWrapper(val jwt: String?, val user: User?)