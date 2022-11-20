package team.duckie.app.android.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import team.duckie.app.ui.main.navigation.MainNavigation
import team.duckie.app.ui.main.navigation.homeBottomNavItems
import team.duckie.app.ui.main.setting.vm.SettingViewModel
import team.duckie.app.ui.main.notification.NotificationViewModel
import team.duckie.quackquack.ui.component.QuackBottomNavigation
import team.duckie.quackquack.ui.theme.QuackTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settingVM: SettingViewModel

    @Inject
    lateinit var notificationVM: NotificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            QuackTheme {
                var selectedNavigationIndex by remember { mutableStateOf(0) }
                val navController = rememberNavController()

                Column(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.weight(1f)) {
                        MainNavigation(
                            settingVM = settingVM,
                            navController = navController,
                            notificationVM = notificationVM,
                        )
                    }
                    QuackBottomNavigation(
                        selectedIndex = selectedNavigationIndex,
                        onClick = { index ->
                            selectedNavigationIndex = index
                            navController.navigate(homeBottomNavItems[index]) {
                                navController.graph.startDestinationRoute?.let { startDestinationRoute ->
                                    popUpTo(startDestinationRoute) { saveState = true }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        }
    }
}
