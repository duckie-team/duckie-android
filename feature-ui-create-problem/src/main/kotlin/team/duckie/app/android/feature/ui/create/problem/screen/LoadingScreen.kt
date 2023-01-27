/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.quackquack.ui.color.QuackColor

@Composable
internal fun LoadingScreen(
    viewModel: CreateProblemViewModel = activityViewModel(),
    modifier: Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch { viewModel.initState() }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // TODO(riflockle7): 추후 DuckieCircularProgressIndicator.kt 와 합치거나 꽥꽥 컴포넌트로 필요
        CircularProgressIndicator(
            color = QuackColor.DuckieOrange.composeColor,
        )
    }
}
