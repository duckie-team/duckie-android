/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.app.android.feature.ui.home.screen.guide

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.constants.GuideStep
import team.duckie.app.android.feature.ui.home.viewmodel.guide.HomeGuideViewModel
import team.duckie.app.android.shared.ui.compose.Spacer
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackSmallButtonType
import team.duckie.quackquack.ui.modifier.quackClickable

@Composable
internal fun HomeGuideScreen(
    modifier: Modifier = Modifier,
    vm: HomeGuideViewModel = activityViewModel(),
    onClose: () -> Unit,
) {
    val state = vm.collectAsState().value
    val pagerState = rememberPagerState()
    val pageCount = GuideStep.values().size
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = QuackColor.Black.composeColor.copy(alpha = 0.9F)),
        contentAlignment = Alignment.BottomCenter,
    ) {
        if (state.isGuideStarted) {
            HomeGuideStartScreen(
                modifier = Modifier.quackClickable(
                    // 하위 컴포저블의 clickable 막기 위함
                    rippleEnabled = false,
                    onClick = {},
                ),
                onNext = {
                    vm.updateGuideStared(started = false)
                },
                onClosed = onClose,
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                QuackBody2(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                        .padding(top = 40.dp, end = 16.dp),
                    text = stringResource(id = R.string.skip),
                    color = QuackColor.Gray3,
                    onClick = onClose,
                )
                HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
                    pageCount = pageCount,
                    state = pagerState,
                ) { index ->
                    HomeGuideFeatureScreen(
                        guideStep = GuideStep.getGuideStepByIndex(index),
                    )
                }
            }
            HomeGuideFeatureBottomLayout(
                pagerState = pagerState,
                pageCount = GuideStep.values().size,
                onNext = {
                    if (pagerState.currentPage == pageCount - 1) {
                        onClose()
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
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
    onClosed: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
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
            text = stringResource(id = R.string.guide_start_message),
            color = QuackColor.White,
            align = TextAlign.Center,
        )
        Spacer(space = 20.dp)
        QuackSmallButton(
            modifier = Modifier
                .size(118.dp, 44.dp),
            type = QuackSmallButtonType.Fill,
            text = stringResource(id = R.string.guide_start_accept_message),
            enabled = true,
            onClick = onNext,
        )
        Spacer(space = 16.dp)
        QuackBody2(
            text = stringResource(id = R.string.guide_start_deny_message),
            color = QuackColor.Gray2,
            onClick = onClosed,
        )
    }
}
