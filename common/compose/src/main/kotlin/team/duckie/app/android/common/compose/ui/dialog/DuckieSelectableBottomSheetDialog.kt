/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.common.compose.ui.dialog

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.common.compose.R
import team.duckie.app.android.common.kotlin.fastMap

const val ReportAlreadyExists = "이미 신고한 게시물 입니다!"

enum class DuckieSelectableType {
    Ignore,
    Report,
}

/**
 * [types] 항목들 중에 하나를 선택할 수 있는 형식의 [DuckieSelectableBottomSheetDialog]
 */
@Composable
fun DuckieSelectableBottomSheetDialog(
    modifier: Modifier = Modifier,
    navigationBarsPaddingVisible: Boolean = true,
    bottomSheetState: ModalBottomSheetState,
    types: List<DuckieSelectableType> = persistentListOf(DuckieSelectableType.Report),
    closeSheet: () -> Unit,
    onReport: (() -> Unit)? = null,
    onIgnore: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val items = types
        .fastMap { it.toBottomSheetItem(onReport, onIgnore) }
        .toImmutableList()

    SelectableItemBottomSheetDialog(
        modifier = modifier,
        navigationBarsPaddingVisible = navigationBarsPaddingVisible,
        closeSheet = closeSheet,
        items = items,
        bottomSheetState = bottomSheetState,
    ) {
        content()
    }
}

private fun DuckieSelectableType.toBottomSheetItem(
    onReport: (() -> Unit)? = null,
    onIgnore: (() -> Unit)? = null,
): BottomSheetItem {
    return when (this) {
        DuckieSelectableType.Ignore -> BottomSheetItem(
            icon = R.drawable.ic_flag_24,
            text = R.string.ignore,
            onClick = {
                onIgnore?.invoke()
            },
        )

        DuckieSelectableType.Report -> BottomSheetItem(
            icon = R.drawable.ic_report,
            text = R.string.report,
            onClick = {
                onReport?.invoke()
            },
        )
    }
}
