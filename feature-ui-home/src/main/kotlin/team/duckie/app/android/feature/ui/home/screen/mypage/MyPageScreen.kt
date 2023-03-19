/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.component.HeadLineTopAppBar
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.util.DpSize

@Composable
internal fun MyPageScreen(
    navigateToMyPage: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        HeadLineTopAppBar(
            title = stringResource(id = R.string.mypage),
            rightIcons = {
                Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                    QuackImage(
                        src = QuackIcon.Setting,
                        size = DpSize(all = 24.dp),
                    )
                    QuackImage(
                        src = QuackIcon.Notice,
                        size = DpSize(all = 24.dp),
                        onClick = navigateToMyPage,
                    )
                }
            },
        )
    }
}

@Stable
private val QuackIcon.Companion.Notice
    get() = R.drawable.home_ic_notice_24
