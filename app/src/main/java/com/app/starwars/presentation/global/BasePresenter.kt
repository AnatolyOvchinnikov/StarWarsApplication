package com.app.starwars.presentation.global

import com.app.starwars.di.DaggerAppComponent
import com.app.starwars.di.RoomModule
import com.app.starwars.presentation.main.StarWarsPresenter
import com.app.starwars.presentation.profile.UserDetailsPresenter
import com.arellomobile.mvp.MvpPresenter

open class BasePresenter<V : BaseView> : MvpPresenter<V>() {

    private var injector = DaggerAppComponent.builder()
        .roomModule(RoomModule())
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is StarWarsPresenter -> injector.inject(this)
            is UserDetailsPresenter -> injector.inject(this)
        }
    }
}