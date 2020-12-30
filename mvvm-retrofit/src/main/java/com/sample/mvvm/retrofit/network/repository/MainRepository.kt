package com.sample.mvvm.retrofit.network.repository

import com.sample.mvvm.retrofit.network.api.ApiHelper

class MainRepository (private val apiHelper: ApiHelper) {

    suspend fun getUsers() =  apiHelper.getUsers()

}