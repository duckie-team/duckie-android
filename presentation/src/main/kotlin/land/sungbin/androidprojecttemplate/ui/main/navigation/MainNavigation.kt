package land.sungbin.androidprojecttemplate.ui.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import land.sungbin.androidprojecttemplate.ui.main.duckchat.DuckChatScreen
import land.sungbin.androidprojecttemplate.ui.main.home.HomeScreen
import land.sungbin.androidprojecttemplate.ui.main.notification.NotificationScreen
import land.sungbin.androidprojecttemplate.ui.main.search.SearchScreen
import land.sungbin.androidprojecttemplate.ui.main.setting.screen.SettingScreen
import land.sungbin.androidprojecttemplate.ui.main.setting.vm.SettingViewModel

@Composable
internal fun MainNavigation(
    settingVM: SettingViewModel,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = MainScreens.Home.route,
    ) {
        composable(
            route = MainScreens.Home.route,
        ) {
            HomeScreen(
                navController = navController,
            )
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
        composable(
            route = MainScreens.Setting.route,
        ) {
            SettingScreen(
                settingVM = settingVM,
            )
        }
    }
}