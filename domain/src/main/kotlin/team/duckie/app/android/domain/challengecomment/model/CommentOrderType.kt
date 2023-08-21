/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.challengecomment.model

enum class CommentOrderType(val type: String, val kor: String) {
    DATE(
        type = "date",
        kor = "최신순",
    ),
    LIKE(
        type = "like",
        kor = "인기순",
    )
}
