/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class)

package team.duckie.app.android.util.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState

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
