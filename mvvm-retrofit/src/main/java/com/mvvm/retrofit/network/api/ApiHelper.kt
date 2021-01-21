package com.mvvm.retrofit.network.api

import com.mvvm.retrofit.network.model.Cat
import retrofit2.Response

/**
 * Interface for API class
 */
interface ApiHelper {
    suspend fun getCats(limit:Int, page:Int): Response<List<Cat>>
}