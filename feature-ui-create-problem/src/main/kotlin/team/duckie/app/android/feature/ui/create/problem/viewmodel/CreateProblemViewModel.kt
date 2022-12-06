/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.viewmodel

import android.util.Log
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.ExamParam
import team.duckie.app.android.domain.exam.model.ProblemItem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.exam.model.Tag
import team.duckie.app.android.domain.exam.usecase.MakeExamUseCase
import javax.inject.Singleton

@Singleton
class CreateProblemViewModel(
    private val makeExamUseCase: MakeExamUseCase
) {
    private val TAG = CreateProblemViewModel::class.simpleName

    suspend fun makeExam() {
        makeExamUseCase(dummyParmam).onSuccess { exam ->
            Log.d(TAG, exam.toString())
        }
    }
}

val dummyParmam = ExamParam(
    title = "Test Title 2",
    description = "Test Description 2",
    mainTag = Tag(
        id = 1,
        "1"
    ),
    subTag = listOf(
        Tag(
            2,
            "2",
        )
    ),
    thumbnailImageUrl = "Test Image Url",
    userId = 1,
    certifyingStatement = "Test 필적 확인 문구1",
    thumbnailType = "image",
    buttonTitle = "Test Button Title 1",
    isPublic = true,
    problems = ProblemItem(
        questionObject = Question.Text,
        answerObject = Answer.ShortAnswer(
            shortAnswer = "바보"
        ),
        memo = "test memo 1",
        hint = "test hint 1",
        correctAnswer = "3",
    )
)
