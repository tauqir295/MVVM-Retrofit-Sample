package com.sample.mvvm.retrofit.network.api

import com.sample.mvvm.retrofit.network.model.User
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<List<User>> = apiService.getUsers()

}