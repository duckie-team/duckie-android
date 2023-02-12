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
import team.duckie.app.android.domain.user.model.UserFollowing
import team.duckie.app.android.feature.ui.home.viewmodel.state.HomeState
import team.duckie.app.android.util.kotlin.fastMap

// TODO(riflockle7): GET /users/following API commit
internal fun UserFollowing.toUiModel() =
    HomeState.RecommendUserByTopic(
        topic = category.name,
        users = users?.fastMap(User::toUiModel)?.toPersistentList()
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
    // TODO(riflockle7): user 엔티티 commit
    HomeState.RecommendUserByTopic.User(
        userId = id,
        profileImgUrl = profileImageUrl ?: "",
        nickname = nickname,
        favoriteTag = duckPower?.tag?.name ?: "",
        tier = duckPower?.tier ?: "",
    )

internal fun Exam.toUiModel() =
    // TODO(riflockle7): exam 엔티티 commit
    HomeState.FollowingTest(
        coverUrl = thumbnailUrl,
        title = title,
        owner = HomeState.FollowingTest.User(
            nickname = user?.nickname ?: "",
            profileImgUrl = user?.profileImageUrl ?: "",
            favoriteTag = user?.duckPower?.tag?.name ?: "",
            tier = user?.duckPower?.tier ?: "",
        ),
    )
