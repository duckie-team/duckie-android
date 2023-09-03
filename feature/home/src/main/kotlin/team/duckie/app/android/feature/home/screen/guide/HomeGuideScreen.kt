/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class, ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.home.screen.guide

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
import androidx.compose.runtime.remember
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
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.feature.home.R
import team.duckie.app.android.feature.home.constants.GuideStep
import team.duckie.app.android.feature.home.viewmodel.guide.HomeGuideViewModel
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackButton
import team.duckie.quackquack.ui.QuackButtonStyle
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

@Composable
internal fun HomeGuideScreen(
    modifier: Modifier = Modifier,
    vm: HomeGuideViewModel = activityViewModel(),
    onClose: () -> Unit,
) {
    val state = vm.collectAsState().value
    val pageCount = remember { GuideStep.values().size }
    val pagerState = rememberPagerState(

        pageCount = { pageCount },
    )
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = QuackColor.Black.value.copy(alpha = 0.9F)),
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
                QuackText(
                    modifier = Modifier
                        .quackClickable(onClick = onClose)
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                        .padding(top = 40.dp, end = 16.dp),
                    text = stringResource(id = R.string.skip),
                    typography = QuackTypography.Body2.change(color = QuackColor.Gray3),
                )
                HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
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
        QuackText(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.guide_start_message),
            typography = QuackTypography.HeadLine1.change(
                color = QuackColor.White,
                textAlign = TextAlign.Center,
            ),
        )
        Spacer(space = 20.dp)
        QuackButton(
            text = stringResource(id = R.string.guide_start_accept_message),
            style = QuackButtonStyle.PrimaryFilledSmall,
            modifier = Modifier.size(118.dp, 44.dp),
            enabled = true,
            onClick = onNext,
        )
        Spacer(space = 16.dp)
        QuackText(
            modifier = Modifier.quackClickable(onClick = onClosed),
            text = stringResource(id = R.string.guide_start_deny_message),
            typography = QuackTypography.Body2.change(color = QuackColor.Gray2),
        )
    }
}
