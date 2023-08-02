/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.app.android.common.compose.ui.quack.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.kotlin.runtimeCheck
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.quackicon.OutlinedGroup
import team.duckie.quackquack.material.icon.quackicon.outlined.Close
import team.duckie.quackquack.ui.QuackTag
import team.duckie.quackquack.ui.QuackTagStyle
import team.duckie.quackquack.ui.trailingIcon
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi


/**
 * [LazyRow] 형식으로 주어진 태그들을 **한 줄로** 배치합니다.
 * 이 컴포넌트는 항상 상위 컴포저블의 가로 길이만큼 width 가 지정됩니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param contentPadding 이 컴포넌트의 광역에 적용될 [PaddingValues]
 * @param title 상단에 표시될 제목. 만약 null 을 제공할 시 표시되지 않습니다.
 * @param items 표시할 태그들의 제목. **중복되는 태그 제목은 허용하지 않습니다.**
 * 이 항목은 바뀔 수 있으므로 [ImmutableList] 가 아닌 일반 [List] 로 받습니다.
 * @param itemSelections 태그들의 선택 여부.
 * 이 항목은 바뀔 수 있으므로 [ImmutableList] 가 아닌 [List] 로 받습니다.
 * @param horizontalSpace 아이템들의 가로 간격
 * @param tagType [QuackLazyVerticalGridTag] 에서 표시할 태그의 타입을 지정합니다.
 * 여러 종류의 태그가 [QuackLazyVerticalGridTag] 으로 표시될 수 있게 태그의 타입을 따로 받습니다.
 * @param key a factory of stable and unique keys representing the item. Using the same key
 * for multiple items in the list is not allowed. Type of the key should be saveable
 * via Bundle on Android. If null is passed the position in the list will represent the key.
 * When you specify the key the scroll position will be maintained based on the key, which
 * means if you add/remove items before the current visible item the item with the given key
 * will be kept as the first visible one.
 * @param contentType a factory of the content types for the item. The item compositions of
 * the same type could be reused more efficiently. Note that null is a valid type and items of such
 * type will be considered compatible.
 * @param onClick 사용자가 태그를 클릭했을 때 호출되는 람다.
 * 람다식의 인자로는 선택된 태그의 index 가 들어옵니다.
 */
@Composable
fun QuackSingeLazyRowTag(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(all = 0.dp),
    title: String? = null,
    items: List<String>,
    itemSelections: List<Boolean>? = null,
    horizontalSpace: Dp = 8.dp,
    trailingIconResId: Int?,
    key: ((index: Int, item: String) -> Any)? = null,
    contentType: (index: Int, item: String) -> Any? = { _, _ -> null },
    onClick: (index: Int) -> Unit,
) {
    if (itemSelections != null) {
        runtimeCheck(
            value = items.size == itemSelections.size,
        ) {
            "The size of items and the size of itemsSelection must always be the same. " +
                    "[items.size (${items.size}) != itemsSelection.size (${itemSelections.size})]"
        }
    }

    val layoutDirection = LocalLayoutDirection.current

    Column(modifier = modifier.fillMaxWidth()) {
        if (title != null) {
            team.duckie.quackquack.ui.QuackText(
                modifier = Modifier.padding(
                    start = contentPadding.calculateStartPadding(layoutDirection),
                    end = contentPadding.calculateEndPadding(layoutDirection),
                    bottom = 12.dp,
                ),
                text = title,
                typography = QuackTypography.Title2,
                singleLine = true,
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = contentPadding,
            horizontalArrangement = Arrangement.spacedBy(horizontalSpace),
        ) {
            itemsIndexed(
                items = items,
                key = key,
                contentType = contentType,
            ) { index, item ->
                QuackTag(
                    text = item,
                    style = QuackTagStyle.GrayscaleFlat,
                    modifier = if (trailingIconResId != null) {
                        Modifier.trailingIcon(
                            OutlinedGroup.Close,
                            onClick = { onClick(index) },
                        )
                    } else {
                        Modifier
                    },
                    selected = itemSelections?.get(index) ?: false,
                    onClick = {},
                )
            }
        }
    }
}
