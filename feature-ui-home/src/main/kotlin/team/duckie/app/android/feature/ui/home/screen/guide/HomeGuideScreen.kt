/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package team.duckie.app.android.feature.ui.home.screen.guide

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.shared.ui.compose.Spacer
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackSmallButtonType

@Composable
internal fun HomeGuideScreen(
    modifier: Modifier = Modifier,
    vm: HomeGuideViewModel = activityViewModel(),
    onClosed: () -> Unit,
) {
    val state = vm.collectAsState().value
    val pagerState = rememberPagerState()
    val pageCount = GuideStep.values().size
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = QuackColor.Black.composeColor.copy(alpha = 0.9F),
            ),
        contentAlignment = Alignment.BottomCenter,
    ) {
        if (state.isGuideStarted) {
            HomeGuideStartScreen(
                onNext = {
                    vm.updateGuideStared(started = false)
                },
                onClosed = onClosed,
            )
        } else {
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                pageCount = pageCount,
                state = pagerState,
            ) { index ->
                HomeGuideFeatureScreen(
                    pagerState = pagerState,
                    guideStep = GuideStep.getGuideStepByIndex(index),
                )
            }
            HomeGuideFeatureBottomLayout(
                pagerState = pagerState,
                pageCount = GuideStep.values().size,
                onNext = {
                    if (pagerState.currentPage == pageCount - 1) {
                        onClosed()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
            )
        }
    }
}

@Composable
private fun HomeGuideStartScreen(
    onNext: () -> Unit,
    onClosed: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.size(192.dp, 104.dp),
            painter = painterResource(id = R.drawable.bg_guide_character),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
        Spacer(space = 12.dp)
        QuackHeadLine1(
            text = "덕키에 온걸 환영한덕!\n나와 같이 한 번 살펴볼까?",
            color = QuackColor.White,
            align = TextAlign.Center,
        )
        Spacer(space = 20.dp)
        QuackSmallButton(
            modifier = Modifier
                .size(118.dp, 44.dp),
            type = QuackSmallButtonType.Fill,
            text = "좋아, 가보자고!",
            enabled = true,
            onClick = onNext,
        )
        Spacer(space = 16.dp)
        QuackBody2(
            text = "아냐, 괜찮아",
            color = QuackColor.Gray2,
            onClick = onClosed
        )
    }
}
