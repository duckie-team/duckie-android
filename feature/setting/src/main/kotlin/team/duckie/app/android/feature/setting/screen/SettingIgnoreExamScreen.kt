/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import team.duckie.app.android.common.compose.ui.content.ExamIgnoreLayout
import team.duckie.app.android.feature.setting.viewmodel.SettingViewModel
import team.duckie.app.android.feature.setting.viewmodel.state.SettingState

@Composable
internal fun SettingIgnoreExamScreen(
    vm: SettingViewModel,
    state: SettingState,
) {
    LaunchedEffect(key1 = Unit) {
        vm.getIgnoreExams()
    }

    LazyColumn {
        items(state.ignoreExams) { item ->
            ExamIgnoreLayout(
                examId = item.id,
                examThumbnailUrl = item.thumbnailUrl,
                name = item.title,
                onClickTrailingButton = { examId ->
                    vm.cancelIgnoreExam(examId = examId)
                },
            )
        }
    }
}
