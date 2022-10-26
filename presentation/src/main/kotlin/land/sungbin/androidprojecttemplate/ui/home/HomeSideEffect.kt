package land.sungbin.androidprojecttemplate.ui.home

sealed class HomeSideEffect {
    object NavigateToWriteFeed : HomeSideEffect()
    object NavigateToWriteDuckDeal : HomeSideEffect()
}