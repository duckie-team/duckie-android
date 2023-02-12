/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress(
    "MaxLineLength",
    "MagicNumber",
    "TooGenericExceptionCaught",
) // TODO(limsaehyun): 더미데이터를 위해 임시로 구현, 추후에 삭제 필요

package team.duckie.app.android.data.recommendation.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import team.duckie.app.android.data._datasource.bodyAsText
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.jsonMapper
import team.duckie.app.android.data.category.model.CategoryData
import team.duckie.app.android.data.exam.model.ExamData
import team.duckie.app.android.data.recommendation.mapper.toDomain
import team.duckie.app.android.data.recommendation.model.RecommendationData
import team.duckie.app.android.data.recommendation.model.RecommendationItemData
import team.duckie.app.android.data.recommendation.model.RecommendationJumbotronItemData
import team.duckie.app.android.data.recommendation.repository.ITEMS_PER_PAGE
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.data.user.model.DuckPowerResponse
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.domain.recommendation.model.RecommendationFeeds
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.util.kotlin.ExperimentalApi
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import team.duckie.app.android.util.kotlin.fastMap

private suspend fun dummyReturn(a: Int): RecommendationData {
    println("return skeat! $a")
    val recommendations = (0..15).map {
        RecommendationItemData(
            title = "쿠키좀 구워봤어?\n#웹툰 퀴즈$it",
            tag = TagData(3, "#웹툰"),
            exams = (0..3).map { exam ->
                ExamData(
                    id = exam,
                    title = "문제 $exam",
                    description = "설명",
                    user = UserResponse(
                        id = 1,
                        nickName = "faker",
                        duckPower = DuckPowerResponse(
                            id = 1,
                            tier = "덕력 20%",
                            tag = TagData(
                                id = 1,
                                name = "도로패션",
                            ),
                        ),
                        profileImageUrl = "https://img.freepik.com/free-icon/user_318-804790.jpg?w=2000",
                    ),
                    thumbnailUrl = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    buttonTitle = "열심히 풀어주세요",
                    certifyingStatement = "이것은 content 인비다",
                    solvedCount = 1,
                    answerRate = 0.17f,
                    category = CategoryData(1, "카테고리"),
                    mainTag = TagData(1, "방탄소년단"),
                    type = "text",
                    status = "open",
                )
            },
        )
    }
    val jumbotrons = (0..2).map {
        RecommendationJumbotronItemData(
            id = it,
            title = "타이틀 4",
            description = "설명",
            thumbnailUrl = "https://user-images.githubusercontent.com/80076029/206894333-d060111d-e78e-4294-8686-908b2c662f19.png",
            buttonTitle = "열심히 풀어주세요",
            certifyingStatement = "이것은 content 인비다",
            solvedCount = 1,
            answerRate = 0.17f,
            category = CategoryData(1, "카테고리"),
            type = "type",
        )
    }
    return RecommendationData(
        jumbotrons = jumbotrons,
        recommendations = recommendations,
        page = a,
    )
}

private const val STARTING_KEY = 1

internal class RecommendationPagingSource(
    private val fetchRecommendations: suspend (Int) -> RecommendationFeeds,
) : PagingSource<Int, RecommendationItem>() {

    @ExperimentalApi
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecommendationItem> {
        return try {
            val currentPage = params.key ?: STARTING_KEY
            val response = dummyReturn(currentPage).toDomain()
            LoadResult.Page(
                data = response.recommendations,
                prevKey = if(currentPage == STARTING_KEY) null else currentPage - 1,
                nextKey = if(response.recommendations.isEmpty() || response.recommendations.size < ITEMS_PER_PAGE) null else currentPage + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RecommendationItem>): Int {
        return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
    }
}
