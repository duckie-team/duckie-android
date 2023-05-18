/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.quiz.mapper

import team.duckie.app.android.data.exam.mapper.toDomain
import team.duckie.app.android.data.quiz.model.GetQuizResponse
import team.duckie.app.android.data.quiz.model.PostQuizResponse
import team.duckie.app.android.data.quiz.model.QuizExamData
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.domain.quiz.model.Quiz
import team.duckie.app.android.domain.quiz.model.QuizExam
import team.duckie.app.android.domain.quiz.model.QuizResult
import team.duckie.app.android.util.kotlin.exception.duckieResponseFieldNpe

internal fun PostQuizResponse.toDomain() = Quiz(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    time = time ?: duckieResponseFieldNpe("${this::class.java.simpleName}.time"),
    correctProblemCount = correctProblemCount
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.correctProblemCount"),
    exam = exam?.toDomain() ?: duckieResponseFieldNpe("${this::class.java.simpleName}.exam"),
    score = score ?: duckieResponseFieldNpe("${this::class.java.simpleName}.score"),
    user = user?.toDomain() ?: duckieResponseFieldNpe("${this::class.java.simpleName}.user"),
)

internal fun GetQuizResponse.toDomain() = QuizResult(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    time = time ?: duckieResponseFieldNpe("${this::class.java.simpleName}.time"),
    correctProblemCount = correctProblemCount
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.correctProblemCount"),
    exam = exam?.toDomain() ?: duckieResponseFieldNpe("${this::class.java.simpleName}.exam"),
    score = score ?: duckieResponseFieldNpe("${this::class.java.simpleName}.score"),
    user = user?.toDomain() ?: duckieResponseFieldNpe("${this::class.java.simpleName}.user"),
    wrongProblem = wrongProblem?.toDomain(),
)

internal fun QuizExamData.toDomain() = QuizExam(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    answerRate = answerRate,
    heart = heart,
)
