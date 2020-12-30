package com.sample.mvvm.retrofit.network.api

import com.sample.mvvm.retrofit.network.model.User
import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers(): Response<List<User>>
}