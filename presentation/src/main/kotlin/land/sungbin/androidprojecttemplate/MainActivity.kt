package land.sungbin.androidprojecttemplate

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
import land.sungbin.androidprojecttemplate.home.HomeScreen
import land.sungbin.androidprojecttemplate.home.HomeViewModel
import team.duckie.quackquack.ui.component.QuackBottomNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedNavigationIndex by remember {
                mutableStateOf(0)
            }
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
                    HomeScreen(
                        HomeViewModel()
                    )
                }
                QuackBottomNavigation(
                    selectedIndex = selectedNavigationIndex,
                    onClick = { index ->
                        selectedNavigationIndex = index
                    },
                )
            }
        }
    }
}
