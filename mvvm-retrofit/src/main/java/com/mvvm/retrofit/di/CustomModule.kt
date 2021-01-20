package com.mvvm.retrofit.di

import android.content.Context
import com.mvvm.retrofit.BuildConfig
import com.mvvm.retrofit.network.api.ApiHelper
import com.mvvm.retrofit.network.api.ApiHelperImpl
import com.mvvm.retrofit.network.api.ApiService
import com.mvvm.retrofit.network.repository.MainRepository
import com.mvvm.retrofit.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideNetworkHelper(@ApplicationContext appContext: Context) = NetworkHelper(appContext)

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://api.thecatapi.com/")
            .client(okHttpClient)
            .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    fun provideApiHelper(apiService: ApiService): ApiHelper = ApiHelperImpl(apiService)

    @Provides
    fun provideMainRepo(apiHelper: ApiHelper): MainRepository = MainRepository(apiHelper)
}