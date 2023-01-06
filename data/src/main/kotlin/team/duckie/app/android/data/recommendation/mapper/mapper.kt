/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.recommendation.mapper

import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.data.recommendation.model.RecommendationsResponse
import team.duckie.app.android.domain.recommendation.model.RecommendationFeeds
import team.duckie.app.android.util.kotlin.fastMap

internal fun RecommendationsResponse.Recommendation.toDomain(): RecommendationFeeds.Recommendation {
    return RecommendationFeeds.Recommendation(
        title = title,
        tag = tag,
        exams = exams
            .fastMap(RecommendationsResponse.Recommendation.Exam::toDomain)
            .toImmutableList(),
    )
}

internal fun RecommendationsResponse.Recommendation.Exam.toDomain() =
    RecommendationFeeds.Recommendation.Exam(
        title = title,
        coverImg = coverImg,
        nickname = nickname,
        examineeNumber = examineeNumber,
        recommendId = recommendId,
    )
