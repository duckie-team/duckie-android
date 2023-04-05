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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.component.BaseBottomLayout
import team.duckie.app.android.shared.ui.compose.DuckieHorizontalPagerIndicator
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackSubtitle

private fun List<Measurable>.getPlaceable(layoutId: String, constraints: Constraints): Placeable =
    fastFirstOrNull { measureable ->
        measureable.layoutId == layoutId
    }?.measure(constraints) ?: npe()

private fun Constraints.centerHorizontally(width: Int, layoutDirection: LayoutDirection): Int =
    Alignment.CenterHorizontally.align(
        size = width,
        space = maxWidth,
        layoutDirection = layoutDirection,
    )

private object HomeGuideFeatureLayoutId {
    const val Skip = "HomeGuideFeatureSkip"
    const val Title1 = "HomeGuideFeatureTitle1"
    const val Title2 = "HomeGuideFeatureTitle2"
    const val Image = "HomeGuideFeatureImage"
}

private val HomeGuideFeatureMeasurePolicy = MeasurePolicy { measureables, constraints ->
    val looseConstraints = constraints.asLoose()
    val extraLooseConstraints = constraints.asLoose(width = true)

    val guideSkipPlaceable =
        measureables.getPlaceable(HomeGuideFeatureLayoutId.Skip, extraLooseConstraints)

    val guideTitle1Placeable =
        measureables.getPlaceable(HomeGuideFeatureLayoutId.Title1, looseConstraints)

    val guideTitle2Height = guideSkipPlaceable.height + guideTitle1Placeable.height

    val guideTitle2Placeable =
        measureables.getPlaceable(HomeGuideFeatureLayoutId.Title2, looseConstraints)

    val guideImagePlaceable =
        measureables.getPlaceable(HomeGuideFeatureLayoutId.Image, looseConstraints)

    layout(
        width = constraints.maxWidth,
        height = constraints.maxHeight,
    ) {
        guideSkipPlaceable.place(
            x = constraints.maxWidth - guideSkipPlaceable.width,
            y = 0,
        )
        guideTitle1Placeable.place(
            x = constraints.centerHorizontally(
                width = guideTitle1Placeable.width,
                layoutDirection = layoutDirection,
            ),
            y = guideSkipPlaceable.height,
        )
        guideTitle2Placeable.place(
            x = constraints.centerHorizontally(
                width = guideTitle2Placeable.width,
                layoutDirection = layoutDirection
            ),
            y = guideTitle2Height,
        )
        guideImagePlaceable.place(
            x = 0,
            y = guideTitle2Height + guideTitle2Placeable.height,
        )
    }
}

@Composable
internal fun HomeGuideFeatureScreen(
    pagerState: PagerState,
    guideStep: GuideStep,
) {
    Layout(
        modifier = Modifier
            .fillMaxSize(),
        content = {
            QuackBody2(
                modifier = Modifier
                    .layoutId(HomeGuideFeatureLayoutId.Skip)
                    .padding(top = 40.dp, end = 16.dp),
                text = stringResource(id = R.string.skip),
                color = QuackColor.Gray3,
            )
            QuackHeadLine2(
                modifier = Modifier
                    .layoutId(HomeGuideFeatureLayoutId.Title1)
                    .padding(top = 41.dp),
                text = stringResource(id = guideStep.subtitle),
                color = QuackColor.White,
                align = TextAlign.Center,
            )
            QuackHeadLine1(
                modifier = Modifier
                    .layoutId(HomeGuideFeatureLayoutId.Title2),
                text = stringResource(id = guideStep.title),
                color = QuackColor.DuckieOrange,
                align = TextAlign.Center,
            )
            Image(
                modifier = Modifier
                    .layoutId(HomeGuideFeatureLayoutId.Image)
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(top = 28.dp),
                painter = painterResource(id = guideStep.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopStart
            )
        },
        measurePolicy = HomeGuideFeatureMeasurePolicy,
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
                .background(color = QuackColor.DuckieOrange.composeColor),
            contentAlignment = Alignment.Center,
        ) {
            QuackHeadLine2(
                text = "덕키 시작하기",
                color = QuackColor.White,
                onClick = onNext,
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
