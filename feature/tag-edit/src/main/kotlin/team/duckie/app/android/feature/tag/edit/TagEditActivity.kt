/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.tag.edit

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.common.android.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.feature.tag.edit.screen.TagEditScreen
import team.duckie.app.android.feature.tag.edit.viewmodel.TagEditSideEffect
import team.duckie.app.android.feature.tag.edit.viewmodel.TagEditViewModel
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.theme.QuackTheme

@AndroidEntryPoint
class TagEditActivity : BaseActivity() {
    private val viewModel: TagEditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuackTheme {
                TagEditScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = QuackColor.White.value)
                        .systemBarsPadding(),
                )
            }
        }

        viewModel.observe(
            lifecycleOwner = this@TagEditActivity,
            sideEffect = ::handleSideEffect,
        )
    }

    private fun handleSideEffect(sideEffect: TagEditSideEffect) {
        when (sideEffect) {
            is TagEditSideEffect.ReportError -> {
                sideEffect.exception.reportToCrashlyticsIfNeeded()
            }

            is TagEditSideEffect.FinishTagEdit -> {
                finish()
            }
        }
    }
}
