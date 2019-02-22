package com.app.starwars.presentation.profile

import com.app.starwars.presentation.global.BaseView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserDetailsView : BaseView {
// Nothing
}
