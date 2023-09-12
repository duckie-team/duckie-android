/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.app.android.common.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

suspend inline fun PagerState.movePrevPage() {
    if (currentPage > 0 && canScrollBackward) {
        currentPage.minus(1).also {
            animateScrollToPage(it)
        }
    }
}

suspend inline fun PagerState.moveNextPage(maxPage: Int) {
    if (currentPage < maxPage && canScrollForward) {
        currentPage.plus(1).also {
            animateScrollToPage(it)
        }
    }
}

fun PagerState.isCurrentPage(targetIndex: Int): Boolean {
    return currentPageOffsetFraction == 0f && targetIndex == currentPage
}

fun PagerState.isTargetPage(targetIndex: Int): Boolean {
    return targetIndex == targetPage
}

val PagerState.isLastPage: Boolean
    get() = currentPage == pageCount - 1

@SuppressLint("ComposableNaming")
@Composable
fun PagerState.scrollToOriginalPage(
    rememberPage: Int
){
    val isLocateMiddle by remember {
        derivedStateOf {
           currentPageOffsetFraction != 0.0f
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (isLocateMiddle) {
            scrollToPage(rememberPage)
        }
    }
}
