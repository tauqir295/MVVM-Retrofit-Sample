package com.mvvm.retrofit.network.api

import com.mvvm.retrofit.network.model.Cat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * retrofit service interface for defining the end points
 */
interface ApiService {
    @GET("/v1/images/search?")
    suspend fun getCatList(@Query("limit") limit : Int, @Query("page") page : Int): Response<List<Cat>>
}