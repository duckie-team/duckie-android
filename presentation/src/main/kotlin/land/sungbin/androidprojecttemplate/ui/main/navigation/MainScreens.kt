package land.sungbin.androidprojecttemplate.ui.main.navigation

enum class MainScreens(
    val route: String,
) {
    Home("LIST"),
    Search("SEARCH"),
    Notification("NOTIFICATION"),
    DuckChat("DUCK_CHAT"),
    Setting("SETTING"),
}

internal val homeBottomNavItems = listOf(
    MainScreens.Home.route,
    MainScreens.Search.route,
    MainScreens.Notification.route,
    MainScreens.DuckChat.route,
)