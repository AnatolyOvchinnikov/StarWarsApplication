package com.app.starwars.model.data.server

import com.app.starwars.entity.User
import com.app.starwars.entity.UsersListResponce
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsApi {

    companion object {
        const val API_PATH = "/api"
        const val SERVER_DATE_FORMAT = "yyyy-MMM-dd hh:mm:ss a ZZ"
    }

    @Headers("Content-Type: application/json")
    @GET("${API_PATH}/people/")
    fun articlesList(@Query("search") search: String,
                     @Query("page") page: Int): Single<UsersListResponce>

    @Headers("Content-Type: application/json")
    @GET("${API_PATH}/people/{id}")
    fun getUserInfo(@Path("id") userId: Long): Single<User>
}