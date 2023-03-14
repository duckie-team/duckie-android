/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.compose

import androidx.paging.compose.LazyPagingItems

fun <T : Any> itemsIndexedPagingKey(
    items: LazyPagingItems<T>,
    key: (Int) -> Any?,
): ((Int, T) -> Any)? = { index, _ -> // while page Changing and Load, cannot provide unique key
    if (items[items.itemCount - 1] == null || items.itemCount <= 1) index
    else key(index) ?: index
}

fun <T : Any> itemsPagingKey(
    items: LazyPagingItems<T>,
    key: (Int) -> Any?,
): ((Int) -> Any)? = { index -> // while page Changing and Load, cannot provide unique key
    if (items[items.itemCount - 1] == null || items.itemCount <= 1) index
    else key(index) ?: index
}
