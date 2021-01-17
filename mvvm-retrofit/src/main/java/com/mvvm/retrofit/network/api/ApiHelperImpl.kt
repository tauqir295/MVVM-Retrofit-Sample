package com.mvvm.retrofit.network.api

import com.mvvm.retrofit.network.model.Cat
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getCats(limit: Int, page: Int): Response<List<Cat>> =
        apiService.getCatList(limit,page)
}