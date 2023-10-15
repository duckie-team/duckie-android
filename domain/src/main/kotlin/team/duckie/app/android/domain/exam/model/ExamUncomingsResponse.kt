/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.home.model.HomeFunding

@Immutable
data class ExamUncomingsResponse(
    val exams: List<HomeFunding>,
    val page: Int,
)
