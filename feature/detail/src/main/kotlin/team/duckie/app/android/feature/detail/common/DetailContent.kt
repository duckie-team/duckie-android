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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import team.duckie.app.android.common.compose.GetHeightRatioW328H240
import team.duckie.app.android.common.compose.ui.QuackMaxWidthDivider
import team.duckie.app.android.common.compose.ui.quack.QuackProfileImage
import team.duckie.app.android.feature.detail.R
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.More
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackTag
import team.duckie.quackquack.ui.QuackTagStyle
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackTitle2
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

/** 상세 화면 컨텐츠 Layout */
@Suppress("MagicNumber")
@Composable
internal fun DetailContentLayout(
    modifier: Modifier = Modifier,
    state: DetailState.Success,
    tagItemClick: (String) -> Unit,
    moreButtonClick: () -> Unit,
    followButtonClick: () -> Unit,
    profileClick: (Int) -> Unit,
    additionalInfo: (@Composable () -> Unit),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        // 그림
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = GetHeightRatioW328H240)
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                )
                .clip(RoundedCornerShape(size = 8.dp)),
            model = state.exam.thumbnailUrl,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        // 공백
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // 제목
            QuackText(
                text = state.exam.title,
                typography = QuackTypography.HeadLine2,
            )

            // 더보기 아이콘
            QuackImage(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .quackClickable(
                        onClick = moreButtonClick,
                    ),
                src = QuackIcon.Outlined.More,
            )
        }

        // 공백
        Spacer(modifier = Modifier.height(8.dp))
        // 내용
        state.exam.description?.run {
            QuackText(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = this,
                typography = QuackTypography.Body2,
            )
        }
        // 공백
        Spacer(modifier = Modifier.height(12.dp))

        // 태그 목록
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 4.dp,
            ),
        ) {
            items(items = state.tagNames) { tagName ->
                QuackTag(
                    text = tagName,
                    style = QuackTagStyle.GrayscaleFlat,
                    onClick = { tagItemClick(tagName) },
                )
            }
        }

        // 공백
        Spacer(modifier = Modifier.height(24.dp))

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

        additionalInfo()
    }
}

/**
 * 상세 화면 프로필 Layout
 * TODO(riflockle7): 추후 공통화하기
 */
@Composable
private fun DetailProfileLayout(
    state: DetailState.Success,
    followButtonClick: () -> Unit,
    profileClick: (Int) -> Unit,
) {
    val isFollowed = remember(state.isFollowing) { state.isFollowing }

    Row(
        modifier = Modifier
            .quackClickable(
                onClick = { profileClick(state.exam.user?.id ?: 0) },
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
        QuackText(
            modifier = Modifier
                .padding(
                    PaddingValues(
                        top = 8.dp,
                        bottom = 8.dp,
                    ),
                )
                .quackClickable(
                    onClick = followButtonClick,
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
