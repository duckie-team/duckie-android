/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)

package team.duckie.app.android.common.compose.util

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import team.duckie.app.android.common.compose.rememberKeyboardVisible

@Composable
fun HandleKeyboardVisibilityWithSheet(sheetState: ModalBottomSheetState) {
    val keyboardVisible = rememberKeyboardVisible(false)
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = sheetState.currentValue) {
        if (keyboardVisible.value && sheetState.currentValue == ModalBottomSheetValue.Hidden) {
            keyboardController?.hide()
        }
    }
}

fun Modifier.addFocusCleaner(
    doOnClear: () -> Unit = {},
): Modifier = composed {
    val focusManager = LocalFocusManager.current
    pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}
