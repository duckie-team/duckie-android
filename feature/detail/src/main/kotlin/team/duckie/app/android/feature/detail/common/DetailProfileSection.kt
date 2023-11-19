/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.detail.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.VibrateOnTap
import team.duckie.app.android.common.compose.ui.QuackMaxWidthDivider
import team.duckie.app.android.common.compose.ui.quack.QuackProfileImage
import team.duckie.app.android.feature.detail.R
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackTitle2

/** 상세 화면 프로필 Section */
@Composable
fun DetailProfileSection(
    state: DetailState.Success,
    followButtonClick: () -> Unit,
    profileClick: (Int) -> Unit,
) {
    // 구분선
    QuackMaxWidthDivider()

    // 프로필 Layout
    DetailProfileLayout(
        state = state,
        followButtonClick = followButtonClick,
        profileClick = profileClick,
    )

    // 구분선
    QuackMaxWidthDivider()
}

/** 상세 화면 프로필 Layout */
@Composable
private fun DetailProfileLayout(
    state: DetailState.Success,
    followButtonClick: () -> Unit,
    profileClick: (Int) -> Unit,
) {
    val isFollowed = remember(state.isFollowing) { state.isFollowing }
    val onProfileClick = { profileClick(state.exam.user?.id ?: 0) }

    Row(
        modifier = Modifier
            .quackClickable(
                onClick = onProfileClick,
                rippleEnabled = false,
            )
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 작성자 프로필 이미지
        QuackProfileImage(
            profileUrl = state.profileImageUrl,
            size = DpSize(36.dp, 36.dp),
            onClick = onProfileClick,
        )

        // 공백
        Spacer(modifier = Modifier.width(8.dp))
        // 닉네임, 응시자, 일자 Layout
        Column {
            // 유저 닉네임
            QuackTitle2(text = state.nickname)
            // 덕티어 + 퍼센트, 태그
            QuackText(
                text = stringResource(
                    R.string.detail_tier_tag,
                    state.exam.user?.duckPower?.tier ?: "",
                    state.exam.user?.duckPower?.tag?.name ?: "",
                ),
                typography = QuackTypography.Body2.change(color = QuackColor.Gray1),
            )
        }
        // 공백
        Spacer(modifier = Modifier.weight(1f))
        // 팔로우 버튼
        VibrateOnTap { _, vibrate ->
            QuackText(
                modifier = Modifier
                    .padding(
                        PaddingValues(
                            top = 8.dp,
                            bottom = 8.dp,
                        ),
                    )
                    .quackClickable(
                        onClick = {
                            vibrate()
                            followButtonClick()
                        },
                    ),
                text = stringResource(
                    if (isFollowed) {
                        R.string.detail_following
                    } else {
                        R.string.detail_follow
                    },
                ),
                typography = QuackTypography.Body2.change(
                    color = if (isFollowed) {
                        QuackColor.Gray2
                    } else {
                        QuackColor.DuckieOrange
                    },
                ),
            )
        }
    }
}
