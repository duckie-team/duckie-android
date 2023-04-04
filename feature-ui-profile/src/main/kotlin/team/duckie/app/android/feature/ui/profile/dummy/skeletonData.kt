/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.dummy

import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.util.kotlin.randomString

fun skeletonExams(count: Int = 10) = (1..count).map {
    Exam.empty().copy(
        id = it,
        user = User.empty().copy(nickname = randomString(3)),
        title = randomString(6),
        solvedCount = 20,
        description = randomString(6),
    )
}.toImmutableList()
