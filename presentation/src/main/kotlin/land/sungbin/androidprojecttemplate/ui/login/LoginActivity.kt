package land.sungbin.androidprojecttemplate.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import land.sungbin.androidprojecttemplate.ui.navigator.DuckieNavigator
import land.sungbin.androidprojecttemplate.util.EventObserver
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var navigator: DuckieNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        observeViewModel()
    }

    private fun initView() {
        setContent {
            LoginScreen(
                onClickLogin = {
                    /**
                     * TODO: 서버 통신해서 카카오 로그인부터 연결하기
                     * viewModel.kakaoLogin()
                     */
                    navigator.navigateOnboardScreen(this@LoginActivity, withFinish = true)
                },
            )
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            loginSuccess.observe(this@LoginActivity, EventObserver {
                navigator.navigateMainScreen(this@LoginActivity, withFinish = true)
            })
            signUpSuccess.observe(this@LoginActivity, EventObserver {
                navigator.navigateOnboardScreen(this@LoginActivity, withFinish = true)
            })
        }
    }
}