/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.shared.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.color.QuackColor

private val BottomSheetShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp,
)

@Composable
fun DuckieBottomSheetDialog(
    useHandle: Boolean = false,
    sheetState: ModalBottomSheetState,
    sheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            DuckieBottomSheetContent(
                useHandle = useHandle,
                sheetContent = sheetContent,
            )
        },
        sheetBackgroundColor = Color.Transparent,
    ) {
        content()
    }
}

@Composable
private fun DuckieBottomSheetContent(
    useHandle: Boolean,
    sheetContent: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = QuackColor.White.composeColor,
            ),
    ) {
        DuckieBottomSheetHandle(
            useHandle = useHandle,
        )

        sheetContent()
    }
}

private val BottomSheetContentPadding = PaddingValues(
    vertical = 8.dp,
)

private val BottomSheetHandleSize = DpSize(
    width = 40.dp,
    height = 4.dp,
)

private val BottomSheetHandleShape = RoundedCornerShape(
    size = 2.dp,
)

@Composable
private fun DuckieBottomSheetHandle(
    useHandle: Boolean,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(BottomSheetContentPadding),
        contentAlignment = Alignment.Center,
    ) {
        if (useHandle) {
            Box(
                modifier = Modifier
                    .size(BottomSheetHandleSize)
                    .clip(BottomSheetHandleShape)
                    .background(
                        color = QuackColor.Gray3.composeColor,
                    ),
            )
        }
    }
}
