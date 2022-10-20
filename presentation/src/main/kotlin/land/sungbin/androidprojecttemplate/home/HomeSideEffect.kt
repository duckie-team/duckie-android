package land.sungbin.androidprojecttemplate.home

sealed class HomeSideEffect {
    object NavigateToWriteFeed : HomeSideEffect()
    object NavigateToWriteDuckDeal : HomeSideEffect()
}