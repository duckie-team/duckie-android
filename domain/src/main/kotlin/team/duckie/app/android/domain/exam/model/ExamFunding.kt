/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.user.model.User

@Immutable
data class ExamFunding(
    val contributorCount: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val user: User,
)
