/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack.todo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import team.duckie.app.android.common.compose.ui.icon.v1.toQuackV1Icon

@Composable
fun QuackCircleTag(
    modifier: Modifier = Modifier,
    text: String,
    trailingIconResId: Int? = null,
    isSelected: Boolean,
    onClick: (() -> Unit)? = null,
) {
    team.duckie.quackquack.ui.component.QuackCircleTag(
        modifier = modifier,
        text = text,
        trailingIcon = trailingIconResId?.toQuackV1Icon,
        isSelected = isSelected,
        onClick = onClick,
    )
}
