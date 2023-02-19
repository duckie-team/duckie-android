/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.shared.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackSmallButtonType

@Composable
fun ErrorScreen(
    modifier: Modifier,
    isNetworkError: Boolean = false,
    title: String? = null,
    onRetryClick: suspend () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier.navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            QuackHeadLine2(
                padding = PaddingValues(top = 164.dp),
                text = if (isNetworkError) {
                    stringResource(id = R.string.default_network_error_title)
                } else {
                    title ?: stringResource(id = R.string.default_error)
                },
                color = QuackColor.Gray1,
            )

            if (isNetworkError) {
                QuackBody2(
                    padding = PaddingValues(top = 8.dp),
                    text = stringResource(id = R.string.default_network_error_message),
                    color = QuackColor.Gray1,
                )
            }
        }

        // TODO(riflockle7): 꽥꽥에서 하얀 배경 버튼 제공 필요
        QuackSmallButton(
            modifier = Modifier.padding(bottom = 40.dp),
            type = QuackSmallButtonType.Fill,
            text = stringResource(id = R.string.default_error_retry),
            enabled = false,
            onClick = {
                coroutineScope.launch {
                    onRetryClick()
                }
            },
        )
    }
}
