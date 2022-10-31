package land.sungbin.androidprojecttemplate.ui.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import land.sungbin.androidprojecttemplate.ui.navigator.DuckieNavigator
import land.sungbin.androidprojecttemplate.util.UserHolder
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: DuckieNavigator

    @Inject
    lateinit var userHolder: UserHolder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        setContent {
            SplashScreen(
                onCheckSession = {
                    when (userHolder.hasSession()) {
                        true -> navigator.navigateMainScreen(this, withFinish = true)
                        else -> navigator.navigateLoginScreen(this, withFinish = true)
                    }
                }
            )
        }
    }
}