/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.recommendation.model

data class RecommendationsResponse(
    val recommendations: List<Recommendation>,
    val jumbotrons: List<Jumbotron>,
    val page: Int,
    val offset: Int,
    val limit: Int,
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
