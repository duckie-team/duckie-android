/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.viewmodel.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.exam.model.getDefaultAnswer

internal data class CreateProblemState(
    val createProblemStep: CreateProblemStep = CreateProblemStep.CreateProblem,
    val examInformation: ExamInformation = ExamInformation(),
    val createProblem: CreateProblem = CreateProblem(),
    val additionalInfo: AdditionInfo = AdditionInfo(),
    val error: Error? = null,
    val photoState: CreateProblemPhotoState? = null,
) {
    data class ExamInformation(
        val isCategoryLoading: Boolean = true,
        val categories: ImmutableList<Category> = persistentListOf(),
        val categorySelection: Int = -1,
        val isExamAreaSelected: Boolean = false,
        val examTitle: String = "",
        val examDescription: String = "",
        val certifyingStatement: String = "",
        val foundExamArea: FoundExamArea = FoundExamArea(),
        val scrollPosition: Int = 0,
        val examDescriptionFocused: Boolean = false,
    ) {
        val examArea: String
            get() = foundExamArea.examArea

        data class FoundExamArea(
            val searchResults: ImmutableList<String> = persistentListOf(
                // TODO(EvergreenTree97): Server Request
                "도로",
                "도로 주행",
                "도로 셀카",
                "도로 패션",
            ),
            val examArea: String = "",
            val cursorPosition: Int = 0,
        )
    }

    /** 문제 만들기 2단계 화면에서 사용하는 data 모음 */
    data class CreateProblem(
        // Problem 의 데이터를 Map 으로 쪼개 놓은 영역들
        val questions: ImmutableList<Question> = persistentListOf(Question.Text("")),
        val answers: ImmutableList<Answer> = persistentListOf(
            Answer.Type.Choice.getDefaultAnswer(),
        ),
        val correctAnswers: ImmutableList<String> = persistentListOf(""),
        val hints: ImmutableList<String> = persistentListOf(),
        val memos: ImmutableList<String> = persistentListOf(),
    ) {
        data class Error(val throwable: Throwable?)
    }

    /** 문제 만들기 3단계 화면에서 사용하는 data 모음 */
    data class AdditionInfo(
        val thumbnail: Any? = null,
        val takeTitle: String = "",
        val tempTag: String = "",
        val tags: ImmutableList<String> = persistentListOf(),
    )
}

sealed class CreateProblemPhotoState {
    data class QuestionImageType(
        val questionIndex: Int,
        val value: Question?,
    ) : CreateProblemPhotoState()

    data class AnswerImageType(
        val questionIndex: Int,
        val answerIndex: Int,
        val value: Answer.ImageChoice,
    ) : CreateProblemPhotoState()

    object AdditionalThumbnailType : CreateProblemPhotoState()
}
