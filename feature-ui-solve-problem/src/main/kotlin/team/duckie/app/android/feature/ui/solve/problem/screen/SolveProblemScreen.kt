/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.screen

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import team.duckie.app.android.feature.ui.solve.problem.common.CloseAndPageTopBar
import team.duckie.app.android.feature.ui.solve.problem.common.DoubleButtonBottomBar

@Composable
internal fun SolveProblemScreen() {
    val currentPage by remember { mutableStateOf(0) }
    val totalPage by remember { mutableStateOf(0) }
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            CloseAndPageTopBar(
                onCloseClick = { },
                currentPage = currentPage,
                totalPage = totalPage,
            )
        },
        bottomBar = {
            DoubleButtonBottomBar(
                isFirstPage = currentPage == 0,
                onLeftButtonClick = { },
                onRightButtonClick = { }
            )
        }
    ) { padding ->
        padding
    }
}
