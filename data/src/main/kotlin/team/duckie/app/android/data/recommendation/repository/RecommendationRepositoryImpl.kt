/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("unused")

package team.duckie.app.android.data.recommendation.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.kittinunf.fuel.Fuel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import team.duckie.app.android.common.kotlin.ExperimentalApi
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.data._exception.util.responseCatchingFuel
import team.duckie.app.android.data.recommendation.mapper.toDomain
import team.duckie.app.android.data.recommendation.model.RecommendationData
import team.duckie.app.android.data.recommendation.model.RecommendationTypeData
import team.duckie.app.android.data.recommendation.paging.RecommendationPagingSource
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.recommendation.model.RecommendationFeeds
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.domain.recommendation.repository.RecommendationRepository
import javax.inject.Inject

class RecommendationRepositoryImpl @Inject constructor(
    private val fuel: Fuel,
) : RecommendationRepository {

    override fun fetchRecommendations(): Flow<PagingData<RecommendationItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = RecommendationsPagingPage,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                RecommendationPagingSource(
                    fetchRecommendations = { fetchRecommendations(it) },
                )
            },
        ).flow
    }

    override fun fetchMusicRecommendations(): Flow<PagingData<RecommendationItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = RecommendationsPagingPage,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                RecommendationPagingSource(
                    fetchRecommendations = {
                        fetchRecommendations(
                            page = it,
                            type = RecommendationTypeData.MUSIC,
                        )
                    },
                )
            },
        ).flow
    }

    private suspend fun fetchRecommendations(
        page: Int,
        type: RecommendationTypeData = RecommendationTypeData.NONE,
    ): RecommendationFeeds =
        withContext(Dispatchers.IO) {
            val (_, response) = fuel
                .get(
                    path = "/recommendations",
                    parameters = mutableListOf<Pair<String, Any?>>(
                        "page" to page,
                    ).also {
                        if (type != RecommendationTypeData.NONE) {
                            it.add("type" to type.value)
                        }
                    },
                )
                .responseString()

            return@withContext responseCatchingFuel(
                response = response,
                parse = RecommendationData::toDomain,
            )
        }

    override suspend fun fetchJumbotrons(): ImmutableList<Exam> {
        fetchRecommendations(1, RecommendationTypeData.NONE).jumbotrons?.let {
            return it
        } ?: duckieResponseFieldNpe("jumbotrons")
    }

    override suspend fun fetchMusicJumbotrons(): ImmutableList<Exam> {
        fetchRecommendations(1, RecommendationTypeData.MUSIC).jumbotrons?.let {
            return it
        } ?: duckieResponseFieldNpe("jumbotrons")
    }

    internal companion object {
        const val RecommendationsPagingPage = 3
    }
}
