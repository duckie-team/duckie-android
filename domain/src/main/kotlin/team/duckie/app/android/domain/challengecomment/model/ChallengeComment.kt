/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.challengecomment.model

import team.duckie.app.android.domain.heart.model.Heart
import team.duckie.app.android.domain.user.model.User
import java.util.Date

data class ChallengeComment(
    val id: Int,
    val message: String,
    val wrongAnswer: String,
    val heartCount: Int,
    val createdAt: Date,
    val user: User,
    val heart: Heart?,
) {
    fun isMine(userId: Int): Boolean {
        return user.id == userId
    }
}
