/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("MaxLineLength")

package team.duckie.app.android.feature.ui.create.problem.viewmodel

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.ExamParam
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.exam.usecase.GetCategoriesUseCase
import team.duckie.app.android.domain.exam.usecase.MakeExamUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.gallery.usecase.LoadGalleryImagesUseCase
import team.duckie.app.android.feature.ui.create.problem.viewmodel.sideeffect.CreateProblemSideEffect
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemState
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.util.kotlin.copy
import team.duckie.app.android.util.viewmodel.BaseViewModel

private const val ExamTitleMaxLength = 12
private const val ExamDescriptionMaxLength = 30
private const val CertifyingStatementMaxLength = 16

@Singleton
class CreateProblemViewModel @Inject constructor(
    private val makeExamUseCase: MakeExamUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
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
        if (examTitle.length <= ExamTitleMaxLength) {
            updateState { prevState ->
                prevState.copy(
                    examInformation = prevState.examInformation.copy(
                        examTitle = examTitle,
                    ),
                )
            }
        }
    }

    fun setExamDescription(examDescription: String) {
        if (examDescription.length <= ExamDescriptionMaxLength) {
            updateState { prevState ->
                prevState.copy(
                    examInformation = prevState.examInformation.copy(
                        examDescription = examDescription,
                    ),
                )
            }
        }
    }

    fun setCertifyingStatement(certifyingStatement: String) {
        if (certifyingStatement.length <= CertifyingStatementMaxLength) {
            updateState { prevState ->
                prevState.copy(
                    examInformation = prevState.examInformation.copy(
                        certifyingStatement = certifyingStatement,
                    ),
                )
            }
        }
    }

    fun setExamArea(examArea: String) {
        updateState { prevState ->
            prevState.copy(
                examInformation = prevState.examInformation.copy(
                    foundExamArea = prevState.examInformation.foundExamArea.copy(
                        examArea = examArea,
                    )
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
        mutableGalleryImages = mutableGalleryImages.addAll(images)
    }

    fun isAllFieldsNotEmpty(): Boolean {
        return with(currentState.examInformation) {
            categorySelection >= 0 && isExamAreaSelected && examTitle.isNotEmpty() && examDescription.isNotEmpty() && certifyingStatement.isNotEmpty()
        }
    }
}

private val dummyParam = ExamParam(
    // TODO(EvergreenTree97): 문제 만들기 3단계 작업 시 테스트 후 삭제 필요
    title = "제 1회 도로 패션영역",
    description = "도로의 패션을 파헤쳐보자 ㅋㅋ",
    mainTagId = 3,
    subTagIds = persistentListOf(5, 15, 2, 3, 4),
    categoryId = 3,
    thumbnailImageUrl = "https://duckie-resource.s3.ap-northeast-2.amazonaws.com/exam/thumbnail/1669793968813",
    certifyingStatement = "열심히 살지 말라고 하셨다",
    thumbnailType = "image",
    buttonTitle = "TestText",
    isPublic = true,
    problems = persistentListOf(
        Problem(
            question = Question.Text(
                text = "",
                type = "",
            ),
            answer = Answer.ShortAnswer(
                shortAnswer = "바보",
                type = "",
            ),
            memo = "test memo 1",
            hint = "test hint 1",
            correctAnswer = "3",
        ),
        Problem(
            question = Question.Text(
                text = "",
                type = "",
            ),
            answer = Answer.ShortAnswer(
                shortAnswer = "바보",
                type = "",
            ),
            memo = "test memo 1",
            hint = "test hint 1",
            correctAnswer = "3",
        ),
        Problem(
            question = Question.Text(
                text = "",
                type = "",
            ),
            answer = Answer.ShortAnswer(
                shortAnswer = "바보",
                type = "",
            ),
            memo = "test memo 1",
            hint = "test hint 1",
            correctAnswer = "3",
        ),
        Problem(
            question = Question.Text(
                text = "",
                type = "",
            ),
            answer = Answer.ShortAnswer(
                shortAnswer = "바보",
                type = "",
            ),
            memo = "test memo 1",
            hint = "test hint 1",
            correctAnswer = "3",
        ),
        Problem(
            question = Question.Text(
                text = "",
                type = "",
            ),
            answer = Answer.ShortAnswer(
                shortAnswer = "바보",
                type = "",
            ),
            memo = "test memo 1",
            hint = "test hint 1",
            correctAnswer = "3",
        ),
    ),
    userId = 1,
)
