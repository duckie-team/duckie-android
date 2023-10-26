/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

import team.duckie.app.android.domain.user.model.User

data class MyMusicRecord(
    val user: User,
    val takenTime: Float?,
    val score: Float?,
    val correctProblemCount: Int?,
    val ranking: Int?,
    val status: String?,
) {
    companion object {
        fun empty() = MyMusicRecord(
            user = User.empty(),
            takenTime = 0f,
            score = 0f,
            correctProblemCount = 0,
            ranking = 0,
            status = "",
        )
    }
}
