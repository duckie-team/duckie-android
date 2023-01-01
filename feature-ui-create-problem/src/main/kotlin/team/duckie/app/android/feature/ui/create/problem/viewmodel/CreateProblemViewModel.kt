/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("MaxLineLength")

// TODO: OptIn 제거
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
import team.duckie.app.android.domain.exam.model.ThumbnailType
import team.duckie.app.android.domain.exam.usecase.MakeExamUseCase
import team.duckie.app.android.domain.gallery.usecase.LoadGalleryImagesUseCase
import team.duckie.app.android.feature.ui.create.problem.viewmodel.sideeffect.CreateProblemSideEffect
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemPhotoState
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemState
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.util.kotlin.OutOfDateApi
import team.duckie.app.android.util.kotlin.copy
import team.duckie.app.android.util.viewmodel.BaseViewModel

@Singleton
class CreateProblemViewModel @Inject constructor(
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
        prevState.copy(
            examInformation = prevState.examInformation.copy(photoState = photoState),
        )
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
    /** 문제를 만든다. 아래 규칙대로 [Problem] 을 만든다.
     *
     * [Problem.question]: 처음 문제를 만들 때 기본 [Question.Type] 은 [Question.Text] 이다.
     * [Problem.answer]: 기본 [Answer.Type] 은 타입값에 기반하여 생성해준다. (객관식일 경우, 기본 Choice 개수는 0개이다)
     * [Problem.correctAnswer]: 처음 문제를 만들 때 기본 값은 빈 값이다.
     * [Problem.hint]: 처음 문제를 만들 때 기본 값은 null 값이다.
     * [Problem.memo]: 처음 문제를 만들 때 기본 값은 null 값이다.
     */
    fun addProblem(answerType: Answer.Type) = updateState { prevState ->
        val newQuestion = Question.Text(text = "")
        val newAnswer = when (answerType) {
            Answer.Type.ShortAnswer -> Answer.Short("")
            Answer.Type.Choice -> Answer.Choice(persistentListOf())
            Answer.Type.ImageChoice -> Answer.ImageChoice(persistentListOf())
        }

        val prevCreateProblemArea = prevState.examInformation.createProblemArea
        val newProblemNo = prevCreateProblemArea.questions.size + 1
        val newQuestions = prevCreateProblemArea.questions.toMutableMap()
            .apply { this[newProblemNo] = newQuestion }
        val newAnswers = prevCreateProblemArea.answers.toMutableMap()
            .apply { this[newProblemNo] = newAnswer }
        val newCorrectAnswers = prevCreateProblemArea.correctAnswers.toMutableMap()
            .apply { this[newProblemNo] = "" }
        val newHints = prevCreateProblemArea.hints.toMutableMap()
            .apply { this[newProblemNo] = "" }
        val newMemos = prevCreateProblemArea.memos.toMutableMap()
            .apply { this[newProblemNo] = "" }

        prevState.copy(
            examInformation = prevState.examInformation.copy(
                createProblemArea = prevState.examInformation.createProblemArea.copy(
                    questions = newQuestions,
                    answers = newAnswers,
                    correctAnswers = newCorrectAnswers,
                    hints = newHints,
                    memos = newMemos,
                )
            ),
        )
    }

    fun setQuestion(
        questionType: Question.Type?,
        questionNo: Int,
        title: String? = null,
        urlSource: Uri? = null,
    ) = updateState { prevState ->
        val newQuestions = prevState.examInformation.createProblemArea.questions.toMutableMap()
        val newQuestion = newQuestions[questionNo]
        newQuestions[questionNo] = when (questionType) {
            Question.Type.Text -> Question.Text(
                title ?: (newQuestion?.text ?: ""),
            )

            Question.Type.Image -> Question.Image(
                title ?: newQuestion?.text ?: "",
                "${urlSource ?: newQuestion ?: ""}"
            )

            Question.Type.Audio -> Question.Audio(
                title ?: newQuestion?.text ?: "",
                "${urlSource ?: newQuestion ?: ""}"
            )

            Question.Type.Video -> Question.Video(
                title ?: newQuestion?.text ?: "",
                "${urlSource ?: newQuestion ?: ""}"
            )

            else -> null
        }
        prevState.copy(
            examInformation = prevState.examInformation.copy(
                createProblemArea = prevState.examInformation.createProblemArea.copy(
                    questions = newQuestions
                )
            ),
        )
    }

    fun setAnswers(
        questionNo: Int,
        answerType: Answer.Type?,
        answers: List<String>? = null,
        urlSources: List<Uri>? = null,
    ) = updateState { prevState ->
        val newAnswers = prevState.examInformation.createProblemArea.answers.toMutableMap()
        for (answerNo in 0 until newAnswers.size) {
            newAnswers.generateAnswers(
                questionNo,
                answerNo,
                answerType,
                answers?.get(answerNo),
                urlSources?.get(answerNo)
            )
        }

        prevState.copy(
            examInformation = prevState.examInformation.copy(
                createProblemArea = prevState.examInformation.createProblemArea.copy(
                    answers = newAnswers
                )
            ),
        )
    }

    fun setAnswer(
        questionNo: Int,
        answerNo: Int,
        answerType: Answer.Type?,
        answer: String? = null,
        urlSource: Uri? = null,
    ) = updateState { prevState ->
        val newAnswers = prevState.examInformation.createProblemArea.answers.toMutableMap()
            .generateAnswers(questionNo, answerNo, answerType, answer, urlSource)

        prevState.copy(
            examInformation = prevState.examInformation.copy(
                createProblemArea = prevState.examInformation.createProblemArea.copy(
                    answers = newAnswers
                )
            ),
        )
    }

    private fun MutableMap<Int, Answer?>.generateAnswers(
        questionNo: Int,
        answerNo: Int,
        answerType: Answer.Type?,
        answer: String?,
        urlSource: Uri?
    ): MutableMap<Int, Answer?> {
        val answerIndex = answerNo - 1
        val newAnswer = this[questionNo]
        this[questionNo] = when (answerType) {
            Answer.Type.ShortAnswer -> Answer.Short(
                answer ?: ((newAnswer as? Answer.Short)?.answer ?: ""),
            )

            Answer.Type.Choice -> when (newAnswer) {
                is Answer.Short -> Answer.Choice(persistentListOf())
                is Answer.Choice -> Answer.Choice(
                    newAnswer.choices.mapIndexed { index, choiceModel ->
                        if (index == answerIndex)
                            ChoiceModel(answer ?: choiceModel.text)
                        else
                            choiceModel
                    }.toPersistentList()
                )

                is Answer.ImageChoice -> Answer.Choice(
                    newAnswer.imageChoice.mapIndexed { index, imageChoiceModel ->
                        if (index == answerIndex)
                            ChoiceModel(answer ?: imageChoiceModel.text)
                        else
                            ChoiceModel(imageChoiceModel.text)
                    }.toPersistentList()
                )

                else -> null
            }

            Answer.Type.ImageChoice -> when (newAnswer) {
                is Answer.Short -> Answer.ImageChoice(persistentListOf())
                is Answer.Choice -> Answer.ImageChoice(
                    newAnswer.choices.mapIndexed { index, choiceModel ->
                        if (index == answerIndex)
                            ImageChoiceModel(
                                answer ?: choiceModel.text,
                                "${urlSource ?: ""}"
                            )
                        else
                            ImageChoiceModel(choiceModel.text, "")
                    }.toPersistentList()
                )

                is Answer.ImageChoice -> Answer.ImageChoice(
                    newAnswer.imageChoice.mapIndexed { index, imageChoiceModel ->
                        if (index == answerIndex)
                            ImageChoiceModel(
                                answer ?: imageChoiceModel.text,
                                "${urlSource ?: imageChoiceModel.imageUrl}"
                            )
                        else
                            ImageChoiceModel(imageChoiceModel.text, imageChoiceModel.imageUrl)
                    }.toPersistentList()
                )

                else -> null
            }

            else -> null
        }
        return this
    }

    fun setCorrectAnswer(
        questionNo: Int,
        correctAnswer: String? = null,
    ) = updateState { prevState ->
        val newCorrectAnswers =
            prevState.examInformation.createProblemArea.correctAnswers.toMutableMap()
        newCorrectAnswers[questionNo] = correctAnswer

        prevState.copy(
            examInformation = prevState.examInformation.copy(
                createProblemArea = prevState.examInformation.createProblemArea.copy(
                    correctAnswers = newCorrectAnswers
                )
            ),
        )
    }

    fun addAnswer(
        questionNo: Int,
        answerType: Answer.Type?,
    ) = updateState { prevState ->
        val newAnswers = prevState.examInformation.createProblemArea.answers.toMutableMap()
        val newAnswer = newAnswers[questionNo]

        newAnswers[questionNo] = when (answerType) {
            Answer.Type.ShortAnswer -> throw Exception("주관식은 답이 여러개가 될 수 없습니다.")

            Answer.Type.Choice -> {
                val choiceAnswer = newAnswer as? Answer.Choice ?: throw Exception()
                val newChoices =
                    choiceAnswer.choices.toMutableList().apply { this.add(ChoiceModel("")) }
                Answer.Choice(newChoices.toImmutableList())
            }

            Answer.Type.ImageChoice -> {
                val choiceAnswer = newAnswer as? Answer.ImageChoice ?: throw Exception()
                val newImageChoices = choiceAnswer.imageChoice.toMutableList()
                    .apply { this.add(ImageChoiceModel("", "")) }
                Answer.ImageChoice(newImageChoices.toImmutableList())
            }

            else -> null
        }
        prevState.copy(
            examInformation = prevState.examInformation.copy(
                createProblemArea = prevState.examInformation.createProblemArea.copy(
                    questions = prevState.examInformation.createProblemArea.questions,
                    answers = newAnswers
                )
            ),
        )
    }

    // AdditionalInfo
    fun setThumbnail(thumbnail: Any?) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    additionalInfoArea = prevState.examInformation.additionalInfoArea.copy(
                        thumbnail = thumbnail,
                    )
                ),
            )
        }
    }

    fun setButtonTitle(buttonTitle: String) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    additionalInfoArea = prevState.examInformation.additionalInfoArea.copy(
                        takeTitle = buttonTitle,
                    )
                ),
            )
        }
    }

    fun setTempTag(tempTag: String) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    additionalInfoArea = prevState.examInformation.additionalInfoArea.copy(
                        tempTag = tempTag,
                    )
                ),
            )
        }
    }

    fun addTag(tag: String) {
        updateState { prevState ->
            val newTags = prevState.examInformation.additionalInfoArea.tags
                .copy { add(tag) }
                .toImmutableList()
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    additionalInfoArea = prevState.examInformation.additionalInfoArea.copy(
                        tags = newTags,
                    )
                ),
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
                    prevState.copy(error = CreateProblemState.ExamInformation.Error(exception))
                }
                postSideEffect {
                    CreateProblemSideEffect.ReportError(exception)
                }
            }
    }

    /** `PhotoPicker` 에서 표시할 이미지 목록을 업데이트합니다. */
    internal fun addGalleryImages(images: List<String>) {
        mutableGalleryImages = persistentListOf<String>().addAll(images)
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
            question = Question.Text(
                text = "",
            ),
            answer = Answer.Short(
                answer = "바보",
            ),
            memo = "test memo 1",
            hint = "test hint 1",
            correctAnswer = "3",
        ),
        Problem(
            question = Question.Text(
                text = "",
            ),
            answer = Answer.Short(
                answer = "바보",
            ),
            memo = "test memo 1",
            hint = "test hint 1",
            correctAnswer = "3",
        ),
        Problem(
            question = Question.Text(
                text = "",
            ),
            answer = Answer.Short(
                answer = "바보",
            ),
            memo = "test memo 1",
            hint = "test hint 1",
            correctAnswer = "3",
        ),
        Problem(
            question = Question.Text(
                text = "",
            ),
            answer = Answer.Short(
                answer = "바보",
            ),
            memo = "test memo 1",
            hint = "test hint 1",
            correctAnswer = "3",
        ),
        Problem(
            question = Question.Text(
                text = "",
            ),
            answer = Answer.Short(
                answer = "바보",
            ),
            memo = "test memo 1",
            hint = "test hint 1",
            correctAnswer = "3",
        ),
    ),
    userId = 1,
)
