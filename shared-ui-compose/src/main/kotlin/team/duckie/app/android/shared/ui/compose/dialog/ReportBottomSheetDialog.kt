/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.shared.ui.compose.dialog

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.shared.ui.compose.R
import team.duckie.app.android.util.kotlin.runIf
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSubtitle2
import team.duckie.quackquack.ui.modifier.quackClickable

const val ReportAlreadyExists = "이미 신고한 게시물 입니다!"

@Composable
fun ReportBottomSheetDialog(
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
                icon = R.drawable.ic_report,
                text = R.string.report,
                onClick = onReport,
            ),
        )
    }

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
                items(rememberBottomSheetItems) { item ->
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
