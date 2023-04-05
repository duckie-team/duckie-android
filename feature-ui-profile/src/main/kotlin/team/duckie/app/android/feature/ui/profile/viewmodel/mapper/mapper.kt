/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.viewmodel.mapper

import team.duckie.app.android.domain.exam.model.ProfileExam
import team.duckie.app.android.domain.examInstance.model.ProfileExamInstance
import team.duckie.app.android.shared.ui.compose.DuckTestCoverItem

fun ProfileExam.toPresentation() = DuckTestCoverItem(
    testId = id,
    thumbnailUrl = thumbnailUrl,
    nickname = user?.nickname ?: "",
    title = title,
    solvedCount = solvedCount ?: 0,
    heartCount = heartCount ?: 0,
)

fun ProfileExamInstance.toPresentation() = DuckTestCoverItem(
    testId = exam?.id ?: 0,
    thumbnailUrl = exam?.thumbnailUrl,
    nickname = exam?.user?.nickname ?: "",
    title = exam?.title ?: "",
    solvedCount = exam?.solvedCount ?: 0,
    heartCount = exam?.heartCount ?: 0,
)
