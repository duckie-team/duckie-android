package land.sungbin.androidprojecttemplate.ui.main.home

sealed class HomeSideEffect {
    object NavigateToWriteFeed : HomeSideEffect()
    object NavigateToWriteDuckDeal : HomeSideEffect()
}