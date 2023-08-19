/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.challengecomment.model

import android.service.autofill.UserData
import team.duckie.app.android.domain.heart.model.Heart
import team.duckie.app.android.domain.user.model.User

data class ChallengeComment(
    val id: Int,
    val message: String,
    val wrongAnswer: String,
    val heartCount: Int,
    val createdAt: String,
    val user: User,
    val heart: Heart?,
)
