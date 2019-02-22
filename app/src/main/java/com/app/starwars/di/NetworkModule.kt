package com.app.starwars.di

import com.app.starwars.BuildConfig
import com.app.starwars.model.data.server.ErrorResponseInterceptor
import com.app.starwars.model.data.server.StarWarsApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
public class NetworkModule {

//    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addNetworkInterceptor(ErrorResponseInterceptor())
        if (BuildConfig.DEBUG) {
            val httpLogInterceptor = HttpLoggingInterceptor()
            httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addNetworkInterceptor(httpLogInterceptor)
        }
        return httpClientBuilder.build()
    }

//    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat(StarWarsApi.SERVER_DATE_FORMAT)
            .create()
    }

    @Singleton
    @Provides
    fun provideApiClient(okHttpClient: OkHttpClient, gson: Gson): StarWarsApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(BuildConfig.BaseURL)
            .build()
            .create(StarWarsApi::class.java)
    }
}