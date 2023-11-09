/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.solve.problem.viewmodel.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.examInstance.model.ProblemInstance
import team.duckie.app.android.domain.recommendation.model.ExamType

data class SolveProblemState(
    val examId: Int = -1,
    val isProblemsLoading: Boolean = true,
    val isError: Boolean = false,
    val examType: ExamType = ExamType.Text,
    val currentPageIndex: Int = 0,
    val problems: ImmutableList<ProblemInstance> = persistentListOf(),
    val quizProblems: ImmutableList<Problem> = persistentListOf(),
    val inputAnswers: ImmutableList<InputAnswer> = persistentListOf(),
    val requirementAnswer: String = "",
    val totalPage: Int = 0,
    val time: Int = 0,
    val musicExamState: MusicExamState = MusicExamState(),
)

data class MusicExamState(
    val currentPage: Int = 0,
    val totalPage: Int = 0,
    val question: Question = Question.Audio("", ""),
    val hint: String = "",
    val correctAnswer: String = "",
    val inputAnswer: String = "",
    val challengeCount: Int = DefaultChallengeCount,
    val useWordCountAndSpacing: Boolean,
    val useFirstWord: Boolean,
    val hintUsage: Hint,
) {
    data class Hint(
        val useWordCountAndSpacing: Boolean = false,
        val useFirstWord: Boolean = false,
        val useArtistName: Boolean = false,
    )

    companion object {
        const val DefaultChallengeCount = 10
    }
}

data class InputAnswer(
    val number: Int = -1,
    val answer: String = "",
)
