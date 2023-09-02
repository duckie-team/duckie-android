/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.create.exam.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.quack.todo.QuackTopAppBar
import team.duckie.app.android.feature.create.exam.R
import team.duckie.quackquack.material.icon.quackicon.OutlinedGroup
import team.duckie.quackquack.material.icon.quackicon.outlined.ArrowBack
import team.duckie.quackquack.material.icon.quackicon.outlined.Close
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.sugar.QuackHeadLine2

@Composable
internal fun PrevAndNextTopAppBar(
    modifier: Modifier = Modifier,
    onLeadingIconClick: () -> Unit,
    trailingText: String? = null,
    onTrailingTextClick: (() -> Unit)? = null,
    trailingTextEnabled: Boolean = false,
) { // TODO(EvergreenTree97): enabled 속성 필요
    QuackTopAppBar(
        modifier = modifier,
        leadingIcon = OutlinedGroup.ArrowBack,
        leadingText = stringResource(id = R.string.create_problem),
        onLeadingIconClick = onLeadingIconClick,
        trailingText = trailingText,
        onTrailingTextClick = when (trailingTextEnabled) {
            true -> {
                // 클릭 이벤트가 동작해야 하므로 아래 두 개의 값은 절대 null 이 될 수 없음
                requireNotNull(trailingText)
                requireNotNull(onTrailingTextClick)
                onTrailingTextClick
            }

            else -> null
        },
    )
}

// TODO(EvergreenTree): QuackTopAppBar에 leadingIcon이 nullable로 변경됨이 필요
@Composable
internal fun ExitAppBar(
    modifier: Modifier = Modifier,
    leadingText: String,
    onTrailingIconClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        QuackHeadLine2(text = leadingText)
        QuackIcon(
            modifier = Modifier.quackClickable(onClick = onTrailingIconClick),
            icon = OutlinedGroup.Close,
        )
    }
}
