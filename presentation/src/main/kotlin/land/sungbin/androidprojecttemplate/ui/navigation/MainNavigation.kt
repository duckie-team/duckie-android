package land.sungbin.androidprojecttemplate.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import land.sungbin.androidprojecttemplate.ui.duckchat.DuckChatScreen
import land.sungbin.androidprojecttemplate.ui.home.HomeScreen
import land.sungbin.androidprojecttemplate.ui.notification.NotificationScreen
import land.sungbin.androidprojecttemplate.ui.search.SearchScreen

@Composable
internal fun MainNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MainScreens.Home.route,
    ) {
        composable(
            route = MainScreens.Home.route,
        ) {
            HomeScreen()
        }
        composable(
            route = MainScreens.Search.route,
        ) {
            SearchScreen()
        }
        composable(
            route = MainScreens.Notification.route,
        ) {
            NotificationScreen()
        }
        composable(
            route = MainScreens.DuckChat.route,
        ) {
            DuckChatScreen()
        }
    }
}