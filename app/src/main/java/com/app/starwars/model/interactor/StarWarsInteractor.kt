package com.app.starwars.model.interactor

import android.arch.paging.PagedList
import com.app.starwars.entity.User
import com.app.starwars.entity.UserWithMetadata
import com.app.starwars.model.repository.StarWarsRepository
import io.reactivex.Flowable
import io.reactivex.Single

class StarWarsInteractor (
        val appRepository: StarWarsRepository
) {

    fun loadList(search: String): Flowable<PagedList<UserWithMetadata>> {
        return appRepository.search(search)
    }

    fun getUserInfo(userId: Long): Single<User> {
        return appRepository.getUserInfo(userId)
    }

    fun setVisited(userId: Long) {
        appRepository.setVisited(userId)
    }
}