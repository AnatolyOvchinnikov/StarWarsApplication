package com.app.starwars.presentation.profile

import android.annotation.SuppressLint
import android.databinding.ObservableField
import com.app.starwars.entity.User
import com.app.starwars.model.interactor.StarWarsInteractor
import com.app.starwars.presentation.global.BasePresenter
import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class UserDetailsPresenter : BasePresenter<UserDetailsView>() {

    var userObservable = ObservableField<User>()

    @SuppressLint("CheckResult")
    fun loadList(userId: Long) {
        appInteractor.getUserInfo(userId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { viewState.showProgress(true) }
            .doAfterTerminate {
                viewState.showProgress(false)
            }
            .subscribe({ response ->
                userObservable.set(response)
            }, { error ->
                viewState.showProgress(false)
                viewState.showError(error)
                Timber.e(error)
            })
    }

    @Inject
    lateinit var appInteractor: StarWarsInteractor

    fun setVisited() {
        userObservable.get()?.userId?.let {
            appInteractor.setVisited(it)
        }
    }

}