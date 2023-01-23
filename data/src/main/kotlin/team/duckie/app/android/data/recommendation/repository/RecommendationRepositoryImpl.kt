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
import kotlinx.coroutines.flow.Flow
import team.duckie.app.android.data.category.model.CategoryData
import team.duckie.app.android.data.recommendation.mapper.toDomain
import team.duckie.app.android.data.recommendation.model.RecommendationJumbotronItemData
import team.duckie.app.android.data.recommendation.paging.RecommendationPagingSource
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.data.user.model.UserFollowingData
import team.duckie.app.android.data.user.model.UserFollowingRecommendationsData
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.domain.recommendation.model.RecommendationJumbotronItem
import team.duckie.app.android.domain.recommendation.model.SearchType
import team.duckie.app.android.domain.recommendation.repository.RecommendationRepository
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.UserFollowing
import team.duckie.app.android.util.kotlin.ExperimentalApi
import team.duckie.app.android.util.kotlin.fastMap

/**
 * fetchRecommendatiins 에서 사용되는 paging 단위
 */
private const val ITEMS_PER_PAGE = 10

class RecommendationRepositoryImpl : RecommendationRepository {
    @ExperimentalApi
    override fun fetchRecommendations(): Flow<PagingData<RecommendationItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = true,
                maxSize = 200,
            ),
            pagingSourceFactory = { RecommendationPagingSource() }
        ).flow
    }

    @ExperimentalApi
    override suspend fun fetchJumbotrons(): List<RecommendationJumbotronItem> {
//        val response = client.get {
//            url("/recommendations")
//            parameter("page", 1)
//        }
//
//        return responseCatching(response.bodyAsText()) { body ->
//            body.toJsonObject<RecommendationData>().toDomain().jumbotrons
//        }
//      TODO(limsaehyun): Server Request 필요

        val response = (0..2).map {
            RecommendationJumbotronItemData(
                id = 1,
                title = "제 1회 도로 셀카영역",
                description = "아 저 근데 재밌어요 같아요\n내 시험 최고",
                thumbnailUrl = "https://duckie-resource.s3.ap-northeast-2.amazonaws.com/exam/thumbnail/1673924133028",
                certifyingStatement = "Love Doro",
                solvedCount = 3,
                buttonTitle = "도로 덕질하러 가기",
                type = "text",
                answerRate = 0.123.toFloat(),
                category = CategoryData(
                    id = 1,
                    name = "카테고리",
                    thumbnailUrl = "",
                    popularTags = null,
                ),
                mainTag = TagData(
                    id = 1,
                    name = "sub tag",
                ),
                subTags = listOf(
                    TagData(
                        id = 1,
                        name = "main tag1",
                    ),
                    TagData(
                        id = 2,
                        name = "main tag2",
                    ),
                ),
            )
        }.toList()

        return response.fastMap(RecommendationJumbotronItemData::toDomain)
    }

    @ExperimentalApi
    override suspend fun fetchFollowingTest() {

    }

    @ExperimentalApi
    override suspend fun fetchRecommendFollowing(): UserFollowing {
        val response = UserFollowingData(
            followingRecommendations = (0..10).map {
                UserFollowingRecommendationsData(
                    category = CategoryData(
                        id = 1,
                        name = "카테고리$it",
                        thumbnailUrl = "",
                        popularTags = emptyList(),
                    ),
                    user = (0..10).map {
                        UserResponse(
                            tier = 1,
                            nickName = "user$it",
                            id = 1,
                            profileImageUrl = "https://w7.pngwing.com/pngs/906/222/png-transparent-computer-icons-user-profile-avatar-french-people-computer-network-heroes-black.png",
                            favoriteTags = (0..3).map {
                                Tag(
                                    id = 1,
                                    name = "sub tag",
                                )
                            }.toList(),
                            favoriteCategories = emptyList(),
                        )
                    }
                )
            }
        )

        return response.toDomain()
    }

    @ExperimentalApi
    override suspend fun fetchRecommendTags(
        tag: String,
        type: SearchType,
    ) {
        // TODO(limsaehyun): repository 작업 필요
    }
}
