package team.duckie.app.android.ui.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import team.duckie.app.ui.main.duckchat.DuckChatScreen
import team.duckie.app.ui.main.home.HomeScreen
import team.duckie.app.ui.main.notification.NotificationScreen
import team.duckie.app.ui.main.notification.NotificationViewModel
import team.duckie.app.ui.main.search.SearchScreen
import team.duckie.app.ui.main.setting.screen.SettingScreen
import team.duckie.app.ui.main.setting.vm.SettingViewModel

@Composable
internal fun MainNavigation(
    settingVM: SettingViewModel,
    notificationVM: NotificationViewModel,
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
            NotificationScreen(
                navController = navController,
                notificationViewModel = notificationVM,
            )
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
