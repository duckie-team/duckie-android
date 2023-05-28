/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.skeleton

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.theme.QuackTheme

/** UI 모듈 추가 skeleton(template) 액티비티 */
class SkeletonActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuackTheme {
                DuckieScreen()
            }
        }
    }
}

@Composable
fun DuckieScreen() {
    QuackText(
        modifier = Modifier,
        text = "",
        style = QuackTextStyle.Body1,
    )
}
