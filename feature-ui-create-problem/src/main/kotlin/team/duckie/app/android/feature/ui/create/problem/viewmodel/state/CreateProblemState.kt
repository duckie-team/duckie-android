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
import team.duckie.app.android.domain.exam.model.ExamThumbnailBody
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.exam.model.ThumbnailType
import team.duckie.app.android.domain.exam.model.getDefaultAnswer
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe

internal data class CreateProblemState(
    val me: User,
    val isEditMode: Boolean = false,
    val createProblemStep: CreateProblemStep = CreateProblemStep.Loading,
    val examInformation: ExamInformation = ExamInformation(),
    val createProblem: CreateProblem = CreateProblem(),
    val additionalInfo: AdditionInfo = AdditionInfo(),
    val findResultType: FindResultType = FindResultType.MainTag,
    val photoState: CreateProblemPhotoState? = null,
    val defaultThumbnail: String = "",
) {
    data class ExamInformation(
        val isCategoryLoading: Boolean = true,
        val categories: ImmutableList<Category> = persistentListOf(),
        val categorySelection: Int = -1,
        val isMainTagSelected: Boolean = false,
        val examTitle: String = "",
        val examDescription: String = "",
        val certifyingStatement: String = "",
        val searchMainTag: SearchScreenData = SearchScreenData(),
        val scrollPosition: Int = 0,
        val examDescriptionFocused: Boolean = false,
    ) {
        val mainTag: String
            get() = searchMainTag.results.firstOrNull()?.name ?: ""

        val examThumbnailBody: ExamThumbnailBody
            get() = ExamThumbnailBody(
                category = selectedCategory?.name ?: duckieResponseFieldNpe("선택된 카테고리가 있어야 합니다."),
                certifyingStatement = certifyingStatement,
                mainTag = mainTag,
                // TODO(riflockle7): 유저 데이터 활용 가능해질 시 처리 필요
                nickName = "nickname",
                title = examTitle,
                // TODO(riflockle7): 기획이 나오는대로 해당 값 처리 방법 구현해야 함
                type = "text",
            )

        // TODO(riflockle7):
        val selectedCategory: Category?
            get() = if (categorySelection == -1) {
                null
            } else {
                categories[categorySelection]
            }
    }

    /** 문제 만들기 2단계 화면에서 사용하는 data 모음 */
    data class CreateProblem(
        // Problem 의 데이터를 Map 으로 쪼개 놓은 영역들
        val questions: ImmutableList<Question> = persistentListOf(Question.Text("")),
        val answers: ImmutableList<Answer> = persistentListOf(
            Answer.Type.Choice.getDefaultAnswer(),
        ),
        val correctAnswers: ImmutableList<String> = persistentListOf(""),
        val hints: ImmutableList<String> = persistentListOf(""),
        val memos: ImmutableList<String> = persistentListOf(""),
        val isValidate: Boolean = false,
    ) {
        data class Error(val throwable: Throwable?)
    }

    /** 문제 만들기 3단계 화면에서 사용하는 data 모음 */
    data class AdditionInfo(
        val thumbnail: Any = "",
        val thumbnailType: ThumbnailType = ThumbnailType.Text,
        val takeTitle: String = "",
        val isSubTagsAdded: Boolean = false,
        val searchSubTags: SearchScreenData = SearchScreenData(),
    ) {
        val subTags: ImmutableList<Tag>
            get() = searchSubTags.results
    }
}

data class SearchScreenData(
    val searchResults: ImmutableList<String> = persistentListOf(
        // TODO(EvergreenTree97): Server Request
        "도로",
        "도로 주행",
        "도로 셀카",
        "도로 패션",
    ),
    val textFieldValue: String = "",
    val results: ImmutableList<Tag> = persistentListOf(),
)

enum class FindResultType {
    MainTag, SubTags;

    fun isMultiMode() = this == SubTags
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
