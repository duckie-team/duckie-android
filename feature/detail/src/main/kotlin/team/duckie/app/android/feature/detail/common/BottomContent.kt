/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.detail.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.QuackMaxWidthDivider
import team.duckie.app.android.common.compose.ui.icon.v2.FilledHeart
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.Heart
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackButton
import team.duckie.quackquack.ui.QuackButtonStyle
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

/** 상세 화면 최하단 Layout */
@Composable
internal fun DetailBottomLayout(
    modifier: Modifier,
    state: DetailState.Success,
    onHeartClick: () -> Unit,
    onChallengeClick: () -> Unit,
) {
    Column(modifier = modifier) {
        // 구분선
        QuackMaxWidthDivider()

        // 버튼 모음 Layout
        // TODO(riflockle7): 추후 Layout 을 활용해 처리하기
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // 좋아요 버튼
            QuackIcon(
                modifier = Modifier
                    .size(DpSize(24.dp, 24.dp))
                    .quackClickable(onClick = onHeartClick),
                icon = if (state.isHeart) {
                    QuackIcon.FilledHeart
                } else {
                    QuackIcon.Outlined.Heart
                },
            )

            // 버튼
            QuackButton(
                style = QuackButtonStyle.PrimaryFilledSmall,
                text = state.buttonTitle,
                onClick = onChallengeClick,
            )
        }
    }
}
