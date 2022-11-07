package land.sungbin.androidprojecttemplate.ui.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import land.sungbin.androidprojecttemplate.ui.navigator.DuckieNavigator
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: SplashViewModel

    @Inject
    lateinit var navigator: DuckieNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        setContent {
            LaunchedEffect(Unit) {
                delay(SPLASH_DELAY)
                viewModel.navigatePage(SplashPage.Second)
                delay(SPLASH_DELAY)
                viewModel.onCheckSession()
            }

            SplashScreen(
                viewModel = viewModel
            )

            LaunchedEffect(key1 = viewModel.effect) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        SplashSideEffect.NavigateToLogin -> {
                            navigator.navigateLoginScreen(this@SplashActivity, withFinish = true)
                        }

                        SplashSideEffect.NavigateToMain -> {
                            navigator.navigateMainScreen(this@SplashActivity, withFinish = true)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val SPLASH_DELAY = 1000L
    }
}