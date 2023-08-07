/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.setting.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import team.duckie.app.android.common.compose.ui.quack.QuackRoundCheckBox
import team.duckie.app.android.common.compose.ui.quack.todo.QuackReactionTextArea
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.app.android.feature.setting.R
import team.duckie.app.android.feature.setting.constans.Withdraweason
import team.duckie.app.android.feature.setting.viewmodel.SettingViewModel
import team.duckie.app.android.feature.setting.viewmodel.state.SettingState
<<<<<<< HEAD
=======
import team.duckie.quackquack.animation.QuackAnimatedVisibility
import team.duckie.quackquack.animation.animateQuackColorAsState
>>>>>>> 45a2134... refactor: 설정 페이지 QuackV2로 마이그레이션
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackImage
<<<<<<< HEAD
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackReviewTextArea
import team.duckie.app.android.common.compose.ui.quack.todo.animation.QuackRoundCheckBox
import team.duckie.quackquack.ui.modifier.quackClickable
=======
import team.duckie.quackquack.ui.QuackText
>>>>>>> 45a2134... refactor: 설정 페이지 QuackV2로 마이그레이션
import team.duckie.quackquack.ui.sugar.QuackBody1
import team.duckie.quackquack.ui.sugar.QuackHeadLine2
import team.duckie.quackquack.ui.sugar.QuackSubtitle
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

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
        modifier = Modifier.imePadding(),
    ) {
        item {
            QuackImage(src = R.drawable.ic_sign_out_character)
            QuackHeadLine2(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(id = R.string.withdraw_check_msg, nickname),
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
                QuackReactionTextArea(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .height(140.dp)
                        .focusRequester(focusRequester = focusRequester)
                        .onFocusChanged { state ->
                            vm.updateWithDrawFocus(state.isFocused)
                        },
                    reaction = state.withdrawUserInputReason,
                    onReactionChanged = { text ->
                        vm.updateWithdrawUserInputReason(text)
                    },
                    placeHolder = stringResource(
                        id = R.string.withdraw_others_text_field_hint,
                    ),
                    visibleCurrentLength = false,
                )
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .padding(horizontal = 4.dp, vertical = 12.dp),
            ) {
                val buttonModifier = Modifier
                    .weight(1f)
                    .height(44.dp)

                val buttonEnabled = state.withdrawReasonSelected != Withdraweason.INITIAL

                val primaryButtonColor =
                    animateQuackColorAsState(targetValue = if (buttonEnabled) QuackColor.DuckieOrange else QuackColor.Gray2)

                Box(
                    modifier = buttonModifier
                        .background(
                            color = QuackColor.White.value,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp),
                            color = QuackColor.Gray3.value,
                        )
                        .quackClickable(
                            onClick = vm::navigateBack,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    QuackSubtitle(text = stringResource(id = R.string.withdraw_cancel_msg))
                }
                Spacer(space = 8.dp)
                Box(
                    modifier = buttonModifier
                        .background(
                            color = primaryButtonColor.value.value,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .runIf(buttonEnabled) {
                            quackClickable(
                                onClick = { vm.changeWithdrawDialogVisible(true) },
                            )
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    QuackText(
                        text = stringResource(id = R.string.withdraw),
                        typography = QuackTypography.Subtitle.change(
                            color = QuackColor.White,
                        ),
                    )
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
            .quackClickable(
                onClick = {
                    onClick(reason)
                },
            )
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
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackRoundCheckBox(checked = visible)
        Spacer(space = 10.dp)
        QuackBody1(text = reasonDescription)
    }
}
