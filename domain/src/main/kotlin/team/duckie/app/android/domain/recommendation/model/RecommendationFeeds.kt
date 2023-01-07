/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.recommendation.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class RecommendationFeeds(
    val recommendations: ImmutableList<Recommendation>,
    val jumbotrons: ImmutableList<Jumbotron>,
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
        val exams: ImmutableList<Exam>,
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
