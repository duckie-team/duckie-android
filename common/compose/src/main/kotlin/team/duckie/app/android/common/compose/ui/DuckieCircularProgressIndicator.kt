/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import team.duckie.quackquack.material.QuackColor

/**
 * 로딩 상태를 표현하는 [CircularProgressIndicator]
 *
 * TODO(limsaehyun): 추후에 꽥꽥 Indicator로 교체하고 Materi
 */
@Composable
fun DuckieCircularProgressIndicator() {
    CircularProgressIndicator(
        color = QuackColor.DuckieOrange.composeColor,
    )
}
