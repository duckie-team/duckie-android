/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalDesignToken::class, ExperimentalQuackQuackApi::class)

package team.duckie.app.android.common.compose.ui.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.ArrowSend
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackDefaultTextField
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DuckieTextFieldBottomSheet(
    sheetState: ModalBottomSheetState,
    onDismissRequest: (String) -> Unit,
    content: @Composable () -> Unit,
) {
    val text = remember { mutableStateOf("") }

    DuckieBottomSheetDialog(
        modifier = Modifier.fillMaxSize(),
        useHandle = true,
        sheetState = sheetState,
        sheetContent = {
            Column {
                Spacer(space = 16.dp)
                QuackDivider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    QuackDefaultTextField(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 16.dp),
                        value = text.value,
                        onValueChange = { str ->
                            text.value = str
                        },
                        style = QuackTextFieldStyle.Default,
                        placeholderText = "댓글을 남겨보세요!",
                    )
                    QuackIcon(
                        modifier = Modifier.quackClickable(rippleEnabled = false) {
                            onDismissRequest(text.value)
                        },
                        icon = QuackIcon.Outlined.ArrowSend,
                        tint = QuackColor.Gray2,
                    )
                }
            }
        },
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun PreviewBottomSheet() {
    val rememberSheet = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded)

    LaunchedEffect(key1 = Unit, block = {
        rememberSheet.show()
    },)
    DuckieTextFieldBottomSheet(
        sheetState = rememberSheet,
        onDismissRequest = {},
    ) {
        Text(text = "안녕 난 개구리야")
    }
}
