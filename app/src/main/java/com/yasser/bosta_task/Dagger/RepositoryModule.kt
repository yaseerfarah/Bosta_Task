package com.yasser.nagwa.Dagger

import com.google.gson.GsonBuilder
import com.yasser.bosta_task.Constants
import com.yasser.bosta_task.Data.ApiEndpoints
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
 class RepositoryModule {

    @Provides
    @Singleton
    fun apiEndpoints(retrofit: Retrofit): ApiEndpoints {
        return retrofit.create(ApiEndpoints::class.java)
    }


    @Singleton
    @Provides
    fun retrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES).build()


        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Constants.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(client)
            .build()
    }
}