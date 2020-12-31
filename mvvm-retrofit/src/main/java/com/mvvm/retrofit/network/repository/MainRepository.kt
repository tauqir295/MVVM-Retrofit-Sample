package com.mvvm.retrofit.network.repository

import com.mvvm.retrofit.network.api.ApiHelper

class MainRepository (private val apiHelper: ApiHelper) {

    suspend fun getUsers() =  apiHelper.getUsers()

}