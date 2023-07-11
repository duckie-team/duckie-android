/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.common.compose.ui.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.sugar.QuackSubtitle2

/** 여러 아이템 중 하나를 선택하는 형태의 [DuckieBottomSheetDialog] */
@Composable
fun SelectableItemBottomSheetDialog(
    modifier: Modifier = Modifier,
    items: ImmutableList<BottomSheetItem>,
    navigationBarsPaddingVisible: Boolean = true,
    bottomSheetState: ModalBottomSheetState,
    closeSheet: () -> Unit,
    content: @Composable () -> Unit,
) {
    DuckieBottomSheetDialog(
        modifier = modifier,
        useHandle = true,
        sheetState = bottomSheetState,
        sheetContent = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .runIf(navigationBarsPaddingVisible) {
                        navigationBarsPadding()
                    }
                    .padding(bottom = 16.dp),
            ) {
                items(items) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .quackClickable(
                                rippleEnabled = false,
                            ) {
                                item.onClick()
                                closeSheet()
                            }
                            .padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        QuackImage(
                            src = item.icon,
                            size = DpSize(width = 24.dp, height = 24.dp),
                        )
                        QuackSubtitle2(
                            modifier = Modifier.padding(start = 8.dp),
                            text = stringResource(id = item.text),
                        )
                    }
                }
            }
        },
        content = content,
    )
}
