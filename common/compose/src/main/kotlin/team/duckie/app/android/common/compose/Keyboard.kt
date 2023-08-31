/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.common.compose

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import team.duckie.app.android.common.kotlin.AllowMagicNumber

/**
 * 키보드의 Show/Hide 상태를 가져온다.
 * @return 키보드의 visible 상태
 */
@AllowMagicNumber("keyboard visible 상태 계산")
@Composable
fun rememberKeyboardVisible(
    initialKeyboardState: Boolean = false,
): State<Boolean> {
    val keyboardVisibleState = remember { mutableStateOf(initialKeyboardState) }
    val view = LocalView.current

    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)

            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardVisibleState.value = keypadHeight > screenHeight * 0.15
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardVisibleState
}

/**
 * 키보드 상태에 따른 Modifier 조합
 */
@Composable
fun Modifier.composedWithKeyboardVisibility(
    whenKeyboardVisible: Modifier.() -> Modifier = { this },
    whenKeyboardHidden: Modifier.() -> Modifier = { this },
): Modifier {
    val keyboardVisible = rememberKeyboardVisible(initialKeyboardState = true)

    return this
        .composed { if (keyboardVisible.value) whenKeyboardVisible() else this }
        .composed { if (!keyboardVisible.value) whenKeyboardHidden() else this }
}


@Composable
fun HideKeyboardWhenBottomSheetHidden(sheetState: ModalBottomSheetState) {
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        val sheetStateFlow = snapshotFlow { sheetState.currentValue }
        sheetStateFlow.collect { state ->
            if (state == ModalBottomSheetValue.Hidden) {
                keyboard?.hide()
            }
        }
    }
}
