/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.app.android.common.compose.ui.quack.todo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import team.duckie.quackquack.material.icon.quackicon.OutlinedGroup
import team.duckie.quackquack.material.icon.quackicon.outlined.Close
import team.duckie.quackquack.ui.QuackTag
import team.duckie.quackquack.ui.QuackTagStyle
import team.duckie.quackquack.ui.trailingIcon
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

@Composable
fun QuackCircleTag(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: (() -> Unit)? = null,
) {
    QuackTag(
        text = text,
        style = QuackTagStyle.Outlined,
        modifier = modifier.trailingIcon(OutlinedGroup.Close, onClick = onClick ?: {}),
        selected = isSelected,
    ) {}
}
