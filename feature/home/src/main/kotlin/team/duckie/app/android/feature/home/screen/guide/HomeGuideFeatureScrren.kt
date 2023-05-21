/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.app.android.feature.home.screen.guide

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.home.R
import team.duckie.app.android.feature.home.component.BaseBottomLayout
import team.duckie.app.android.feature.home.constants.GuideStep
import team.duckie.app.android.common.compose.ui.DuckieHorizontalPagerIndicator
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.modifier.quackClickable

@Composable
internal fun HomeGuideFeatureScreen(
    guideStep: GuideStep,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            QuackHeadLine2(
                modifier = Modifier
                    .padding(top = 41.dp),
                text = stringResource(id = guideStep.subtitle),
                color = QuackColor.White,
                align = TextAlign.Center,
            )
            QuackHeadLine1(
                text = stringResource(id = guideStep.title),
                color = QuackColor.DuckieOrange,
                align = TextAlign.Center,
            )
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .padding(horizontal = 22.dp)
                    .padding(top = 28.dp),
                painter = painterResource(id = guideStep.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopStart,
            )
        },
    )
}

@Composable
internal fun HomeGuideFeatureBottomLayout(
    pagerState: PagerState,
    pageCount: Int,
    onNext: () -> Unit,
) {
    if (pagerState.currentPage == pageCount - 1) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .quackClickable {
                    onNext()
                }
                .background(color = QuackColor.DuckieOrange.composeColor),
            contentAlignment = Alignment.Center,
        ) {
            QuackHeadLine2(
                text = stringResource(id = R.string.start_duckie),
                color = QuackColor.White,
            )
        }
    } else {
        BaseBottomLayout(
            centerContent = {
                DuckieHorizontalPagerIndicator(
                    pagerState = pagerState,
                    pageCount = pageCount,
                    spacing = 6.dp,
                )
            },
            trailingContent = {
                QuackSubtitle(
                    text = stringResource(id = R.string.next),
                    color = QuackColor.DuckieOrange,
                    onClick = onNext,
                )
            },
        )
    }
}
