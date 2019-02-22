package com.app.starwars.di

import com.app.starwars.presentation.main.StarWarsPresenter
import com.app.starwars.presentation.profile.UserDetailsPresenter
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(RoomModule::class, NetworkModule::class))
interface AppComponent {

    fun inject(presenter: StarWarsPresenter)
    fun inject(presenter: UserDetailsPresenter)
}