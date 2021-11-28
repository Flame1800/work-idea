package com.sielom.idea_working.internet


import com.sielom.idea_working.model.Category
import com.sielom.idea_working.model.Idea
import com.sielom.idea_working.model.Project
import com.sielom.idea_working.model.User
import com.sielom.idea_working.model.request_wraper.SignInRequestWrapper
import com.sielom.idea_working.model.request_wraper.SignUpRequestWrapper
import com.sielom.idea_working.model.request_wraper.UpdateUserRequestWrapper
import com.sielom.idea_working.model.response_wrapper.UserResponseWrapper
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @POST("auth/local/register")
    fun signUp(@Body signUpRequestWrapper: SignUpRequestWrapper): Call<UserResponseWrapper>

    @POST("auth/local")
    fun signIn(@Body signInRequestWrapper: SignInRequestWrapper): Call<UserResponseWrapper>

    @GET("users")
    fun getUsers(): Call<List<User>?>

    @GET("ideas")
    fun getIdeas(): Call<List<Idea>?>

    @GET("projects")
    fun getProjects(): Call<List<Project>?>

    @GET("Categories-of-specializations")
    fun getCategories(): Call<List<Category>?>

    @GET("specializations")
    fun getSpecializations(): Call<List<Category>?>

    @PUT("users/{id}")
    fun updateUser(
        @Path("id") id: Int,
        @Body updateUserRequestWrapper: UpdateUserRequestWrapper
    ): Call<User?>
}