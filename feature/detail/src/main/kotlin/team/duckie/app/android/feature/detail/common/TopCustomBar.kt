/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.detail.common

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackIcon
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackTag
import team.duckie.quackquack.ui.QuackTagStyle
import team.duckie.quackquack.ui.trailingIcon
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

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
            .background(QuackColor.White.value)
            .padding(
                vertical = 6.dp,
                horizontal = 10.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        QuackImage(
            modifier = Modifier
                .padding(6.dp)
                .size(DpSize(24.dp, 24.dp))
                .quackClickable(
                    onClick = { activity.finish() },
                ),
            src = QuackIcon.ArrowBack.drawableId,
        )

        QuackTag(
            modifier = Modifier.trailingIcon(
                icon = QuackIcon.ArrowRight,
                onClick = { onTagClick(state.mainTagNames) },
            ),
            text = state.mainTagNames,
            style = QuackTagStyle.Outlined,
            selected = false,
            onClick = { onTagClick(state.mainTagNames) },
        )
    }
}
