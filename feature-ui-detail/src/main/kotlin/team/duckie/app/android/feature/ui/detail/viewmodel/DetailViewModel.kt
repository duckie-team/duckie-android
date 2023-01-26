/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(OutOfDateApi::class)

package team.duckie.app.android.feature.ui.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.exam.model.ShortModel
import team.duckie.app.android.domain.exam.repository.ExamRepository
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.constant.DuckieTier
import team.duckie.app.android.domain.user.model.User
import team.duckie.app.android.domain.user.repository.UserRepository
import team.duckie.app.android.feature.ui.detail.viewmodel.sideeffect.DetailSideEffect
import team.duckie.app.android.feature.ui.detail.viewmodel.state.DetailState
import team.duckie.app.android.util.kotlin.OutOfDateApi
import team.duckie.app.android.util.ui.const.Extras
import javax.inject.Inject

const val DelayTime = 2000L

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val examRepository: ExamRepository,
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle,
) : ContainerHost<DetailState, DetailSideEffect>, ViewModel() {
    override val container = container<DetailState, DetailSideEffect>(DetailState.Loading)

    suspend fun initExamData() {
        val examId = savedStateHandle.getStateFlow(Extras.ExamId, -1).value
        val userId = savedStateHandle.getStateFlow(Extras.UserId, -1).value

        val exam = runCatching { examRepository.getExam(examId) }.getOrNull() ?: dummyExam
        val user = runCatching { userRepository.get(userId) }.getOrNull() ?: dummyUser
        delay(DelayTime)
        intent {
            reduce { DetailState.Success(exam, user) }
        }
    }
}

private val dummyExam = Exam(
    id = 1,
    // TODO(EvergreenTree97): 문제 만들기 3단계 작업 시 테스트 후 삭제 필요
    title = "제 1회 도로 패션영역",
    description = "도로의 패션을 파헤쳐보자 ㅋㅋ",
    thumbnailUrl = "https://duckie-resource.s3.ap-northeast-2.amazonaws.com/exam/thumbnail/1669793968813",
    buttonTitle = "TestText",
    certifyingStatement = "열심히 살지 말라고 하셨다",
    solvedCount = 1,
    answerRate = 0.66f,
    category = Category(
        id = 1,
        name = "test",
        thumbnailUrl = "https://duckie-resource.s3.ap-northeast-2.amazonaws.com/exam/thumbnail/1669793968813",
        popularTags = persistentListOf(
            Tag(4, "도로록PopularTag1"),
            Tag(5, "도로록PopularTag2"),
        ),
    ),
    mainTag = Tag(1, "도로록Main1"),
    subTags = persistentListOf(
        Tag(2, "도로록Sub1"),
        Tag(3, "도로록Sub2"),
    ),
    problems = persistentListOf(
        Problem(
            id = 1,
            question = Question.Text(
                text = "",
            ),
            answer = Answer.Short(
                answer = ShortModel("바보"),
            ),
            memo = "test memo 1",
            hint = "test hint 1",
            correctAnswer = "3",
        ),
        Problem(
            id = 2,
            question = Question.Text(
                text = "",
            ),
            answer = Answer.Short(
                answer = ShortModel("바보"),
            ),
            memo = "test memo 1",
            hint = "test hint 1",
            correctAnswer = "3",
        ),
        Problem(
            id = 3,
            question = Question.Text(
                text = "",
            ),
            answer = Answer.Short(
                answer = ShortModel("바보"),
            ),
            memo = "test memo 1",
            hint = "test hint 1",
            correctAnswer = "3",
        ),
        Problem(
            id = 4,
            question = Question.Text(
                text = "",
            ),
            answer = Answer.Short(
                answer = ShortModel("바보"),
            ),
            memo = "test memo 1",
            hint = "test hint 1",
            correctAnswer = "3",
        ),
        Problem(
            id = 5,
            question = Question.Text(
                text = "",
            ),
            answer = Answer.Short(
                answer = ShortModel("바보"),
            ),
            memo = "test memo 1",
            hint = "test hint 1",
            correctAnswer = "3",
        ),
    ),
    type = "text",
    user = User(
        id = 1,
        nickname = "doro",
        profileImageUrl = "",
        tier = DuckieTier.DuckBronze,
        favoriteTags = persistentListOf(),
        favoriteCategories = persistentListOf(),
    ),
    status = "PENDING",
)

private val dummyUser = User(
    id = 1,
    nickname = "도로롥",
    profileImageUrl = "",
    tier = DuckieTier.DuckKid,
    favoriteTags = persistentListOf(
        Tag(2, "도로록Sub1"),
        Tag(3, "도로록Sub2"),
    ),
    favoriteCategories = persistentListOf(
        Category(
            id = 1,
            name = "test",
            thumbnailUrl = "https://duckie-resource.s3.ap-northeast-2.amazonaws.com/exam/thumbnail/1669793968813",
            popularTags = persistentListOf(
                Tag(4, "도로록PopularTag1"),
                Tag(5, "도로록PopularTag2"),
            ),
        ),
    ),
)
