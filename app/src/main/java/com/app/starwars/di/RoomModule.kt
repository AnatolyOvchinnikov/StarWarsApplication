package com.app.starwars.di

import android.arch.persistence.room.Room
import com.app.starwars.StarWarsApplication
import com.app.starwars.model.data.server.StarWarsApi
import com.app.starwars.model.data.db.StarWarsDatabase
import com.app.starwars.model.data.db.StarWarsLocalCache
import com.app.starwars.model.data.db.UserDao
import com.app.starwars.model.interactor.StarWarsInteractor
import com.app.starwars.model.repository.StarWarsRepository
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton


@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideStarWarsDatabase(): StarWarsDatabase {
        return Room.databaseBuilder(StarWarsApplication.applicationContext(),
            StarWarsDatabase::class.java, "starwars.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideUsersDao(appDatabase: StarWarsDatabase): UserDao {
        return appDatabase.usersDao()
    }

    @Singleton
    @Provides
    fun provideLocalCache(usersDao: UserDao, ioExecutor: Executor
    ): StarWarsLocalCache {
        return StarWarsLocalCache(usersDao, ioExecutor)
    }

    @Singleton
    @Provides
    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @Singleton
    @Provides
    fun provideRepository(api: StarWarsApi, cache: StarWarsLocalCache): StarWarsRepository {
        return StarWarsRepository(api, cache)
    }

    @Singleton
    @Provides
    fun provideInteractor(appRepository: StarWarsRepository): StarWarsInteractor {
        return StarWarsInteractor(appRepository)
    }













//    @Provides
//    fun provideOkHttpClient(): OkHttpClient {
//        val httpClientBuilder = OkHttpClient.Builder()
//        httpClientBuilder.addNetworkInterceptor(ErrorResponseInterceptor())
//        if (BuildConfig.DEBUG) {
//            val httpLogInterceptor = HttpLoggingInterceptor()
//            httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            httpClientBuilder.addNetworkInterceptor(httpLogInterceptor)
//        }
//        return httpClientBuilder.build()
//    }
//
//    @Provides
//    fun provideGson(): Gson {
//        return GsonBuilder()
//            .setPrettyPrinting()
//            .setDateFormat(StarWarsApi.SERVER_DATE_FORMAT)
//            .create()
//    }
//
//    @Provides
//    fun provideApiClient(okHttpClient: OkHttpClient, gson: Gson): StarWarsApi {
//        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .client(okHttpClient)
//            .baseUrl(BuildConfig.BaseURL)
//            .build()
//            .create(StarWarsApi::class.java)
//    }
}