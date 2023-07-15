/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack.todo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.icon.v1.toQuackV1Icon
import team.duckie.quackquack.ui.component.QuackTagType

@Composable
fun QuackSingeLazyRowTag(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(all = 0.dp),
    title: String? = null,
    items: List<String>,
    itemSelections: List<Boolean>? = null,
    horizontalSpace: Dp = 8.dp,
    tagTypeResId: Int?,
    key: ((index: Int, item: String) -> Any)? = null,
    contentType: (index: Int, item: String) -> Any? = { _, _ -> null },
    onClick: (index: Int) -> Unit,
) {
    team.duckie.quackquack.ui.component.QuackSingeLazyRowTag(
        modifier = modifier,
        contentPadding = contentPadding,
        title = title,
        items = items,
        itemSelections = itemSelections,
        horizontalSpace = horizontalSpace,
        tagType = QuackTagType.Circle(tagTypeResId?.toQuackV1Icon),
        key = key,
        contentType = contentType,
        onClick = onClick,
    )
}
