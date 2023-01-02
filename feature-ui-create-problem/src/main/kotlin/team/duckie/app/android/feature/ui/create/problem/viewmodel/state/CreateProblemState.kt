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

data class CreateProblemState(
    // TODO(riflockle7): 추후 구현 완료 시 원래대로 돌려놓아야 함
    val createProblemStep: CreateProblemStep = CreateProblemStep.CreateProblem,
    val examInformation: ExamInformation = ExamInformation(),
    val error: ExamInformation.Error? = null,
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
        val createProblemArea: CreateProblemArea = CreateProblemArea(),
        val additionalInfoArea: AdditionInfoArea = AdditionInfoArea(),
        val photoState: CreateProblemPhotoState? = null,
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

        /** 문제 만들기 2단계 화면에서 사용하는 data 모음 */
        data class CreateProblemArea(
            // Problem 의 데이터를 Map 으로 쪼개 놓은 영역들
            val questions: ImmutableList<Question> = persistentListOf(),
            val answers: ImmutableList<Answer> = persistentListOf(),
            val correctAnswers: ImmutableList<String> = persistentListOf(),
            val hints: ImmutableList<String> = persistentListOf(),
            val memos: ImmutableList<String> = persistentListOf(),
        )

        data class AdditionInfoArea(
            val thumbnail: Any? = null,
            val takeTitle: String = "",
            val tempTag: String = "",
            val tags: ImmutableList<String> = persistentListOf(),
        )
        data class Error(val throwable: Throwable?)
    }
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

    object AdditionalThumbnailType: CreateProblemPhotoState()
}
