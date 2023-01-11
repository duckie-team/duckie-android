/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("unused")

package team.duckie.app.android.data.recommendation.repository

import androidx.paging.PagingSource
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.toJsonObject
import team.duckie.app.android.data.recommendation.mapper.toDomain
import team.duckie.app.android.data.recommendation.model.RecommendationData
import team.duckie.app.android.data.recommendation.paging.RecommendationPagingSource
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.domain.recommendation.model.RecommendationJumbotronItem
import team.duckie.app.android.domain.recommendation.model.SearchType
import team.duckie.app.android.domain.recommendation.repository.RecommendationRepository
import team.duckie.app.android.util.kotlin.ExperimentalApi

class RecommendationRepositoryImpl : RecommendationRepository {
    @ExperimentalApi
    override suspend fun fetchRecommendations(): PagingSource<Int, RecommendationItem> {
        val response = client.get {
            url("/recommendations")
        }

        return responseCatching(response.bodyAsText()) { body ->
            @Suppress("UNUSED_VARIABLE")
            val recommendations = body.toJsonObject<RecommendationData>().toDomain().recommendations
            RecommendationPagingSource()
        }
    }

    @ExperimentalApi
    override suspend fun fetchJumbotrons(): List<RecommendationJumbotronItem> {
        val response = client.get {
            url("/recommendations")
            parameter("page", 1)
        }

        return responseCatching(response.bodyAsText()) { body ->
            body.toJsonObject<RecommendationData>().toDomain().jumbotrons
        }
    }

    @ExperimentalApi
    override suspend fun fetchFollowingTest() {
        // TODO(limsaehyun): repository 작업 필요
    }

    @ExperimentalApi
    override suspend fun fetchRecommendFollowing() {
        // TODO(limsaehyun): repository 작업 필요
    }

    @ExperimentalApi
    override suspend fun fetchRecommendTags(
        tag: String,
        type: SearchType,
    ) {
        // TODO(limsaehyun): repository 작업 필요
    }
}
