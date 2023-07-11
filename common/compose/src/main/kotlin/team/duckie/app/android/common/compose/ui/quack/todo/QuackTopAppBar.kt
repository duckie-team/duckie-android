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

/** 덕키에서 사용하는 TopAppBar */
@Composable
fun QuackTopAppBar(
    modifier: Modifier = Modifier,
    leadingIcon: Int? = null,
    leadingText: String? = null,
    onLeadingIconClick: (() -> Unit)? = null,
    showLogoAtCenter: Boolean? = null,
    centerText: String? = null,
    centerTextTrailingIcon: Int? = null,
    onCenterClick: (() -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    trailingIcon: Int? = null,
    trailingExtraIcon: Int? = null,
    trailingText: String? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    onTrailingExtraIconClick: (() -> Unit)? = null,
    onTrailingTextClick: (() -> Unit)? = null,
) {
    team.duckie.quackquack.ui.component.QuackTopAppBar(
        modifier = modifier,
        leadingIcon = leadingIcon?.toQuackV1Icon,
        leadingText = leadingText,
        onLeadingIconClick = onLeadingIconClick,
        showLogoAtCenter = showLogoAtCenter,
        centerText = centerText,
        centerTextTrailingIcon = centerTextTrailingIcon?.toQuackV1Icon,
        onCenterClick = onCenterClick,
        trailingContent = trailingContent,
        trailingIcon = trailingIcon?.toQuackV1Icon,
        trailingExtraIcon = trailingExtraIcon?.toQuackV1Icon,
        trailingText = trailingText,
        onTrailingIconClick = onTrailingIconClick,
        onTrailingExtraIconClick = onTrailingExtraIconClick,
        onTrailingTextClick = onTrailingTextClick,
    )
}
