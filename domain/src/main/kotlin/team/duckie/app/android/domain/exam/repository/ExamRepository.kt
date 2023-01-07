/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.repository

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.exam.model.ExamBody
import team.duckie.app.android.util.kotlin.OutOfDateApi

@Immutable
interface ExamRepository {
    @OutOfDateApi
    suspend fun makeExam(exam: ExamBody): Boolean

    @OutOfDateApi
    suspend fun getExam(id: Int): Exam
}
