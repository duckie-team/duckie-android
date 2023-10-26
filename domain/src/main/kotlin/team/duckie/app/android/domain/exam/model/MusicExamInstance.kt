/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

import team.duckie.app.android.domain.user.model.User

data class MusicExamInstance(
    val user: User,
    val takenTime: Double?,
    val score: Double?,
    val correctProblemCount: Int?,
    val status: String?,
)
