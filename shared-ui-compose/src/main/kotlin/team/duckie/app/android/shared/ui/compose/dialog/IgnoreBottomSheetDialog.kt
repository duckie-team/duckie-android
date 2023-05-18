/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.shared.ui.compose.dialog

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.shared.ui.compose.R

@Composable
fun IgnoreBottomSheetDialog(
    modifier: Modifier = Modifier,
    navigationBarsPaddingVisible: Boolean = true,
    bottomSheetState: ModalBottomSheetState,
    closeSheet: () -> Unit,
    onReport: () -> Unit,
    content: @Composable () -> Unit,
) {
    val rememberBottomSheetItems = remember {
        persistentListOf(
            BottomSheetItem(
                icon = R.drawable.ic_flag_24,
                text = R.string.ignore,
                onClick = onReport,
            ),
        )
    }

    SelectableItemBottomSheetDialog(
        modifier = modifier,
        navigationBarsPaddingVisible = navigationBarsPaddingVisible,
        closeSheet = closeSheet,
        items = rememberBottomSheetItems,
        bottomSheetState = bottomSheetState,
    ) {
        content()
    }
}
