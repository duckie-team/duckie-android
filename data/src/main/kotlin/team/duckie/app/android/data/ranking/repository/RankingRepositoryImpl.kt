/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.ranking.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import team.duckie.app.android.data.ranking.datasource.RankingDataSource
import team.duckie.app.android.data.ranking.model.OrderType
import team.duckie.app.android.data.ranking.paging.ExamRankingsPagingSource
import team.duckie.app.android.data.ranking.paging.UserRankingsPagingSource
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.ranking.repository.RankingRepository
import team.duckie.app.android.domain.user.model.User
import javax.inject.Inject

class RankingRepositoryImpl @Inject constructor(
    private val rankingDataSource: RankingDataSource,
) : RankingRepository {
    override suspend fun getUserRankings(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = PageSize,
                enablePlaceholders = true,
                maxSize = MaxSize,
            ),
            pagingSourceFactory = {
                UserRankingsPagingSource(
                    getUserRankings = {
                        rankingDataSource.getUserRankings(it)
                    },
                )
            },
        ).flow.flowOn(Dispatchers.IO)
    }

    override suspend fun getExamRankingsBySolvedCount(tagId: Int?): Flow<PagingData<Exam>> {
        return getExamRankings(
            orderType = OrderType.SolvedCount,
            tagId = tagId,
        )
    }

    override suspend fun getExamRankingsByAnswerRate(tagId: Int?): Flow<PagingData<Exam>> {
        return getExamRankings(
            orderType = OrderType.AnswerRate,
            tagId = tagId,
        )
    }

    private fun getExamRankings(
        orderType: OrderType,
        tagId: Int?,
    ): Flow<PagingData<Exam>> {
        return Pager(
            config = PagingConfig(
                pageSize = PageSize,
                enablePlaceholders = true,
                maxSize = MaxSize,
            ),
            pagingSourceFactory = {
                ExamRankingsPagingSource(
                    getExamRankings = {
                        rankingDataSource.getExamRankings(
                            page = it,
                            order = orderType,
                            tagId = tagId,
                        )
                    },
                )
            },
        ).flow.flowOn(Dispatchers.IO)
    }

    internal companion object {
        const val PageSize = 10

        const val MaxSize = 200
    }
}
