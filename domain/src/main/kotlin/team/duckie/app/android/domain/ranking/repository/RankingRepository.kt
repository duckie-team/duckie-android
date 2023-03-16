/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.ranking.repository

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.user.model.User

@Immutable
interface RankingRepository {

    fun getUserRankings(): Flow<PagingData<User>>

    fun getExamRankingsBySolvedCount(tagId: Int?): Flow<PagingData<Exam>>

    fun getExamRankingsByAnswerRate(tagId: Int?): Flow<PagingData<Exam>>
}
