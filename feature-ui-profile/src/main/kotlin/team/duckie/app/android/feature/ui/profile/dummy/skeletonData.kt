/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.dummy

import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.exam.model.ProfileExam
import team.duckie.app.android.domain.examInstance.model.ProfileExamInstance
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.model.UserProfile
import team.duckie.app.android.util.kotlin.randomString

fun skeletonUserProfile(count: Int = 10) = UserProfile.empty().copy(
    createdExams = skeletonProfileExams(),
    solvedExamInstances = skeletonProfileExamInstances(),
    heartExams = skeletonProfileExams(),
)

fun skeletonProfileExams(count: Int = 10) = (1..count).map {
    ProfileExam.empty().copy(
        id = it,
        user = User.empty().copy(nickname = randomString(3)),
        title = randomString(6),
        solvedCount = 20,
    )
}.toImmutableList()

fun skeletonProfileExamInstances(count: Int = 10) = (1..count).map {
    ProfileExamInstance.empty().copy(
        id = it,
        exam = ProfileExam(
            id = it,
            user = User.empty().copy(nickname = randomString(3)),
            title = randomString(6),
            solvedCount = 20,
            heartCount = 20,
            thumbnailUrl = ""
        )
    )
}.toImmutableList()

