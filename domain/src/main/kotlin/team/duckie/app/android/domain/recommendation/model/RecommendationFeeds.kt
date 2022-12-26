/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.recommendation.model

data class RecommendationFeeds(
    val jumbotrons: List<Jumbotron>,
    val recommendations: List<Recommendation>,
) {
    data class Jumbotron(
        val title: String,
        val tag: List<Tag>,
        val exams: List<Exam>,
    ) {
        data class Tag(
            val id: Int,
            val name: String,
        )

        data class Exam(
            val id: Int,
        )
    }
    data class Recommendation(
        val title: String,
    )
}
