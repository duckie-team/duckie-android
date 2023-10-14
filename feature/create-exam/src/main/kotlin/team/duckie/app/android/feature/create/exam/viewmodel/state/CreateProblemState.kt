/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.create.exam.viewmodel.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.ExamThumbnailBody
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.exam.model.ThumbnailType
import team.duckie.app.android.domain.exam.model.getDefaultAnswer
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User

internal data class CreateProblemState(
    val me: User? = null,
    val isNetworkError: Boolean = false,
    val isEditMode: Boolean = false,
    val createExamStep: CreateProblemStep = CreateProblemStep.CreateExam,
    val examInformation: ExamInformation = ExamInformation(),
    val createExam: CreateProblem = CreateProblem(),
    val additionalInfo: AdditionInfo = AdditionInfo(),
    val findResultType: FindResultType = FindResultType.MainTag,
    val photoState: CreateProblemPhotoState? = null,
    val defaultThumbnail: String = "",
    val isMakeExamUploading: Boolean = false,
) {
    data class ExamInformation(
        val isCategoryLoading: Boolean = true,
        val categories: ImmutableList<Category> = persistentListOf(),
        val categorySelection: Int = -1,
        val isMainTagSelected: Boolean = false,
        val prevExamTitle: String = "",
        val examTitle: String = "",
        val examDescription: String = "",
        val certifyingStatement: String = "",
        val searchMainTag: SearchScreenData = SearchScreenData(),
        val scrollPosition: Int = 0,
        val examDescriptionFocused: Boolean = false,
    ) {
        val mainTag: String
            get() = searchMainTag.results.firstOrNull()?.name ?: ""

        val selectedCategory: Category?
            get() = if (categorySelection == -1) {
                null
            } else {
                categories[categorySelection]
            }

        fun examThumbnailBody(me: User): ExamThumbnailBody {
            return ExamThumbnailBody(
                category = selectedCategory?.name ?: duckieResponseFieldNpe("선택된 카테고리가 있어야 합니다."),
                certifyingStatement = certifyingStatement,
                mainTag = mainTag,
                nickName = me.nickname,
                title = examTitle,
                type = ThumbnailType.Text.value,
            )
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
    val searchResults: ImmutableList<Tag> = persistentListOf(),
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
