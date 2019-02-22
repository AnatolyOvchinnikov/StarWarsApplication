package com.app.starwars.model.repository

import android.annotation.SuppressLint
import android.arch.paging.PagedList
import android.util.Log
import com.app.starwars.entity.UserWithMetadata
import com.app.starwars.model.data.db.StarWarsLocalCache
import com.app.starwars.model.data.server.StarWarsApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StarWarsBoundaryCallback( val query: String,
                                val api: StarWarsApi,
                                val cache: StarWarsLocalCache
) : PagedList.BoundaryCallback<UserWithMetadata>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    private var isRequestInProgress = false

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    override fun onZeroItemsLoaded() {
        Log.d("RepoBoundaryCallback", "onZeroItemsLoaded")
        requestAndSaveData(query)
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: UserWithMetadata) {
        Log.d("RepoBoundaryCallback", "onItemAtEndLoaded")
        itemAtEnd.user.userId?.let {
            lastRequestedPage = (it.toInt() / 10) + 1
        }
        requestAndSaveData(query)
    }

    @SuppressLint("CheckResult")
    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return
        isRequestInProgress = true
        api.articlesList(query, lastRequestedPage)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                cache.insert(response.results) {
                    lastRequestedPage++
                    isRequestInProgress = false
                }
            }, { error ->
                isRequestInProgress = false
            })
    }
}