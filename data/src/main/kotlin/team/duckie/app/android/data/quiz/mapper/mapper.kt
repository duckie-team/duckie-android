/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.quiz.mapper

import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.data.challengecomment.mapper.toDomain
import team.duckie.app.android.data.challengecomment.model.ChallengeCommentResponse
import team.duckie.app.android.data.exam.mapper.toDomain
import team.duckie.app.android.data.quiz.model.GetQuizResponse
import team.duckie.app.android.data.quiz.model.PostQuizResponse
import team.duckie.app.android.data.quiz.model.QuizExamData
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.domain.quiz.model.Quiz
import team.duckie.app.android.domain.quiz.model.QuizExam
import team.duckie.app.android.domain.quiz.model.QuizResult

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
    wrongAnswer = wrongAnswer?.toDomain(),
    ranking = ranking,
    requirementAnswer = requirementAnswer,
    isBestRecord = isBestRecord ?: false, // for start-exam
    popularComments = popularComments?.data?.fastMap(ChallengeCommentResponse::toDomain)?.toImmutableList(),
    commentsTotal = popularComments?.total,
    solveRank = solveRank,
    percentileRank = percentileRank,
)

internal fun QuizExamData.toDomain() = QuizExam(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    answerRate = answerRate,
    heart = heart,
)

internal fun GetQuizResponse.MeAndMostWrongAnswer.toDomain() = QuizResult.WrongAnswer(
    meTotal = me.total,
    meData = me.data,
    mostTotal = most.total,
    mostData = most.data,
)
