/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.detail.common

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackCircleTag
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.icon.QuackIcon

/** 상세 화면에서 사용하는 TopAppBar */
@Composable
internal fun TopAppCustomBar(
    modifier: Modifier,
    state: DetailState.Success,
    onTagClick: (String) -> Unit,
) {
    val activity = LocalContext.current as Activity
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(QuackColor.White.composeColor)
            .padding(
                vertical = 6.dp,
                horizontal = 10.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        QuackImage(
            padding = PaddingValues(6.dp),
            src = QuackIcon.ArrowBack,
            size = DpSize(24.dp, 24.dp),
            rippleEnabled = false,
            onClick = { activity.finish() },
        )

        QuackCircleTag(
            text = state.mainTagNames,
            trailingIcon = QuackIcon.ArrowRight,
            isSelected = false,
            onClick = { onTagClick(state.mainTagNames) },
        )
    }
}
