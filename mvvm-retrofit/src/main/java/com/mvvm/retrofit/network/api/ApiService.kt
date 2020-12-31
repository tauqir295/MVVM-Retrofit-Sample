package com.mvvm.retrofit.network.api

import com.mvvm.retrofit.network.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

}