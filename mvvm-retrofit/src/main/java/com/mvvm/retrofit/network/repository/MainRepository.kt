package com.mvvm.retrofit.network.repository

import com.mvvm.retrofit.network.api.ApiHelper

/**
 * Repository class used for managing different api calls
 */
class MainRepository (private val apiHelper: ApiHelper) {
    suspend fun getCats(limit:Int, page:Int) =  apiHelper.getCats(limit, page)
}