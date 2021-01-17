package com.mvvm.retrofit.network.model

data class Cat(
    val breeds: List<Breed>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)