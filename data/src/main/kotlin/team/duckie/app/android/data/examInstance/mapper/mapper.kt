/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.examInstance.mapper

import team.duckie.app.android.data.exam.mapper.toDomain
import team.duckie.app.android.data.examInstance.model.ExamInstanceData
import team.duckie.app.android.data.examInstance.model.ProblemInstanceData
import team.duckie.app.android.domain.examInstance.model.ExamInstance
import team.duckie.app.android.domain.examInstance.model.ProblemInstance
import team.duckie.app.android.util.kotlin.OutOfDateApi
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import team.duckie.app.android.util.kotlin.fastMap

@OptIn(OutOfDateApi::class)
internal fun ExamInstanceData.toDomain() = ExamInstance(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    exam = exam?.toDomain() ?: duckieResponseFieldNpe("${this::class.java.simpleName}.exam"),
    problemInstances = problemInstances?.fastMap { it.toDomain() },
    status = status ?: duckieResponseFieldNpe("${this::class.java.simpleName}.status"),
)

@OptIn(OutOfDateApi::class)
internal fun ProblemInstanceData.toDomain() = ProblemInstance(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    problem = problem?.toDomain()
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.problem"),
    status = status ?: duckieResponseFieldNpe("${this::class.java.simpleName}.status"),
    submittedAnswer = submittedAnswer,
)
