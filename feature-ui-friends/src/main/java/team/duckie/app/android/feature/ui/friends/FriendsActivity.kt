/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.friends

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.feature.ui.friends.viewmodel.FriendsViewModel
import team.duckie.app.android.feature.ui.friends.viewmodel.sideeffect.FriendsSideEffect
import team.duckie.app.android.navigator.feature.profile.ProfileNavigator
import team.duckie.app.android.util.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.const.Extras
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.theme.QuackTheme
import javax.inject.Inject

@AndroidEntryPoint
class FriendsActivity : BaseActivity() {

    private val viewModel: FriendsViewModel by viewModels()

    @Inject
    lateinit var profileNavigator: ProfileNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.observe(
            lifecycleOwner = this,
            sideEffect = ::handleSideEffect,
        )

        setContent {
            QuackTheme {
                FriendScreen(
                    viewModel = viewModel,
                    onPrevious = {
                        finishWithAnimation()
                    },
                )
            }
        }
    }

    private fun handleSideEffect(sideEffect: FriendsSideEffect) {
        when (sideEffect) {
            is FriendsSideEffect.ReportError -> {
                with(sideEffect.exception) {
                    printStackTrace()
                    reportToCrashlyticsIfNeeded()
                }
            }

            is FriendsSideEffect.NavigateToUserProfile -> {
                profileNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.UserId, sideEffect.userId)
                    },
                )
            }
        }
    }
}
