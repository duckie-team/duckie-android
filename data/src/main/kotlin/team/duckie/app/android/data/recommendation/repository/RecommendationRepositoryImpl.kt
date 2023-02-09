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
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.coroutines.awaitObjectResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import team.duckie.app.android.data._datasource.bodyAsText
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.buildJson
import team.duckie.app.android.data._util.jsonMapper
import team.duckie.app.android.data.category.model.CategoryData
import team.duckie.app.android.data.recommendation.mapper.toDomain
import team.duckie.app.android.data.recommendation.model.RecommendationData
import team.duckie.app.android.data.recommendation.model.RecommendationJumbotronItemData
import team.duckie.app.android.data.recommendation.paging.RecommendationPagingSource
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.data.user.model.DuckPowerResponse
import team.duckie.app.android.data.user.model.UserFollowingResponse
import team.duckie.app.android.data.user.model.UserFollowingRecommendationsResponse
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.domain.recommendation.model.RecommendationJumbotronItem
import team.duckie.app.android.domain.recommendation.model.SearchType
import team.duckie.app.android.domain.recommendation.repository.RecommendationRepository
import team.duckie.app.android.domain.user.model.UserFollowing
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.kotlin.ExperimentalApi
import team.duckie.app.android.util.kotlin.fastMap
import javax.inject.Inject

/**
 * [fetchRecommendatiins] 에서 사용되는 paging 단위
 */
internal const val ITEMS_PER_PAGE = 16

class RecommendationRepositoryImpl @Inject constructor(
    private val fuel: Fuel,
) : RecommendationRepository {

    override fun fetchRecommendations(): Flow<PagingData<RecommendationItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = true,
                maxSize = 200,
            ),
            pagingSourceFactory = {
                RecommendationPagingSource(
                    fetchRecommendations = { fetchRecommendations(it) },
                )
            },
        ).flow
    }

    private suspend fun fetchRecommendations(page: Int): RecommendationData =
        withContext(Dispatchers.IO) {
            val (_, response) = fuel
                .get(
                    path = "/recommendations",
                    parameters = listOf("page" to page),
                )
                .responseString()

            return@withContext responseCatching(
                response.statusCode,
                response.bodyAsText(),
            ) {
                jsonMapper.readValue(response.bodyAsText(), RecommendationData::class.java)
            }
        }

    override suspend fun fetchJumbotrons(): List<RecommendationJumbotronItem> =
        withContext(Dispatchers.IO) {
            val (_, response) = fuel
                .get(
                    path = "/recommendations",
                    parameters = listOf("page" to 1),
                )
                .responseString()

            return@withContext responseCatching(
                response.statusCode,
                response.bodyAsText(),
            ) {
                jsonMapper.readValue(response.bodyAsText(), RecommendationData::class.java)
                    .toDomain().jumbotrons
            }
        }

    override suspend fun fetchRecommendFollowing(): UserFollowing = withContext(Dispatchers.IO) {
        val (_, response) = fuel
            .get(
                path = "/recommendations",
                parameters = listOf("page" to 1),
            )
            .responseString()

        return@withContext responseCatching(
            response.statusCode,
            response.bodyAsText(),
        ) {
            jsonMapper.readValue(response.bodyAsText(), UserFollowingResponse::class.java).toDomain()
        }
    }

    @ExperimentalApi
    override suspend fun fetchRecommendTags(
        tag: String,
        type: SearchType,
    ) {
        // TODO(limsaehyun): repository 작업 필요
    }
}
