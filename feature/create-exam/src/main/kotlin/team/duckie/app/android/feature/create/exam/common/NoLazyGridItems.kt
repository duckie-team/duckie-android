/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.create.exam.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.key

/**
 * Lazy 하지 않은 그리드 목록 빌더 (개수로 계산)
 * (https://stackoverflow.com/questions/69336555/fixed-grid-inside-lazycolumn-in-jetpack-compose)
 *
 * @param count 전체 아이템 개수
 * @param nColumns 한 행의 개수
 * @param paddingValues 그리드 목록 패딩
 * @param horizontalArrangement 정렬 방향
 * @param itemContent index 기반으로 만들어지는 아이템 컨텐츠 빌더
 */
@Composable
internal fun NoLazyGridItems(
    count: Int,
    nColumns: Int,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(Int) -> Unit,
) {
    NoLazyGridItems(
        data = List(count) { it },
        nColumns = nColumns,
        paddingValues = paddingValues,
        horizontalArrangement = horizontalArrangement,
        itemContent = itemContent,
    )
}

/**
 * Lazy 하지 않은 그리드 목록 빌더 (개수로 계산)
 * (https://stackoverflow.com/questions/69336555/fixed-grid-inside-lazycolumn-in-jetpack-compose)
 *
 * @param data 전체 아이템
 * @param nColumns 한 행의 개수
 * @param paddingValues 그리드 목록 패딩
 * @param horizontalArrangement 정렬 방향
 * @param key 사전 실행 로직 함수?
 * @param itemContent data[`index`] 기반으로 만들어지는 아이템 컨텐츠 빌더
 */
@Composable
internal fun <T> NoLazyGridItems(
    data: List<T>,
    nColumns: Int,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val rows = if (data.isEmpty()) 0 else 1 + (data.count() - 1) / nColumns
    for (rowIndex in 0 until rows) {
        Row(
            modifier = Modifier.padding(paddingValues),
            horizontalArrangement = horizontalArrangement,
        ) {
            for (columnIndex in 0 until nColumns) {
                val itemIndex = rowIndex * nColumns + columnIndex
                if (itemIndex < data.count()) {
                    val item = data[itemIndex]
                    key(key?.invoke(item)) {
                        Box(
                            modifier = Modifier.weight(1f, fill = true),
                            propagateMinConstraints = true,
                        ) {
                            itemContent.invoke(this, item)
                        }
                    }
                } else {
                    Spacer(Modifier.weight(1f, fill = true))
                }
            }
        }
    }
}
