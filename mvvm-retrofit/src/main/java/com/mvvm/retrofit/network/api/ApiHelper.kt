package com.mvvm.retrofit.network.api

import com.mvvm.retrofit.network.model.User
import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers(): Response<List<User>>
}