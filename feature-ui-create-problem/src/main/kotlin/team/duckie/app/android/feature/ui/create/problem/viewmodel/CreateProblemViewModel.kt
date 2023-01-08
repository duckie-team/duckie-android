/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("MaxLineLength")

// TODO(문제만들기 담당자): OptIn 제거
@file:OptIn(OutOfDateApi::class)

package team.duckie.app.android.feature.ui.create.problem.viewmodel

import android.net.Uri
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import team.duckie.app.android.domain.category.usecase.GetCategoriesUseCase
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.ChoiceModel
import team.duckie.app.android.domain.exam.model.ExamBody
import team.duckie.app.android.domain.exam.model.ImageChoiceModel
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.exam.model.ShortModel
import team.duckie.app.android.domain.exam.model.ThumbnailType
import team.duckie.app.android.domain.exam.model.getDefaultAnswer
import team.duckie.app.android.domain.exam.model.toChoice
import team.duckie.app.android.domain.exam.model.toImageChoice
import team.duckie.app.android.domain.exam.model.toShort
import team.duckie.app.android.domain.exam.usecase.MakeExamUseCase
import team.duckie.app.android.domain.gallery.usecase.LoadGalleryImagesUseCase
import team.duckie.app.android.feature.ui.create.problem.viewmodel.sideeffect.CreateProblemSideEffect
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemPhotoState
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemState
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.util.kotlin.OutOfDateApi
import team.duckie.app.android.util.kotlin.copy
import team.duckie.app.android.util.kotlin.duckieClientLogicProblemException
import team.duckie.app.android.util.viewmodel.BaseViewModel

@Singleton
internal class CreateProblemViewModel @Inject constructor(
    private val makeExamUseCase: MakeExamUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val loadGalleryImagesUseCase: LoadGalleryImagesUseCase,
) : BaseViewModel<CreateProblemState, CreateProblemSideEffect>(CreateProblemState()) {
    /**
     * [galleryImages] 의 mutable 한 객체를 나타냅니다.
     *
     * @see galleryImages
     */
    private var mutableGalleryImages = persistentListOf<String>()

    /**
     * [LoadGalleryImagesUseCase] 를 통해 얻어온 이미지 목록을 저장합니다.
     * `ProfileScreen` 에서 `PhotoPicker` 에 사용할 이미지 목록을 불러오기 위해 사용됩니다.
     */
    val galleryImages: ImmutableList<String> get() = mutableGalleryImages

    suspend fun makeExam() {
        makeExamUseCase(dummyParam).onSuccess { isSuccess: Boolean ->
            print(isSuccess) // TODO(EvergreenTree97) 문제 만들기 3단계에서 사용 가능
        }.onFailure {
            it.printStackTrace()
        }
    }

    suspend fun getCategories() {
        getCategoriesUseCase(false).onSuccess { categories ->
            updateState { prevState ->
                prevState.copy(
                    examInformation = prevState.examInformation.copy(
                        isCategoryLoading = false,
                        categories = categories.toImmutableList()
                    )
                )
            }
        }.onFailure {
            print("실패")
        }
    }

    suspend fun onClickArrowBack() {
        postSideEffect {
            CreateProblemSideEffect.FinishActivity
        }
    }

    fun updatePhotoState(photoState: CreateProblemPhotoState?) = updateState { prevState ->
        prevState.copy(photoState = photoState)
    }

    fun onClickCategory(
        index: Int,
    ) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    categorySelection = index
                ),
            )
        }
    }

    fun onClickExamArea(scrollPosition: Int) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    scrollPosition = scrollPosition,
                )
            )
        }
        navigateStep(CreateProblemStep.FindExamArea)
    }

    fun navigateStep(step: CreateProblemStep) {
        updateState { prevState ->
            prevState.copy(
                createProblemStep = step,
            )
        }
    }

    fun setExamTitle(examTitle: String) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    examTitle = examTitle,
                ),
            )
        }
    }

    fun setExamDescription(examDescription: String) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    examDescription = examDescription,
                ),
            )
        }
    }

    fun setCertifyingStatement(certifyingStatement: String) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    certifyingStatement = certifyingStatement,
                ),
            )
        }
    }

    fun setExamArea(
        examArea: String,
        cursorPosition: Int,
    ) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    foundExamArea = prevState.examInformation.foundExamArea.copy(
                        examArea = examArea,
                        cursorPosition = cursorPosition,
                    ),
                ),
            )
        }
    }

    fun onClickSearchListHeader() {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    isExamAreaSelected = true,
                ),
            )
        }
        navigateStep(CreateProblemStep.ExamInformation)
    }

    fun onClickSearchList(index: Int) {
        updateState { prevState ->
            prevState.copy(
                createProblemStep = CreateProblemStep.ExamInformation,
                examInformation = prevState.examInformation.run {
                    copy(
                        isExamAreaSelected = true,
                        foundExamArea = foundExamArea.copy(
                            examArea = foundExamArea.searchResults[index]
                        )
                    )
                },
            )
        }
    }

    fun onClickCloseTag(isExamAreaSelected: Boolean) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.run {
                    copy(
                        isExamAreaSelected = isExamAreaSelected,
                        foundExamArea = foundExamArea.copy(
                            examArea = ""
                        ),
                    )
                }
            )
        }
    }

    fun onExamAreaFocusChanged(isFocused: Boolean) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    examDescriptionFocused = isFocused,
                )
            )
        }
    }

    // CreateProblem
    /**
     * 문제를 만든다. 아래 규칙대로 [Problem] 을 만든다.
     *
     * [Problem.question]: 처음 문제를 만들 때 기본 [Question.Type] 은 [Question.Text] 이다.
     * [Problem.answer]: 기본 [Answer.Type] 은 타입값에 기반하여 생성해준다. (객관식일 경우, 기본 Choice 개수는 0개이다)
     * [Problem.correctAnswer]: 처음 문제를 만들 때 기본 값은 빈 값이다.
     * [Problem.hint]: 처음 문제를 만들 때 기본 값은 null 값이다.
     * [Problem.memo]: 처음 문제를 만들 때 기본 값은 null 값이다.
     */
    fun addProblem(answerType: Answer.Type) = updateState { prevState ->
        val newQuestion = Question.Text(text = "")
        val newAnswer = answerType.getDefaultAnswer()

        with(prevState.createProblem) {
            val newQuestions = questions.copy { add(newQuestion) }
            val newAnswers = answers.copy { add(newAnswer) }
            val newCorrectAnswers = correctAnswers.copy { add("") }
            val newHints = hints.copy { add("") }
            val newMemos = memos.copy { add("") }

            prevState.copy(
                createProblem = prevState.createProblem.copy(
                    questions = newQuestions.toPersistentList(),
                    answers = newAnswers.toPersistentList(),
                    correctAnswers = newCorrectAnswers.toPersistentList(),
                    hints = newHints.toPersistentList(),
                    memos = newMemos.toPersistentList(),
                )
            )
        }
    }

    /** [questionIndex + 1] 번 문제를 삭제한다. */
    fun removeProblem(questionIndex: Int) = updateState { prevState ->
        with(prevState.createProblem) {
            val newQuestions = questions.copy { removeAt(questionIndex) }
            val newAnswers = answers.copy { removeAt(questionIndex) }
            val newCorrectAnswers = correctAnswers.copy { removeAt(questionIndex) }
            val newHints = hints.copy { removeAt(questionIndex) }
            val newMemos = memos.copy { removeAt(questionIndex) }

            prevState.copy(
                createProblem = prevState.createProblem.copy(
                    questions = newQuestions.toPersistentList(),
                    answers = newAnswers.toPersistentList(),
                    correctAnswers = newCorrectAnswers.toPersistentList(),
                    hints = newHints.toPersistentList(),
                    memos = newMemos.toPersistentList(),
                ),
            )
        }
    }

    /**
     * [questionIndex + 1] 번 문제를 설정합니다.
     *
     * [특정 문제 타입][questionType]으로 설정되며
     * [텍스트][title], [이미지 소스 목록][urlSource]을 추가적으로 받아 해당 문제를 특정 값으로 초기 설정합니다.
     */
    fun setQuestion(
        questionType: Question.Type?,
        questionIndex: Int,
        title: String? = null,
        urlSource: Uri? = null,
    ) = updateState { prevState ->
        val newQuestions = prevState.createProblem.questions.toMutableList()
        val prevQuestion = newQuestions[questionIndex]
        val newQuestion = when (questionType) {
            Question.Type.Text -> Question.Text(
                title ?: (prevQuestion.text),
            )

            Question.Type.Image -> Question.Image(
                title ?: prevQuestion.text,
                "${urlSource ?: prevQuestion}"
            )

            Question.Type.Audio -> Question.Audio(
                title ?: prevQuestion.text,
                "${urlSource ?: prevQuestion}"
            )

            Question.Type.Video -> Question.Video(
                title ?: prevQuestion.text,
                "${urlSource ?: prevQuestion}"
            )

            else -> null
        }
        newQuestion?.let { newQuestions[questionIndex] = it }

        prevState.copy(
            createProblem = prevState.createProblem.copy(
                questions = newQuestions.toPersistentList()
            ),
        )
    }

    /** [questionIndex + 1] 번 문제의 문제 타입을 [특정 답안 타입][answerType]으로 변경합니다. */
    fun editAnswersType(
        questionIndex: Int,
        answerType: Answer.Type
    ) = updateState { prevState ->
        val newAnswers = prevState.createProblem.answers.toMutableList()
        when (answerType) {
            Answer.Type.ShortAnswer -> newAnswers[questionIndex].toShort()
            Answer.Type.Choice -> newAnswers[questionIndex].toChoice()
            Answer.Type.ImageChoice -> newAnswers[questionIndex].toImageChoice()
        }.let { newAnswers[questionIndex] = it }

        prevState.copy(
            createProblem = prevState.createProblem.copy(
                answers = newAnswers.toPersistentList()
            ),
        )
    }

    /**
     * [questionIndex + 1] 번 문제의 [answerIndex + 1] 번 답안을 설정합니다.
     * 주관식은 첫 번째 값만 변경합니다.
     *
     * [특정 답안 타입][answerType]으로 설정되며,
     * [텍스트][answer], [이미지 소스][urlSource]을 추가적으로 받아 특정 답안을 해당 값으로 초기 설정합니다.
     */
    fun setAnswer(
        questionIndex: Int,
        answerIndex: Int,
        answerType: Answer.Type,
        answer: String? = null,
        urlSource: Uri? = null,
    ) = updateState { prevState ->
        val newAnswers = prevState.createProblem.answers.toMutableList()

        newAnswers[questionIndex].getEditedAnswers(
            answerIndex,
            answerType,
            answer,
            urlSource
        ).let { newAnswers[questionIndex] = it }

        prevState.copy(
            createProblem = prevState.createProblem.copy(
                answers = newAnswers.toPersistentList()
            ),
        )
    }

    /** [questionIndex + 1] 번 문제의 [answerIndex + 1] 번 답안을 삭제 합니다. */
    fun removeAnswer(
        questionIndex: Int,
        answerIndex: Int,
    ) = updateState { prevState ->
        val newAnswers = prevState.createProblem.answers.toMutableList()
        val newAnswer = newAnswers[questionIndex]
        newAnswers[questionIndex] = when (newAnswer) {
            // TODO(riflockle7): 발현 케이스 확인을 위해 이렇게 두었으며, 추후 state 명세하면서 없앨 예정.
            is Answer.Short -> duckieClientLogicProblemException(message = "주관식 답변은 삭제할 수 없습니다.")

            is Answer.Choice -> Answer.Choice(
                newAnswer.choices.copy { removeAt(answerIndex) }.toImmutableList()
            )

            is Answer.ImageChoice -> Answer.ImageChoice(
                newAnswer.imageChoice.copy { removeAt(answerIndex) }.toImmutableList()
            )
        }

        prevState.copy(
            createProblem = prevState.createProblem.copy(
                answers = newAnswers.toPersistentList()
            ),
        )
    }

    /**
     * [모든 문제의 답안 목록][this] 에서
     * [questionIndex + 1] 번 문제의 [answerIndex + 1] 번 답안을 수정합니다.
     * 이후 [questionIndex + 1] 문제의 답안 목록을 가져옵니다.
     */
    private fun Answer.getEditedAnswers(
        answerIndex: Int,
        answerType: Answer.Type,
        answer: String?,
        urlSource: Uri?
    ): Answer {
        return when (answerType) {
            Answer.Type.ShortAnswer -> this.toShort(answer)
            Answer.Type.Choice -> this.toChoice(answerIndex, answer)
            Answer.Type.ImageChoice -> this.toImageChoice(
                answerIndex,
                answer,
                urlSource?.let { "$this" }
            )
        }
    }

    /** [questionIndex + 1] 번 문제의 [정답][correctAnswer]을 설정합니다. */
    fun setCorrectAnswer(
        questionIndex: Int,
        correctAnswer: String,
    ) = updateState { prevState ->
        val newCorrectAnswers =
            prevState.createProblem.correctAnswers.toMutableList()
        newCorrectAnswers[questionIndex] = correctAnswer

        prevState.copy(
            createProblem = prevState.createProblem.copy(
                correctAnswers = newCorrectAnswers.toPersistentList()
            ),
        )
    }

    /**
     * [questionIndex + 1] 번 문제의 답안을 추가합니다.
     * [답안의 유형][answerType]에 맞춰 값이 추가 됩니다.
     */
    fun addAnswer(
        questionIndex: Int,
        answerType: Answer.Type,
    ) = updateState { prevState ->
        val newAnswers = prevState.createProblem.answers.toMutableList()
        val newAnswer = newAnswers[questionIndex]

        when (answerType) {
            Answer.Type.ShortAnswer -> error("주관식은 답이 여러개가 될 수 없습니다.")

            Answer.Type.Choice -> {
                val choiceAnswer =
                    newAnswer as? Answer.Choice ?: duckieClientLogicProblemException()
                val newChoices = choiceAnswer.choices.copy { this.add(ChoiceModel("")) }
                Answer.Choice(newChoices.toImmutableList())
            }

            Answer.Type.ImageChoice -> {
                val choiceAnswer =
                    newAnswer as? Answer.ImageChoice ?: duckieClientLogicProblemException()
                val newImageChoices =
                    choiceAnswer.imageChoice.copy { this.add(ImageChoiceModel("", "")) }
                Answer.ImageChoice(newImageChoices.toImmutableList())
            }
        }.let { newAnswers[questionIndex] = it }

        prevState.copy(
            createProblem = prevState.createProblem.copy(
                questions = prevState.createProblem.questions,
                answers = newAnswers.toPersistentList()
            ),
        )
    }

    // AdditionalInfo
    fun setThumbnail(thumbnail: Any?) {
        updateState { prevState ->
            prevState.copy(
                additionalInfo = prevState.additionalInfo.copy(
                    thumbnail = thumbnail,
                ),
            )
        }
    }

    fun setButtonTitle(buttonTitle: String) {
        updateState { prevState ->
            prevState.copy(
                additionalInfo = prevState.additionalInfo.copy(
                    takeTitle = buttonTitle,
                )
            )
        }
    }

    fun setTempTag(tempTag: String) {
        updateState { prevState ->
            prevState.copy(
                additionalInfo = prevState.additionalInfo.copy(
                    tempTag = tempTag,
                )
            )
        }
    }

    fun addTag(tag: String) {
        updateState { prevState ->
            val newTags = prevState.additionalInfo.tags
                .copy { add(tag) }
                .toImmutableList()
            prevState.copy(
                additionalInfo = prevState.additionalInfo.copy(
                    tags = newTags,
                )
            )
        }
    }

    /** 갤러리에서 이미지 목록을 조회합니다. */
    suspend fun loadGalleryImages() {
        loadGalleryImagesUseCase()
            .onSuccess { images ->
                postSideEffect {
                    CreateProblemSideEffect.UpdateGalleryImages(images)
                }
            }
            .onFailure { exception ->
                updateState { prevState ->
                    prevState.copy(error = Error(exception))
                }
                postSideEffect {
                    CreateProblemSideEffect.ReportError(exception)
                }
            }
    }

    /** `PhotoPicker` 에서 표시할 이미지 목록을 업데이트합니다. */
    internal fun addGalleryImages(images: List<String>) {
        mutableGalleryImages = persistentListOf(*images.toTypedArray())
    }

    fun isAllFieldsNotEmpty(): Boolean {
        return with(currentState.examInformation) {
            categorySelection >= 0 && isExamAreaSelected && examTitle.isNotEmpty() && examDescription.isNotEmpty() && certifyingStatement.isNotEmpty()
        }
    }
}

private val dummyParam = ExamBody(
    // TODO(EvergreenTree97): 문제 만들기 3단계 작업 시 테스트 후 삭제 필요
    title = "제 1회 도로 패션영역",
    description = "도로의 패션을 파헤쳐보자 ㅋㅋ",
    mainTagId = 3,
    subTagIds = persistentListOf(5, 15, 2, 3, 4),
    categoryId = 3,
    thumbnailImageUrl = "https://duckie-resource.s3.ap-northeast-2.amazonaws.com/exam/thumbnail/1669793968813",
    certifyingStatement = "열심히 살지 말라고 하셨다",
    thumbnailType = ThumbnailType.Image,
    buttonTitle = "TestText",
    isPublic = true,
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
    userId = 1,
)
