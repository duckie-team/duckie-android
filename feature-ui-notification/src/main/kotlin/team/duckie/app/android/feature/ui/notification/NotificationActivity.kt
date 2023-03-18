/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.notification

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.feature.ui.notification.screen.NotificationScreen
import team.duckie.app.android.feature.ui.notification.viewmodel.NotificationSideEffect
import team.duckie.app.android.feature.ui.notification.viewmodel.NotificationViewModel
import team.duckie.app.android.navigator.feature.home.HomeNavigator
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.color.QuackColor
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActivity : BaseActivity() {

    private val viewModel: NotificationViewModel by viewModels()

    @Inject
    lateinit var homeNavigator: HomeNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BackHandler {
                finishWithAnimation()
            }
            NotificationScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = QuackColor.White.composeColor)
                    .systemBarsPadding()
                    .navigationBarsPadding()
                    .padding(top = 12.dp)
                    .padding(horizontal = 12.dp),
            )
        }
        viewModel.observe(
            lifecycleOwner = this,
            sideEffect = ::handleSideEffect,
        )
    }

    private fun handleSideEffect(sideEffect: NotificationSideEffect) {
        when (sideEffect) {
            NotificationSideEffect.FinishActivity -> {
                finishWithAnimation()
            }

            NotificationSideEffect.NavigateToMyPage -> {
                homeNavigator.navigateFrom(activity = this)
            }

            is NotificationSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
            }
        }
    }
}
