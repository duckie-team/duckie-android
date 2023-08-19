/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.ranking.datasource

import team.duckie.app.android.data.ranking.model.RankingOrderType
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.user.model.User

interface RankingDataSource {
    suspend fun getUserRankings(page: Int): List<User>

    suspend fun getExamRankings(
        page: Int,
        order: RankingOrderType,
        tagId: Int?,
    ): List<Exam>
}
