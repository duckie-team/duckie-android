package land.sungbin.androidprojecttemplate.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import dagger.hilt.android.AndroidEntryPoint
import land.sungbin.androidprojecttemplate.ui.navigator.DuckieNavigator
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var navigator: DuckieNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        setContent {
            LoginScreen(viewModel = viewModel)

            LaunchedEffect(viewModel.effect) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        LoginSideEffect.NavigateToMain -> {
                            navigator.navigateMainScreen(this@LoginActivity, withFinish = true)
                        }

                        LoginSideEffect.NavigateToOnboard -> {
                            navigator.navigateOnboardScreen(this@LoginActivity, withFinish = true)
                        }
                    }
                }
            }

        }
    }
}