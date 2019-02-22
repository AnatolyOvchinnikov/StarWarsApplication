package com.app.starwars.presentation.main

import android.arch.paging.PagedList
import com.app.starwars.entity.UserWithMetadata
import com.app.starwars.presentation.global.BaseView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface StarWarsView : BaseView {
    fun loadItems(list: PagedList<UserWithMetadata>)
}
