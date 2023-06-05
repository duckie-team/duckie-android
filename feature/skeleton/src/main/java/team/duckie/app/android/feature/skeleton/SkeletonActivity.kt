/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.skeleton

import SkeletonViewModel
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.feature.skeleton.viewmodel.viewmodel.sideeffect.SkeletonSideEffect
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.theme.QuackTheme
import team.duckie.quackquack.ui.QuackText

/** UI 모듈 추가 skeleton(template) 액티비티 */
@AndroidEntryPoint
class SkeletonActivity : BaseActivity() {
    private val viewModel: SkeletonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuackTheme {
                DuckieScreen(viewModel.toString())
            }
        }

        viewModel.observe(
            lifecycleOwner = this,
            sideEffect = ::handleSideEffect,
        )
    }
}

private fun handleSideEffect(sideEffect: SkeletonSideEffect) {
    println(sideEffect.toString())
}

@Composable
fun DuckieScreen(text: String) {
    QuackText(
        modifier = Modifier,
        text = text,
        typography = QuackTypography.Body1,
    )
}
