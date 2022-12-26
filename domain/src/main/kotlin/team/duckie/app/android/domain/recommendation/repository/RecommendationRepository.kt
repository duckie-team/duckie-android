/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.recommendation.repository

import androidx.compose.runtime.Immutable

/**
 * 홈 화면에서 활용하는 Repository
 *
 * TODO(limsaehyun): 데이터 Return 필요
 */
@Immutable
interface RecommendationRepository {
    suspend fun fetchRecommendations()

    suspend fun fetchFollowingTest()

    suspend fun fetchRecommendFollowing()
}
