/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.app.android.feature.home.screen.music

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.compose.ui.Gray35
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackText

@Composable
internal fun HeroModule(
    pagerState: PagerState,
    items: ImmutableList<Exam>,
    onClickThumbnail: (Exam) -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Box {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            state = pagerState,
            pageSize = PageSize.Fixed(screenWidth.dp - 32.dp),
            pageSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) { page ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .quackClickable {
                        onClickThumbnail(items[page])
                    },
            ) {
                QuackImage(
                    modifier = Modifier.fillMaxSize(),
                    src = items.getOrNull(page % items.size)?.thumbnailUrl,
                    contentScale = ContentScale.FillBounds,
                )
            }
        }
        PageBadge(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    bottom = 8.dp,
                    end = 24.dp,
                ),
            currentPage = pagerState.targetPage + 1,
            totalPage = pagerState.pageCount,
        )
    }

}

@Composable
fun PageBadge(
    modifier: Modifier = Modifier,
    currentPage: Int,
    totalPage: Int,
) {
    Row(
        modifier = modifier.background(
            color = QuackColor.Gray35.value,
            shape = RoundedCornerShape(8.dp),
        ),
    ) {
        QuackText(
            modifier = Modifier
                .padding(start = 8.dp)
                .padding(vertical = 2.dp),
            text = currentPage.toString(),
            typography = QuackTypography.Body2.change(
                color = QuackColor.White,
            )
        )
        QuackText(
            modifier = Modifier
                .padding(end = 8.dp)
                .padding(vertical = 2.dp),
            text = "/$totalPage",
            typography = QuackTypography.Body2.change(
                color = QuackColor.Gray3,
            )
        )
    }
}
