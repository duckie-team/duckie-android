/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.examInstance.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.model.ProfileExam

@Immutable
data class ProfileExamInstance(
    val id: Int,
    val exam: ProfileExam?,
)
