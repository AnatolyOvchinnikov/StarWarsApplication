package com.app.starwars.presentation.main

import android.annotation.SuppressLint
import com.app.starwars.model.interactor.StarWarsInteractor
import com.app.starwars.presentation.global.BasePresenter
import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class StarWarsPresenter: BasePresenter<StarWarsView>() {

    @Inject
    lateinit var appInteractor: StarWarsInteractor

    private val disposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    fun loadList(query: String = "") {
        disposable.clear()
        val queryDisposable = appInteractor.loadList(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                viewState.showProgress(true)
            }
            .subscribe({ response ->
                viewState.loadItems(response)
                viewState.showProgress(false)
            }, { error ->
                viewState.showProgress(false)
                viewState.showError(error)
                Timber.e(error)
            })
        disposable.add(queryDisposable)
    }

    fun onStop() {
        disposable.clear()
    }
}