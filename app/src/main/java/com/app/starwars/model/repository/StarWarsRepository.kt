package com.app.starwars.model.repository

import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import android.util.Log
import com.app.starwars.entity.User
import com.app.starwars.entity.UserMetadata
import com.app.starwars.entity.UserWithMetadata
import com.app.starwars.model.data.db.StarWarsLocalCache
import com.app.starwars.model.data.server.StarWarsApi
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single

class StarWarsRepository(val api: StarWarsApi,
                         val cache: StarWarsLocalCache
) {
    fun search(query: String): Flowable<PagedList<UserWithMetadata>> {
        Log.d("GithubRepository", "New query: $query")

        val dataSourceFactory = cache.usersByName(query)
        val boundaryCallback = StarWarsBoundaryCallback(query, api, cache)

        val data = RxPagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .buildFlowable(BackpressureStrategy.LATEST)

        return data
    }

    fun getUserInfo(userId: Long): Single<User> {
        return api.getUserInfo(userId)
    }

    fun setVisited(userId: Long) {
        cache.updateuserMetadata(UserMetadata(userId, true))
    }

    companion object {
         const val DATABASE_PAGE_SIZE = 20
    }
}