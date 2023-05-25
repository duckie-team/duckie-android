/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.feature.setting.R
import team.duckie.app.android.feature.setting.constans.Withdraweason
import team.duckie.app.android.feature.setting.viewmodel.SettingViewModel
import team.duckie.app.android.feature.setting.viewmodel.state.SettingState
import team.duckie.quackquack.animation.QuackAnimatedVisibility
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackReviewTextArea
import team.duckie.quackquack.ui.component.QuackRoundCheckBox
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.sugar.QuackBody1
import team.duckie.quackquack.ui.sugar.QuackHeadLine2

@Composable
internal fun SettingWithdrawScreen(
    vm: SettingViewModel,
    state: SettingState,
) {
    val nickname = state.me?.nickname ?: ""
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = state.withdrawReasonSelected) {
        if (state.withdrawReasonSelected == Withdraweason.OTHERS) {
            focusRequester.requestFocus()
        }
    }

    LazyColumn(
        modifier = Modifier.imePadding()
    ) {
        item {
            QuackImage(src = R.drawable.ic_sign_out_character)
            QuackHeadLine2(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(id = R.string.withdraw_check_msg, nickname)
            )
            QuackBody1(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(id = R.string.withdraw_privacy_msg),
            )
        }
        item {
            QuackHeadLine2(
                modifier = Modifier.padding(top = 40.dp, bottom = 16.dp),
                text = stringResource(id = R.string.withdraw_reason_msg),
            )
        }
        items(Withdraweason.getSignOutReason()) { item ->
            SettingCheckBox(
                modifier = Modifier.padding(
                    bottom = 4.dp,
                ),
                visible = state.withdrawReasonSelected == item,
                reason = item,
                onClick = {
                    vm.updateWithdrawReason(item)
                },
            )
        }
        item {
            QuackAnimatedVisibility(visible = state.withdrawReasonSelected == Withdraweason.OTHERS) {
                QuackReviewTextArea(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .focusRequester(focusRequester = focusRequester)
                        .onFocusChanged { state ->
                            vm.updateWithDrawFocus(state.isFocused)
                        },
                    text = state.withdrawUserInputReason,
                    onTextChanged = { text ->
                        vm.updateWithdrawUserInputReason(text)
                    },
                    focused = state.withdrawIsFocused,
                    placeholderText = stringResource(
                        id = R.string.withdraw_others_text_field_hint,
                    )
                )
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .padding(horizontal = 4.dp, vertical = 12.dp)
            ) {
                val buttonModifier = Modifier
                    .weight(1f)
                    .height(44.dp)

                QuackLargeButton(
                    modifier = buttonModifier,
                    type = QuackLargeButtonType.Border,
                    text = stringResource(id = R.string.withdraw_cancel_msg),
                    onClick = vm::navigateBack,
                )
                Spacer(space = 8.dp)
                QuackLargeButton(
                    modifier = buttonModifier,
                    type = QuackLargeButtonType.Fill,
                    text = stringResource(id = R.string.withdraw),
                    enabled = state.withdrawReasonSelected != Withdraweason.INITIAL
                ) {
                    // 버튼 클릭 시 실행될 동작
                }
            }
        }
    }
}

@SuppressLint("ResourceType")
@Composable
internal fun SettingCheckBox(
    modifier: Modifier = Modifier,
    visible: Boolean,
    reason: Withdraweason,
    onClick: (Withdraweason) -> Unit,
) {
    val reasonDescription = reason.description?.let { stringResource(id = it) } ?: ""

    Row(
        modifier = modifier
            .fillMaxWidth()
            .quackClickable {
                onClick(reason)
            }
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = QuackColor.Gray4.value,
            )
            .border(
                width = 1.dp,
                color = QuackColor.Gray3.value,
            )
            .padding(
                horizontal = 12.dp,
                vertical = 14.dp,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        QuackRoundCheckBox(checked = visible)
        Spacer(space = 10.dp)
        QuackBody1(text = reasonDescription)
    }
}
