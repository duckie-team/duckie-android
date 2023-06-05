/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.examInstance.mapper

import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.data.exam.mapper.toDomain
import team.duckie.app.android.data.examInstance.model.ExamInstanceData
import team.duckie.app.android.data.examInstance.model.ProblemInstanceData
import team.duckie.app.android.domain.examInstance.model.ExamInstance
import team.duckie.app.android.domain.examInstance.model.ExamStatus
import team.duckie.app.android.domain.examInstance.model.ProblemInstance

internal fun ExamInstanceData.toDomain() = ExamInstance(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    exam = exam?.toDomain(),
    problemInstances = problemInstances?.fastMap { it.toDomain() },
    status = status?.let { ExamStatus.from(it) }
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.status"),
    scoreImageUrl = scoreImageUrl,
)

internal fun ProblemInstanceData.toDomain() = ProblemInstance(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    problem = problem?.toDomain()
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.problem"),
    status = status ?: duckieResponseFieldNpe("${this::class.java.simpleName}.status"),
    submittedAnswer = submittedAnswer,
)
