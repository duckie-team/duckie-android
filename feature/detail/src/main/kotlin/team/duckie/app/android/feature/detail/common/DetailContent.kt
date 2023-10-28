/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class, ExperimentalLayoutApi::class)

package team.duckie.app.android.feature.detail.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import team.duckie.app.android.common.compose.GetHeightRatioW328H240
import team.duckie.app.android.common.kotlin.fastForEach
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.More
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackTag
import team.duckie.quackquack.ui.QuackTagStyle
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

/** 상세 화면 컨텐츠 Layout */
@Suppress("MagicNumber", "unused")
@Composable
internal fun DetailContentLayout(
    modifier: Modifier = Modifier,
    state: DetailState.Success,
    tagItemClick: (String) -> Unit,
    moreButtonClick: () -> Unit,
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
            QuackIcon(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .quackClickable(
                        onClick = moreButtonClick,
                    ),
                icon = QuackIcon.Outlined.More,
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
        FlowRow(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 4.dp,
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            state.tagNames.fastForEach { tagName ->
                key(tagName) {
                    QuackTag(
                        text = tagName,
                        style = QuackTagStyle.GrayscaleFlat,
                        onClick = { tagItemClick(tagName) },
                    )
                }
            }
        }

        // 공백
        Spacer(modifier = Modifier.height(24.dp))

        additionalInfo()
    }
}
