/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.repository

import team.duckie.app.android.data.exam.datasource.remote.ExamDataSource
import team.duckie.app.android.data.exam.mapper.toData
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.exam.model.ExamParam
import team.duckie.app.android.domain.exam.repository.ExamRepository
import javax.inject.Inject

class ExamRepositoryImpl @Inject constructor(
    private val examDataSource: ExamDataSource,
) : ExamRepository {
    override suspend fun makeExam(examParam: ExamParam): Boolean{
        return examDataSource.postExams(examParam.toData())
    }
}
