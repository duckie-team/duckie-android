/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel.mapper

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.recommendation.model.RecommendationJumbotronItem
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserFollowingRecommendations
import team.duckie.app.android.feature.ui.home.viewmodel.state.HomeState
import team.duckie.app.android.util.kotlin.OutOfDateApi
import team.duckie.app.android.util.kotlin.fastMap

internal fun UserFollowingRecommendations.toUiModel() =
    HomeState.RecommendUserByTopic(
        topic = category?.name ?: "",
        users = user?.fastMap(User::toUiModel)?.toPersistentList()
            ?: persistentListOf(),
    )

internal fun RecommendationJumbotronItem.toUiModel() =
    HomeState.HomeRecommendJumbotron(
        id = id,
        coverUrl = thumbnailUrl,
        title = title,
        content = description,
        buttonContent = buttonTitle,
        type = type,
    )

internal fun User.toUiModel() =
    HomeState.RecommendUserByTopic.User(
        userId = id,
        profileImgUrl = profileImageUrl,
        nickname = nickname,
        favoriteTag = duckPower?.tag?.name ?: "",
        tier = duckPower?.tier ?: "",
    )

@OptIn(OutOfDateApi::class)
internal fun Exam.toUiModel() =
    HomeState.FollowingTest(
        coverUrl = thumbnailUrl ?: "",
        title = title,
        owner = HomeState.FollowingTest.User(
            name = user.nickname,
            profile = user.profileImageUrl,
            favoriteTag = user.duckPower?.tag?.name ?: "",
            tier = user.duckPower?.tier ?: "",
        ),
    )
