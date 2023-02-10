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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import team.duckie.app.android.data._exception.util.responseCatchingFuel
import team.duckie.app.android.data.recommendation.mapper.toDomain
import team.duckie.app.android.data.recommendation.model.RecommendationData
import team.duckie.app.android.data.recommendation.paging.RecommendationPagingSource
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.domain.recommendation.model.RecommendationJumbotronItem
import team.duckie.app.android.domain.recommendation.model.SearchType
import team.duckie.app.android.domain.recommendation.repository.RecommendationRepository
import team.duckie.app.android.util.kotlin.ExperimentalApi
import javax.inject.Inject

/**
 * fetchRecommendatiins 에서 사용되는 paging 단위
 */
private const val ITEMS_PER_PAGE = 10

class RecommendationRepositoryImpl @Inject constructor(
    private val fuel: Fuel,
) : RecommendationRepository {
    @ExperimentalApi
    override fun fetchRecommendations(): Flow<PagingData<RecommendationItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = true,
                maxSize = 200,
            ),
            pagingSourceFactory = { RecommendationPagingSource() },
        ).flow
    }

    // TODO(riflockle7): GET /recommendations API commit
    @ExperimentalApi
    override suspend fun fetchJumbotrons(page: Int): List<RecommendationJumbotronItem> =
        withContext(Dispatchers.IO) {
        val (_, response) = fuel
            .post(
                "/recommendations",
                listOf("page" to page),
            )
            .responseString()

        val apiResponse = responseCatchingFuel(
            response = response,
            parse = RecommendationData::toDomain,
        )

        return@withContext apiResponse.jumbotrons
    }

    @ExperimentalApi
    override suspend fun fetchRecommendTags(
        tag: String,
        type: SearchType,
    ) {
        // TODO(limsaehyun): repository 작업 필요
    }
}
