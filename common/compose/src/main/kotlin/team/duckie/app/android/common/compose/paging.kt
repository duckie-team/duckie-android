/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.paging.compose.LazyPagingItems

fun <T : Any> itemsIndexedPagingKey(
    items: LazyPagingItems<T>,
    key: (Int) -> Any?,
): ((Int, T) -> Any)? = { index, _ -> // while page Changing and Load, cannot provide unique key
    if (items[items.itemCount - 1] == null || items.itemCount <= 1) {
        index
    } else {
        key(index) ?: index
    }
}

fun <T : Any> itemsPagingKey(
    items: LazyPagingItems<T>,
    key: (Int) -> Any?,
): ((Int) -> Any)? = { index -> // while page Changing and Load, cannot provide unique key
    if (items[items.itemCount - 1] == null || items.itemCount <= 1) {
        index
    } else {
        key(index) ?: index
    }
}

/**
 * 서버측 에러로 PK가 중복으로 내려오는 경우 방치
 *
 * @return PK + [secondId] 형식의 String
 */
fun Int?.getUniqueKey(secondId: Int): String =
    this.toString() + secondId

/**
 * 스크롤 위치를 알맞게 재설정 함
 * https://issuetracker.google.com/issues/177245496 이슈의 임시 해결책
 * LazyPagingItems 가 재생성 시 일시적으로 0을 반환하는 문제
 */
@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyListState {
    return when (itemCount) {
        // Return a different LazyListState instance.
        0 -> remember(this) { LazyListState(0, 0) }
        // Return rememberLazyListState (normal case).
        else -> androidx.compose.foundation.lazy.rememberLazyListState()
    }
}
