package land.sungbin.androidprojecttemplate.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
            val state by viewModel.state.collectAsState()
            val coroutineScope = rememberCoroutineScope()

            when (state) {
                LoginState.Initial -> {
                    LoginScreen(
                        onClickLogin = {
                            /**
                             * TODO: 서버 통신해서 카카오 로그인부터 연결하기
                             * viewModel.kakaoLogin()
                             */
                            coroutineScope.launch {
                                viewModel.kakaoLogin()
                            }
                        },
                    )
                }

                is LoginState.LoginFailed -> {}
                is LoginState.KakaoLoginFailed -> {}
            }

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