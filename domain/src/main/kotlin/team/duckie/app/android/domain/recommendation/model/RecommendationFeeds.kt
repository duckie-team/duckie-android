/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.recommendation.model

import kotlinx.collections.immutable.PersistentList

data class RecommendationFeeds(
    val recommendations: List<Recommendation>,
    val jumbotrons: List<Jumbotron>,
) {
    data class Jumbotron(
        val coverUrl: String,
        val title: String,
        val content: String,
        val buttonContent: String,
    )

    data class Recommendation(
        val title: String,
        val tag: String,
        val exams: List<Exam>,
    ) {
        data class Exam(
            val coverImg: String,
            val nickname: String,
            val title: String,
            val examineeNumber: Int,
            val recommendId: Int,
        )
    }
}
