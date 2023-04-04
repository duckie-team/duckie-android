/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import team.duckie.app.android.feature.ui.profile.screen.OtherProfileScreen
import team.duckie.app.android.feature.ui.profile.viewmodel.ProfileViewModel
import team.duckie.app.android.navigator.feature.detail.DetailNavigator
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.quackquack.ui.theme.QuackTheme
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {
    private val viewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var detailNavigator: DetailNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuackTheme {
                val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
                OtherProfileScreen(
                    userProfile = state.userProfile,
                    isLoading = true,
                    follow = true,
                    onClickFollow = {},
                )
            }
        }


    }


}
