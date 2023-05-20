/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.detail.common

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import team.duckie.app.android.feature.detail.R
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState
import team.duckie.app.android.common.compose.ui.DefaultProfile
import team.duckie.app.android.common.compose.GetHeightRatioW328H240
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSingeLazyRowTag
import team.duckie.quackquack.ui.component.QuackTagType
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.shape.SquircleShape

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
            QuackHeadLine2(text = state.exam.title)
            // 더보기 아이콘
            QuackImage(
                src = QuackIcon.More,
                size = DpSize(width = 24.dp, height = 24.dp),
                onClick = moreButtonClick,
            )
        }

        // 공백
        Spacer(modifier = Modifier.height(8.dp))
        // 내용
        state.exam.description?.run {
            QuackBody2(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = this,
            )
        }
        // 공백
        Spacer(modifier = Modifier.height(12.dp))
        // 태그 목록
        QuackSingeLazyRowTag(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalSpace = 4.dp,
            items = state.tagNames,
            tagType = QuackTagType.Grayscale(""),
            onClick = { index -> tagItemClick(state.tagNames[index]) },
        )
        // 공백
        Spacer(modifier = Modifier.height(24.dp))
        // 구분선
        QuackDivider()
        // 프로필 Layout
        DetailProfileLayout(
            state = state,
            followButtonClick = followButtonClick,
            profileClick = profileClick,
        )
        // 구분선
        QuackDivider()
        additionalInfo()
        // 점수 분포도 Layout
        // TODO(riflockle7): 기획 정해질 시 활성화
        // DetailScoreDistributionLayout(state)
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
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 12.dp,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 작성자 프로필 이미지
        if (state.profileImageUrl.isNotEmpty()) {
            QuackImage(
                src = state.profileImageUrl,
                shape = SquircleShape,
                size = DpSize(32.dp, 32.dp),
            )
        } else {
            Image(
                modifier = Modifier
                    .size(DpSize(32.dp, 32.dp))
                    .clip(SquircleShape),
                painter = painterResource(id = QuackIcon.DefaultProfile),
                contentDescription = null,
            )
        }

        // 공백
        Spacer(modifier = Modifier.width(8.dp))
        // 닉네임, 응시자, 일자 Layout
        Column(
            modifier = Modifier.quackClickable(
                onClick = { profileClick(state.exam.user?.id ?: 0) },
                rippleEnabled = false,
            ),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 댓글 작성자 닉네임
                QuackBody3(
                    text = state.nickname,
                    color = QuackColor.Black,
                )

                // 공백
                Spacer(modifier = Modifier.width(4.dp))
            }

            // 공백
            Spacer(modifier = Modifier.height(2.dp))

            // 덕티어 + 퍼센트, 태그
            QuackBody3(
                text = stringResource(
                    R.string.detail_tier_tag,
                    state.exam.user?.duckPower?.tier ?: "",
                    state.exam.user?.duckPower?.tag?.name ?: "",
                ),
                color = QuackColor.Gray2,
            )
        }
        // 공백
        Spacer(modifier = Modifier.weight(1f))

        // 팔로우 버튼
        QuackBody2(
            padding = PaddingValues(
                top = 8.dp,
                bottom = 8.dp,
            ),
            text = stringResource(
                if (isFollowed) {
                    R.string.detail_following
                } else {
                    R.string.detail_follow
                },
            ),
            color = if (isFollowed) QuackColor.Gray2 else QuackColor.DuckieOrange,
            onClick = followButtonClick,
        )
    }
}
