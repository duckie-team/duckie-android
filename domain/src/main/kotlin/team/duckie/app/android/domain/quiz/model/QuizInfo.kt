/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.quiz.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.user.model.User

@Immutable
data class QuizInfo(
    val id: Int,
    val correctProblemCount: Int,
    val score: Int,
    val user: User,
    val time: Int,
    val reaction: String?,
) {
    companion object {
        fun dummy() = QuizInfo(
            id = 4214,
            correctProblemCount = 49,
            score = 0,
            reaction = "이거 쉽네요 ㅋㅋ",
            user = User(
                id = 23,
                nickname = "킹도로",
                profileImageUrl = "http://k.kakaocdn.net/dn/biMfAm/btrVzyBqsTy/1xPkCTgSUk1Vnh1KGIcW81/img_640x640.jpg",
                duckPower = null,
                permissions = null,
                status = null,
                follow = null,
                favoriteCategories = null,
                favoriteTags = null,
                introduction = null,
            ),
            time = 227,
        )
    }
}
