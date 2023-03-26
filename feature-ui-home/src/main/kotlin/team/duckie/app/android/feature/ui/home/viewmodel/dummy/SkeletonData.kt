/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel.dummy

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.recommendation.model.ExamType
import team.duckie.app.android.domain.recommendation.model.RecommendationItem
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.feature.ui.home.viewmodel.state.HomeState
import team.duckie.app.android.util.kotlin.randomString

internal val skeletonJumbotrons = persistentListOf(
    HomeState.HomeRecommendJumbotron(
        examId = 1,
        coverUrl = "",
        title = randomString(12),
        content = randomString(18),
        buttonContent = randomString(10),
        type = ExamType.Video,
    ),
)

internal val skeletonRecommendationItems = listOf(
    RecommendationItem(
        id = 0,
        title = randomString(20),
        tag = null,
        exams = listOf(
            Exam.empty().copy(
                user = User.empty().copy(nickname = randomString(3)),
                solvedCount = 20,
                description = randomString(6),
            ),
        ),
    ),
)

internal val skeletonFollowingExam = (0..2).map {
    HomeState.RecommendExam(
        coverUrl = "",
        title = randomString(12),
        examId = 1,
        owner = HomeState.RecommendExam.User.empty().copy(nickname = randomString(3)),
    )
}.toPersistentList()
