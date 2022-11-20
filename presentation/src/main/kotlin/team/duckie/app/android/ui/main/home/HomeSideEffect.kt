package team.duckie.app.android.ui.main.home

sealed class HomeSideEffect {
    object NavigateToWriteFeed : HomeSideEffect()
    object NavigateToWriteDuckDeal : HomeSideEffect()
}
