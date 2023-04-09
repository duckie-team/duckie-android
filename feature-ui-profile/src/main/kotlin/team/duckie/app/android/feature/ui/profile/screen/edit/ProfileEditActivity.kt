/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.screen.edit

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import team.duckie.app.android.feature.ui.profile.viewmodel.ProfileEditViewModel
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.quackquack.ui.theme.QuackTheme

@AndroidEntryPoint
class ProfileEditActivity : BaseActivity() {
    private val viewModel: ProfileEditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuackTheme {
                ProfileEditScreen(viewModel)
            }
        }
    }
}
