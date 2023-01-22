/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.start.exam.screen

import android.os.Bundle
import androidx.activity.compose.setContent
import team.duckie.app.android.shared.ui.compose.DuckieTodoScreen
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.quackquack.ui.theme.QuackTheme

class StartExamActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuackTheme {
                DuckieTodoScreen()
            }
        }
    }
}
