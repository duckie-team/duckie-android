/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.examInstance.repository

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.exam.model.ExamInstanceBody
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmit
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmitBody
import team.duckie.app.android.domain.examInstance.model.ExamInstance
import team.duckie.app.android.util.kotlin.OutOfDateApi

@Immutable
interface ExamInstanceRepository {
    @OutOfDateApi
    suspend fun getExamInstance(examInstanceId: Int): ExamInstance

    @OutOfDateApi
    suspend fun postExamInstance(examInstanceBody: ExamInstanceBody): Boolean

    @OutOfDateApi
    suspend fun postExamInstanceSubmit(
        id: Int,
        examInstanceSubmitBody: ExamInstanceSubmitBody,
    ): ExamInstanceSubmit
}
