/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.friends

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.feature.friends.viewmodel.FriendsViewModel
import team.duckie.app.android.feature.friends.viewmodel.sideeffect.FriendsSideEffect
import team.duckie.app.android.navigator.feature.profile.ProfileNavigator
import team.duckie.app.android.common.android.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.android.ui.finishWithAnimation
import team.duckie.quackquack.ui.theme.QuackTheme
import javax.inject.Inject

@AndroidEntryPoint
class FriendsActivity : BaseActivity() {

    private val viewModel: FriendsViewModel by viewModels()

    @Inject
    lateinit var profileNavigator: ProfileNavigator

    private val profileActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val followStatus = result.data?.getBooleanExtra(Extras.FollowChangedStatus, true)
                val followUserId = result.data?.getIntExtra(Extras.FollowChangedUserId, -1)
                if (isValid(followStatus, followUserId)) {
                    require(followStatus != null) // for lint
                    require(followUserId != null)
                    viewModel.toggleFollowingStatus(followStatus, followUserId)
                }
            }
        }

    private fun isValid(followStatus: Boolean?, followUserId: Int?): Boolean {
        return !(followUserId == null || followUserId == -1) && followStatus != null
    }

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
                profileNavigator.activityResultLaunch(
                    launcher = profileActivityResultLauncher,
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.UserId, sideEffect.userId)
                    },
                )
            }
        }
    }
}
