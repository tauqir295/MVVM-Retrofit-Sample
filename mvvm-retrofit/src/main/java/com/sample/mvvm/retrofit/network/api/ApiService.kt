package com.sample.mvvm.retrofit.network.api

import com.sample.mvvm.retrofit.network.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

}