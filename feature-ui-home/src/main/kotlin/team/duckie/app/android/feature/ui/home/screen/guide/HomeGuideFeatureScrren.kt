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
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.component.BaseBottomLayout
import team.duckie.app.android.shared.ui.compose.DuckieHorizontalPagerIndicator
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.centerHorizontally
import team.duckie.app.android.util.compose.getPlaceable
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.modifier.quackClickable

private object HomeGuideFeatureLayoutId {
    const val Skip = "HomeGuideFeatureSkip"
    const val SubTitle = "HomeGuideFeatureSubTitle"
    const val Title = "HomeGuideFeatureTitle"
    const val Image = "HomeGuideFeatureImage"
}

private val HomeGuideFeatureMeasurePolicy = MeasurePolicy { measureables, constraints ->
    val looseConstraints = constraints.asLoose()
    val extraLooseConstraints = constraints.asLoose(width = true)

    val guideSkipPlaceable =
        measureables.getPlaceable(HomeGuideFeatureLayoutId.Skip, extraLooseConstraints)

    val guideTitle1Placeable =
        measureables.getPlaceable(HomeGuideFeatureLayoutId.SubTitle, looseConstraints)

    val guideTitle2Height = guideSkipPlaceable.height + guideTitle1Placeable.height

    val guideTitle2Placeable =
        measureables.getPlaceable(HomeGuideFeatureLayoutId.Title, looseConstraints)

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
                layoutDirection = layoutDirection,
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
    guideStep: GuideStep,
    onClose: () -> Unit,
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
                onClick = onClose,
            )
            QuackHeadLine2(
                modifier = Modifier
                    .layoutId(HomeGuideFeatureLayoutId.SubTitle)
                    .padding(top = 41.dp),
                text = stringResource(id = guideStep.subtitle),
                color = QuackColor.White,
                align = TextAlign.Center,
            )
            QuackHeadLine1(
                modifier = Modifier
                    .layoutId(HomeGuideFeatureLayoutId.Title),
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
                alignment = Alignment.TopStart,
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
