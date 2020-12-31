package com.sample.mvvm.retrofit.di

import android.content.Context
import com.sample.mvvm.retrofit.BuildConfig
import com.sample.mvvm.retrofit.network.api.ApiService
import com.sample.mvvm.retrofit.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@InstallIn(ApplicationComponent::class)
@Module
object CustomModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {

        return if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        } else OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    fun provideNetworkHelper(context: Context) = NetworkHelper(context)

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String = "https://5e510330f2c0d300147c034c.mockapi.io/"
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}