/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.screen.edit

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.feature.profile.viewmodel.ProfileEditViewModel
import team.duckie.app.android.feature.profile.viewmodel.sideeffect.ProfileEditSideEffect
import team.duckie.app.android.common.android.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.common.android.ui.finishWithAnimation
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
        viewModel.observe(
            lifecycleOwner = this,
            sideEffect = ::handleSideEffect,
        )
    }

    private fun handleSideEffect(sideEffect: ProfileEditSideEffect) {
        when (sideEffect) {
            is ProfileEditSideEffect.NavigateBack -> finishWithAnimation()
            is ProfileEditSideEffect.ReportError -> {
                sideEffect.exception.reportToCrashlyticsIfNeeded()
            }
        }
    }
}
