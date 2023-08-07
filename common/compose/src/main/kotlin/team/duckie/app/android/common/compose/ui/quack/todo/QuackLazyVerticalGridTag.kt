/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class, ExperimentalQuackQuackApi::class)

package team.duckie.app.android.common.compose.ui.quack.todo

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.kotlin.fastForEachIndexed
import team.duckie.app.android.common.kotlin.runtimeCheck
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackTag
import team.duckie.quackquack.ui.QuackTagStyle
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.trailingIcon
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi


/**
 * [LazyVerticalGrid] 형식으로 주어진 태그들을 배치합니다.
 * 이 컴포넌트는 항상 상위 컴포저블의 가로 길이만큼 width 가 지정되고,
 * 한 줄에 최대 [itemChunkedSize]개가 들어갈 수 있습니다. 또한 가로와 세로 스크롤을 모두 지원합니다.
 *
 * 퍼포먼스 측면에서 [LazyLayout] 를 사용하는 것이 좋지만, 덕키의 경우
 * 표시해야 하는 태그의 개수가 많지 않기 때문에 컴포저블을 직접 그려도
 * 성능에 중대한 영향을 미치지 않을 것으로 판단하여 [LazyColumn] 과
 * [Row] + [Modifier.horizontalScroll] 를 사용하여 구현하였습니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param contentPadding 이 컴포넌트의 광역에 적용될 [PaddingValues]
 * @param title 상단에 표시될 제목. 만약 null 을 제공할 시 표시되지 않습니다.
 * @param items 표시할 태그들의 제목. **중복되는 태그 제목은 허용하지 않습니다.**
 * 이 항목은 바뀔 수 있으므로 [ImmutableList] 가 아닌 일반 [List] 로 받습니다.
 * @param itemSelections 태그들의 선택 여부.
 * 이 항목은 바뀔 수 있으므로 [ImmutableList] 가 아닌 [List] 로 받습니다.
 * @param itemChunkedSize 한 칸에 들어갈 최대 아이템의 개수
 * @param horizontalSpace 아이템들의 가로 간격
 * @param verticalSpace 아이템들의 세로 간격
 * @param trailingIconResId trailingIcon 에 들어갈 ResourceId. 없을 시 아이콘이 없습니다.
 * @param onClick 사용자가 태그를 클릭했을 때 호출되는 람다.
 * 람다식의 인자로는 선택된 태그의 index 가 들어옵니다.
 */
@Composable
fun QuackLazyVerticalGridTag(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(all = 0.dp),
    title: String? = null,
    items: List<String>,
    itemSelections: List<Boolean>? = null,
    itemChunkedSize: Int,
    horizontalSpace: Dp = 8.dp,
    verticalSpace: Dp = 8.dp,
    trailingIcon: ImageVector? = null,
    onTrailingClick: ((Int) -> Unit)? = null,
    onClick: (index: Int) -> Unit,
) {
    if (itemSelections != null) {
        runtimeCheck(items.size == itemSelections.size) {
            "The size of items and the size of itemsSelection must always be the same. " +
                    "[items.size (${items.size}) != itemsSelection.size (${itemSelections.size})]"
        }
    }

    val chunkedItems = remember(items) {
        items.chunked(itemChunkedSize)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(contentPadding),
    ) {
        if (title != null) {
            QuackText(
                modifier = Modifier.padding(bottom = 12.dp),
                text = title,
                typography = QuackTypography.Title2,
                singleLine = true,
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(horizontalSpace),
        ) {
            chunkedItems.fastForEachIndexed { rowIndex, items ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(state = rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(verticalSpace),
                ) {
                    items.fastForEachIndexed { index, item ->
                        val currentIndex = rowIndex * itemChunkedSize + index
                        QuackTag(
                            text = item,
                            style = QuackTagStyle.Filled,
                            modifier = if (trailingIcon != null) {
                                Modifier.trailingIcon(
                                    trailingIcon,
                                    onClick = { onTrailingClick?.invoke(index) },
                                )
                            } else {
                                Modifier
                            },
                            selected = itemSelections?.get(currentIndex) ?: false,
                            onClick = { onClick(index) },
                        )
                    }
                }
            }
        }
    }
}
