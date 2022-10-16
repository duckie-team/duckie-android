package land.sungbin.androidprojecttemplate.home

import land.sungbin.androidprojecttemplate.domain.model.Feed

sealed class HomeState {
    object Loading: HomeState()
    data class Loaded(val feeds: List<Feed>): HomeState()
}