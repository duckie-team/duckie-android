/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress(
    "MaxLineLength",
    "MagicNumber",
) // TODO(limsaehyun): 더미데이터를 위해 임시로 구현, 추후에 삭제 필요

package team.duckie.app.android.data.recommendation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.collections.immutable.ImmutableList
import kotlin.math.max
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data._util.toJsonObject
import team.duckie.app.android.data.category.model.CategoryData
import team.duckie.app.android.data.exam.model.AnswerData
import team.duckie.app.android.data.exam.model.ChoiceData
import team.duckie.app.android.data.exam.model.ExamData
import team.duckie.app.android.data.exam.model.ProblemData
import team.duckie.app.android.data.exam.model.QuestionData
import team.duckie.app.android.data.recommendation.mapper.toDomain
import team.duckie.app.android.data.recommendation.model.RecommendationData
import team.duckie.app.android.data.recommendation.model.RecommendationItemData
import team.duckie.app.android.data.recommendation.model.RecommendationJumbotronItemData
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.util.kotlin.ExperimentalApi
import team.duckie.app.android.util.kotlin.OutOfDateApi
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import team.duckie.app.android.util.kotlin.fastMap

private suspend fun dummyReturn(a: Int): RecommendationData {
    delay(1000)
    val recommendations = (a..a + 10).map {
        RecommendationItemData(
            title = "쿠키좀 구워봤어?\n#웹툰 퀴즈${it}",
            tag = TagData(3, "#웹툰"),
            exams = (0..3).map { exam ->
                ExamData(
                    id = exam,
                    title = "문제 $exam",
                    description = "설명",
                    user = UserResponse(
                        id = 1,
                        nickName = "faker",
                    ),
                    thumbnailUrl = "https://user-images.githubusercontent.com/80076029/206901501-8d8a97ea-b7d8-4f18-84e7-ba593b4c824b.png",
                    buttonTitle = "열심히 풀어주세요",
                    certifyingStatement = "이것은 content 인비다",
                    solvedCount = 1,
                    answerRate = 0.17f,
                    category = CategoryData(1, "카테고리"),
                    mainTag = TagData(1, "방탄소년단"),
                    subTags = persistentListOf(
                        TagData(2, "블랙핑크"),
                        TagData(3, "볼빨간사춘기"),
                        TagData(4, "위너"),
                    ),
                    problems = persistentListOf(
                        ProblemData(
                            id = 1,
                            answer = AnswerData.Choice(
                                type = "choice",
                                choices = persistentListOf(
                                    ChoiceData(text = "교촌치킨"),
                                    ChoiceData(text = "BBQ"),
                                    ChoiceData(text = "지코바"),
                                    ChoiceData(text = "엄마가 만들어준 치킨"),
                                ),
                            ),
                            question = QuestionData.Text(
                                text = "다음 중 도로가 가장 좋아하는 치킨 브랜드는?",
                                type = "text",
                            ),
                            hint = "",
                            memo = "",
                            correctAnswer = "지코바",
                        ),
                    ),
                    type = "text",
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
            mainTag = TagData(1, "방탄소년단"),
            subTags = persistentListOf(
                TagData(2, "블랙핑크"),
                TagData(3, "볼빨간사춘기"),
                TagData(4, "위너"),
            ),
            type = "type",
        )
    }
    return RecommendationData(
        jumbotrons = jumbotrons,
        recommendations = recommendations,
        page = a,
        offset = 1,
        limit = 1,
    )
}

private const val STARTING_KEY = 1

class RecommendationPagingSource : PagingSource<Int, RecommendationItem>() {

    @ExperimentalApi
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecommendationItem> {
        return try {
            val nextPageNumber = params.key ?: STARTING_KEY
            val response = dummyReturn(nextPageNumber)
            val range = nextPageNumber.until(nextPageNumber + params.loadSize)

            LoadResult.Page(
                data = response.recommendations?.fastMap(RecommendationItemData::toDomain)?.toPersistentList()
                    ?: duckieResponseFieldNpe("${this::class.java.simpleName}.recommendations"),
                prevKey = when (nextPageNumber) {
                    STARTING_KEY -> null
                    else -> when (
                        val prevKey =
                            ensureValidKey(key = range.first - params.loadSize)
                    ) {
                        STARTING_KEY -> null
                        else -> prevKey
                    }
                },
                nextKey = range.last + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RecommendationItem>): Int {
        return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)

    @ExperimentalApi
    private suspend fun fetchRecommendations(page: Int): ImmutableList<RecommendationItem> {
        return dummyReturn(page).toDomain().recommendations
        val response = client.get {
            url("/recommendations")
            parameter("page", page)
        }

        return responseCatching(response.bodyAsText()) { body ->
            body.toJsonObject<RecommendationData>().toDomain().recommendations
        }
    }
}
