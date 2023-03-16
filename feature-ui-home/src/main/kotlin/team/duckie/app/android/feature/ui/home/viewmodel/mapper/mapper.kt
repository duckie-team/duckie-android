/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel.mapper

import kotlinx.collections.immutable.toPersistentList
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.recommendation.model.ExamType
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserFollowing
import team.duckie.app.android.feature.ui.home.viewmodel.state.HomeState
import team.duckie.app.android.util.kotlin.fastMap

internal fun UserFollowing.toUiModel() =
    HomeState.RecommendUserByTopic(
        topic = category.name,
        users = users.fastMap(User::toUiModel).toPersistentList(),
    )

internal fun Exam.toJumbotronModel() =
    HomeState.HomeRecommendJumbotron(
        examId = id,
        coverUrl = thumbnailUrl,
        title = title,
        content = description ?: "",
        buttonContent = buttonTitle ?: "",
        type = ExamType.toExamType(type ?: ""),
    )

internal fun User.toUiModel() =
    HomeState.RecommendUserByTopic.User(
        userId = id,
        profileImgUrl = profileImageUrl ?: "",
        nickname = nickname,
        favoriteTag = duckPower?.tag?.name ?: "",
        tier = duckPower?.tier ?: "",
    )

internal fun Exam.toFollowingModel() =
    HomeState.RecommendExam(
        coverUrl = thumbnailUrl,
        title = title,
        examId = this.id,
        owner = HomeState.RecommendExam.User(
            nickname = user?.nickname ?: "",
            profileImgUrl = user?.profileImageUrl ?: "",
            favoriteTag = user?.duckPower?.tag?.name ?: "",
            tier = user?.duckPower?.tier ?: "",
        ),
    )
