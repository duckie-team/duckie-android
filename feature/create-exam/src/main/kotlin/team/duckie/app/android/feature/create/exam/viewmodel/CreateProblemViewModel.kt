/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("ConstPropertyName", "PrivatePropertyName")

package team.duckie.app.android.feature.create.exam.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.android.feature.createproblem.CreateProblemType
import team.duckie.app.android.common.android.image.MediaUtil
import team.duckie.app.android.common.android.network.NetworkUtil
import team.duckie.app.android.common.android.ui.const.Debounce
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.android.viewmodel.context
import team.duckie.app.android.common.kotlin.copy
import team.duckie.app.android.common.kotlin.exception.duckieClientLogicProblemException
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.common.kotlin.exception.isTagAlreadyExist
import team.duckie.app.android.common.kotlin.fastMapIndexed
import team.duckie.app.android.domain.category.usecase.GetCategoriesUseCase
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.ChoiceModel
import team.duckie.app.android.domain.exam.model.ExamBody
import team.duckie.app.android.domain.exam.model.ImageChoiceModel
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.exam.model.ThumbnailType
import team.duckie.app.android.domain.exam.model.getDefaultAnswer
import team.duckie.app.android.domain.exam.model.toChoice
import team.duckie.app.android.domain.exam.model.toCorrectAnswerData
import team.duckie.app.android.domain.exam.model.toImageChoice
import team.duckie.app.android.domain.exam.model.toShort
import team.duckie.app.android.domain.exam.usecase.GetExamThumbnailUseCase
import team.duckie.app.android.domain.exam.usecase.MakeExamUseCase
import team.duckie.app.android.domain.file.constant.FileType
import team.duckie.app.android.domain.file.usecase.FileUploadUseCase
import team.duckie.app.android.domain.gallery.usecase.LoadGalleryImagesUseCase
import team.duckie.app.android.domain.problem.model.ProblemBody
import team.duckie.app.android.domain.problem.usecase.PostProblemUseCase
import team.duckie.app.android.domain.recommendation.model.SearchType
import team.duckie.app.android.domain.search.model.Search
import team.duckie.app.android.domain.search.usecase.GetSearchUseCase
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.tag.repository.TagRepository
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.feature.create.exam.viewmodel.sideeffect.CreateProblemSideEffect
import team.duckie.app.android.feature.create.exam.viewmodel.state.CreateProblemPhotoState
import team.duckie.app.android.feature.create.exam.viewmodel.state.CreateProblemState
import team.duckie.app.android.feature.create.exam.viewmodel.state.CreateProblemStep
import team.duckie.app.android.feature.create.exam.viewmodel.state.FindResultType
import javax.inject.Inject

private const val TagsMaximumCount = 10

@HiltViewModel
@Suppress("LargeClass")
internal class CreateProblemViewModel @Inject constructor(
    application: Application,
    private val submitProblemUseCase: PostProblemUseCase,
    private val makeExamUseCase: MakeExamUseCase,
    private val getExamThumbnailUseCase: GetExamThumbnailUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val fileUploadUseCase: FileUploadUseCase,
    private val getSearchUseCase: GetSearchUseCase,
    private val tagRepository: TagRepository,
    private val loadGalleryImagesUseCase: LoadGalleryImagesUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ContainerHost<CreateProblemState, CreateProblemSideEffect>, AndroidViewModel(application) {

    override val container =
        container<CreateProblemState, CreateProblemSideEffect>(CreateProblemState())

    suspend fun initState() {
        val examId = savedStateHandle.getStateFlow(Extras.ExamId, -1).value
        val createProblemType = savedStateHandle.getStateFlow(
            Extras.CreateProblemType,
            CreateProblemType.Exam,
        ).value
        val createProblemStep = if (createProblemType == CreateProblemType.Problem) {
            CreateProblemStep.CreateProblem
        } else {
            CreateProblemStep.ExamInformation
        }
        val isEditMode = examId != -1 && createProblemType == CreateProblemType.Exam

        if (!NetworkUtil.isNetworkAvailable(this.context)) {
            loadErrorPage(isNetworkError = true)
            return
        }

        // 문제 생성 모드
        if (!isEditMode) {
            intent {
                getMeUseCase().onSuccess { me ->
                    reduce {
                        state.copy(
                            me = me,
                            createProblemType = createProblemType,
                            editExamId = examId,
                            createExamStep = createProblemStep,
                        )
                    }
                }.onFailure {
                    postSideEffect(CreateProblemSideEffect.ReportError(it))
                    loadErrorPage()
                }
            }
        }
        // 문제 수정 모드
        else {
            // TODO(riflockle7): 추후 기능 제공 시 구현 필요
            intent { postSideEffect(CreateProblemSideEffect.ReportError(Throwable("미지원 기능"))) }
        }
    }

    private suspend fun loadErrorPage(isNetworkError: Boolean = false) = intent {
        reduce {
            state.copy(
                createExamStep = CreateProblemStep.Error,
                isNetworkError = isNetworkError,
            )
        }
    }

    suspend fun refresh() {
        intent {
            reduce {
                state.copy(
                    isNetworkError = false,
                    createExamStep = CreateProblemStep.Loading,
                )
            }
        }

        initState()
    }

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

    /** tags 검색 flow. 실질 동작 로직은 apply 내에 명세되어 있다. */
    @OptIn(FlowPreview::class)
    private val _getSearchTagsFlow: MutableSharedFlow<String> = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    ).apply {
        viewModelScope.launch {
            this@apply.debounce(Debounce.SearchSecond).collectLatest { query ->
                intent {
                    getSearchUseCase(query = query, page = 1, type = SearchType.Tags)
                        .onSuccess {
                            val searchResults = (it as Search.TagSearch).tags
                                .take(TagsMaximumCount)
                                .toImmutableList()

                            reduce {
                                when (state.findResultType) {
                                    FindResultType.MainTag -> {
                                        state.copy(
                                            examInformation = state.examInformation.copy(
                                                searchMainTag = state.examInformation.searchMainTag.copy(
                                                    searchResults = searchResults,
                                                ),
                                            ),
                                        )
                                    }

                                    FindResultType.SubTags -> {
                                        state.copy(
                                            additionalInfo = state.additionalInfo.copy(
                                                searchSubTags = state.additionalInfo.searchSubTags.copy(
                                                    searchResults = searchResults,
                                                ),
                                            ),
                                        )
                                    }
                                }
                            }
                        }
                        .onFailure {
                            postSideEffect(CreateProblemSideEffect.ReportError(it))
                        }
                }
            }
        }
    }

    // 공통
    /** 문제를 제출한다. */
    internal fun submitExam() = intent {
        reduce { state.copy(isMakeExamUploading = true) }
        runCatching { generateProblemBody() }
            .onSuccess { problemBody ->
                submitProblemUseCase(problemBody)
                    .onSuccess { problem: Problem ->
                        reduce { state.copy(isMakeExamUploading = false) }
                        print(problem)
                        finishCreateProblem()
                    }.onFailure {
                        reduce { state.copy(isMakeExamUploading = false) }
                        it.printStackTrace()
                        postSideEffect(CreateProblemSideEffect.ReportError(it))
                    }
            }.onFailure {
                reduce { state.copy(isMakeExamUploading = false) }
                it.printStackTrace()
                postSideEffect(CreateProblemSideEffect.ReportError(it))
            }
    }

    /** 시험 컨텐츠를 만든다. */
    internal fun makeExam() = intent {
        reduce { state.copy(isMakeExamUploading = true) }
        runCatching { generateExamBody() }
            .onSuccess { examBody ->
                makeExamUseCase(examBody)
                    .onSuccess { isSuccess: Boolean ->
                        reduce { state.copy(isMakeExamUploading = false) }
                        print(isSuccess)
                        finishCreateProblem()
                    }.onFailure {
                        reduce { state.copy(isMakeExamUploading = false) }
                        it.printStackTrace()
                        postSideEffect(CreateProblemSideEffect.ReportError(it))
                    }
            }.onFailure {
                reduce { state.copy(isMakeExamUploading = false) }
                it.printStackTrace()
                postSideEffect(CreateProblemSideEffect.ReportError(it))
            }
    }

    /** request 를 위해 필요한 [ExamBody] 를 생성한다. */
    private fun generateExamBody(): ExamBody {
        val examInformationState = container.stateFlow.value.examInformation
        val createExamState = container.stateFlow.value.createExam
        val additionalInfoState = container.stateFlow.value.additionalInfo

        val serverCorrectAnswers =
            createExamState.correctAnswers.fastMapIndexed { index, correctAnswer ->
                correctAnswer.toCorrectAnswerData(createExamState.answers[index])
            }
        val problems = createExamState.questions.fastMapIndexed { index, question ->
            Problem(
                index,
                question,
                createExamState.answers[index],
                serverCorrectAnswers[index],
                createExamState.hints[index],
                createExamState.memos[index],
                null,
            )
        }.toPersistentList()

        val subTagIds = additionalInfoState.subTags.map { it.id }.toPersistentList()
        return ExamBody(
            title = examInformationState.examTitle,
            description = examInformationState.examDescription,
            mainTagId = examInformationState.categories.first().id,
            subTagIds = if (subTagIds.isEmpty()) {
                null
            } else {
                subTagIds
            },
            categoryId = examInformationState.selectedCategory?.id
                ?: duckieResponseFieldNpe("선택된 카테고리가 있어야 합니다."),
            certifyingStatement = examInformationState.certifyingStatement,
            thumbnailUrl = "${additionalInfoState.thumbnail}",
            thumbnailType = additionalInfoState.thumbnailType,
            problems = problems,
            buttonTitle = additionalInfoState.takeTitle,
            status = null, // 운영용
            type = "text", // TODO(riflockle7): 값 넣는 스펙에 대해 확인 필요
            totalProblemCount = problems.size,
        )
    }

    /** request 를 위해 필요한 [Problem] 를 생성한다. */
    private fun generateProblemBody(): ProblemBody {
        val rootState = container.stateFlow.value
        val createExamState = container.stateFlow.value.createExam

        val serverCorrectAnswer = createExamState.correctAnswers.first()
            .toCorrectAnswerData(createExamState.answers.first())

        return ProblemBody(
            question = createExamState.questions.first(),
            answer = createExamState.answers.first(),
            correctAnswer = serverCorrectAnswer,
            examId = rootState.editExamId,
            wrongAnswerMessage = "",
            solutionImageUrl = "",
            hint = createExamState.hints.first(),
            memo = createExamState.memos.first(),
            status = "READY",
        )
    }

    /** 문제 만들기 화면을 종료한다. */
    private fun finishCreateProblem() = intent {
        postSideEffect(CreateProblemSideEffect.FinishActivity)
    }

    /** 특정 태그의 닫기 버튼을 클릭한다. 대체로 삭제 로직이 실행된다. */
    internal fun onClickCloseTag(index: Int = 0) = intent {
        reduce {
            when (state.createExamStep) {
                CreateProblemStep.ExamInformation -> {
                    state.copy(
                        examInformation = state.examInformation.run {
                            copy(
                                isMainTagSelected = false,
                                searchMainTag = searchMainTag.copy(
                                    results = persistentListOf(),
                                    textFieldValue = "",
                                ),
                            )
                        },
                    )
                }

                CreateProblemStep.Search, CreateProblemStep.AdditionalInformation -> {
                    state.copy(
                        additionalInfo = state.additionalInfo.run {
                            val newSearchResults = searchSubTags.results
                                .copy { removeAt(index) }
                                .toPersistentList()
                            copy(
                                isSubTagsAdded = newSearchResults.isNotEmpty(),
                                searchSubTags = searchSubTags.copy(
                                    results = persistentListOf(),
                                    textFieldValue = "",
                                ),
                            )
                        },
                    )
                }

                else -> duckieClientLogicProblemException(message = "이 화면에는 해당 기능이 없습니다.")
            }
        }
    }

    /** 사진 추가를 위한 작업을 시작 또는 종료한다. */
    internal fun updatePhotoState(photoState: CreateProblemPhotoState?) = intent {
        reduce {
            state.copy(photoState = photoState)
        }
    }

    /** 갤러리에서 이미지 목록을 조회한다. */
    internal fun loadGalleryImages() = intent {
        loadGalleryImagesUseCase()
            .onSuccess { images ->
                postSideEffect(CreateProblemSideEffect.UpdateGalleryImages(images))
            }
            .onFailure { exception ->
                postSideEffect(CreateProblemSideEffect.ReportError(exception))
            }
    }

    /** 서버에 이미지 등록을 요청한다. 요청 결과로 URL 값을 가져온다. */
    private suspend fun requestImage(
        fileType: FileType,
        thumbnailUri: Uri,
        applicationContext: Context?,
    ): String {
        requireNotNull(applicationContext)
        val tempFile = MediaUtil.getOptimizedBitmapFile(applicationContext, thumbnailUri)
        return fileUploadUseCase(tempFile, fileType).getOrThrow()
    }

    /** `PhotoPicker` 에서 표시할 이미지 목록을 업데이트한다. */
    internal fun addGalleryImages(images: List<String>) = intent {
        mutableGalleryImages = persistentListOf(*images.toTypedArray())
    }

    /** 특정 화면으로 이동한다. */
    internal fun navigateStep(step: CreateProblemStep) = intent {
        reduce {
            state.copy(createExamStep = step)
        }
    }

    // ExamInformation
    /** 카테고리 정보를 가져온다. */
    internal fun getCategories() = intent {
        getCategoriesUseCase(false).onSuccess { categories ->
            reduce {
                state.copy(
                    examInformation = state.examInformation.copy(
                        isCategoryLoading = false,
                        categories = categories.toImmutableList(),
                    ),
                )
            }
        }.onFailure {
            print("실패")
        }
    }

    /** 카테고리 항목을 클릭한다. */
    internal fun onClickCategory(index: Int) = intent {
        reduce {
            state.copy(
                examInformation = state.examInformation.copy(
                    categorySelection = index,
                ),
            )
        }
    }

    /** 시험 영역 항목을 등록하기 위한 검색화면으로 진입한다. */
    internal fun goToSearchMainTag(scrollPosition: Int) = intent {
        reduce {
            state.copy(
                createExamStep = CreateProblemStep.Search,
                findResultType = FindResultType.MainTag,
                examInformation = state.examInformation.copy(
                    scrollPosition = scrollPosition,
                ),
            )
        }
    }

    /** 시험 제목을 작성한다. */
    internal fun setExamTitle(examTitle: String) = intent {
        reduce {
            state.copy(
                examInformation = state.examInformation.copy(
                    examTitle = examTitle,
                ),
            )
        }
    }

    /** 설명을 작성한다. */
    internal fun setExamDescription(examDescription: String) = intent {
        reduce {
            state.copy(
                examInformation = state.examInformation.copy(
                    examDescription = examDescription,
                ),
            )
        }
    }

    /** 필적 확인 문구를 작성한다. */
    internal fun setCertifyingStatement(certifyingStatement: String) = intent {
        reduce {
            state.copy(
                examInformation = state.examInformation.copy(
                    certifyingStatement = certifyingStatement,
                ),
            )
        }
    }

    /** 문제 만들기 1단계 화면의 유효성을 체크한다. */
    internal fun examInformationIsValidate(): Boolean {
        return with(container.stateFlow.value.examInformation) {
            categorySelection >= 0 &&
                    isMainTagSelected &&
                    examTitle.isNotEmpty() &&
                    examDescription.isNotEmpty() &&
                    certifyingStatement.isNotEmpty()
        }
    }

    /** 유저가 입력한 정보를 기반으로 Exam 기본 썸네일 이미지를 가져온다. */
    internal fun getExamThumbnail() = intent {
        val state = container.stateFlow.value
        // 이전에 등록된 제목과 동일한 경우 별다른 처리를 하지 않는다.
        if (state.examInformation.prevExamTitle == state.examInformation.examTitle) {
            reduce {
                state.copy(createExamStep = CreateProblemStep.CreateExam)
            }
            return@intent
        }

        getExamThumbnailUseCase(
            examThumbnailBody = state.examInformation.examThumbnailBody(requireNotNull(state.me)),
        ).onSuccess { thumbnail ->
            reduce {
                state.copy(
                    createExamStep = CreateProblemStep.CreateExam,
                    examInformation = state.examInformation.copy(
                        prevExamTitle = state.examInformation.examTitle,
                    ),
                    additionalInfo = state.additionalInfo.copy(thumbnail = thumbnail),
                    defaultThumbnail = thumbnail,
                )
            }
        }.onFailure {
            print("getExamThumbnail 실패")
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
    internal fun addProblem(answerType: Answer.Type) = intent {
        val newQuestion = Question.Text(text = "")
        val newAnswer = answerType.getDefaultAnswer()

        with(state.createExam) {
            val newQuestions = questions.copy { add(newQuestion) }
            val newAnswers = answers.copy { add(newAnswer) }
            val newCorrectAnswers = correctAnswers.copy { add("") }
            val newHints = hints.copy { add("") }
            val newMemos = memos.copy { add("") }

            reduce {
                state.copy(
                    createExam = state.createExam.copy(
                        questions = newQuestions.toPersistentList(),
                        answers = newAnswers.toPersistentList(),
                        correctAnswers = newCorrectAnswers.toPersistentList(),
                        hints = newHints.toPersistentList(),
                        memos = newMemos.toPersistentList(),
                    ),
                )
            }
        }
    }

    /** [questionIndex + 1] 번 문제를 삭제한다. */
    internal fun removeProblem(questionIndex: Int) = intent {
        with(state.createExam) {
            val newQuestions = questions.copy { removeAt(questionIndex) }
            val newAnswers = answers.copy { removeAt(questionIndex) }
            val newCorrectAnswers = correctAnswers.copy { removeAt(questionIndex) }
            val newHints = hints.copy { removeAt(questionIndex) }
            val newMemos = memos.copy { removeAt(questionIndex) }

            reduce {
                state.copy(
                    createExam = state.createExam.copy(
                        questions = newQuestions.toPersistentList(),
                        answers = newAnswers.toPersistentList(),
                        correctAnswers = newCorrectAnswers.toPersistentList(),
                        hints = newHints.toPersistentList(),
                        memos = newMemos.toPersistentList(),
                    ),
                )
            }
        }
    }

    /**
     * [questionIndex + 1] 번 문제를 설정합니다.
     *
     * [특정 문제 타입][questionType]으로 설정되며
     * [텍스트][title], [이미지 소스 목록][urlSource]을 추가적으로 받아 해당 문제를 특정 값으로 초기 설정합니다.
     */
    internal fun setQuestion(
        questionType: Question.Type?,
        questionIndex: Int,
        title: String? = null,
        urlSource: String? = null,
    ) = intent {
        val newQuestions = state.createExam.questions.toMutableList()
        val prevQuestion = newQuestions[questionIndex]
        val newQuestion = when (questionType) {
            Question.Type.Text -> Question.Text(
                title ?: (prevQuestion.text),
            )

            Question.Type.Image -> Question.Image(
                title ?: prevQuestion.text,
                urlSource ?: prevQuestion.mediaUri,
            )

            Question.Type.Audio -> Question.Audio(
                title ?: prevQuestion.text,
                urlSource ?: prevQuestion.mediaUri,
            )

            Question.Type.Video -> Question.Video(
                title ?: prevQuestion.text,
                urlSource ?: prevQuestion.mediaUri,
            )

            else -> null
        }
        newQuestion?.let { newQuestions[questionIndex] = it }

        reduce {
            state.copy(
                createExam = state.createExam.copy(
                    questions = newQuestions.toPersistentList(),
                ),
            )
        }
    }

    /**
     * [questionIndex + 1] 번 문제를 설정합니다.
     * 미디어 설정이 필요한 경우에만 사용되며, 미디어 설정 이후 로직은 [setQuestion] 와 같습니다.
     */
    internal fun setQuestionWithMedia(
        questionType: Question.Type?,
        questionIndex: Int,
        title: String? = null,
        urlSource: Uri?,
        applicationContext: Context,
    ) = viewModelScope.launch {
        urlSource?.run {
            runCatching {
                requestImage(
                    when (questionType) {
                        Question.Type.Image -> FileType.ProblemQuestionImage
                        Question.Type.Audio -> FileType.ProblemQuestionAudio
                        Question.Type.Video -> FileType.ProblemQuestionVideo
                        else -> duckieClientLogicProblemException()
                    },
                    urlSource,
                    applicationContext,
                )
            }.onSuccess { serverUrl ->
                setQuestion(questionType, questionIndex, title, serverUrl)
            }.onFailure {
                intent { postSideEffect(CreateProblemSideEffect.ReportError(it)) }
            }
        } ?: kotlin.run {
            setQuestion(Question.Type.Text, questionIndex, title)
        }
    }

    /** [questionIndex + 1] 번 문제의 문제 타입을 [특정 답안 타입][answerType]으로 변경합니다. */
    internal fun editAnswersType(
        questionIndex: Int,
        answerType: Answer.Type,
    ) = intent {
        val newAnswers = state.createExam.answers.toMutableList()
        newAnswers[questionIndex] = when (answerType) {
            Answer.Type.ShortAnswer -> newAnswers[questionIndex].toShort()
            Answer.Type.Choice -> newAnswers[questionIndex].toChoice()
            Answer.Type.ImageChoice -> newAnswers[questionIndex].toImageChoice()
        }

        reduce {
            state.copy(
                createExam = state.createExam.copy(
                    answers = newAnswers.toPersistentList(),
                ),
            )
        }
    }

    /**
     * [questionIndex + 1] 번 문제의 [answerIndex + 1] 번 답안을 설정합니다.
     * 주관식은 첫 번째 값만 변경합니다.
     *
     * [특정 답안 타입][answerType]으로 설정되며,
     * [텍스트][answer], [이미지 소스][urlSource]을 추가적으로 받아 특정 답안을 해당 값으로 초기 설정합니다.
     */
    internal fun setAnswer(
        questionIndex: Int,
        answerIndex: Int,
        answerType: Answer.Type,
        answer: String? = null,
        urlSource: String? = null,
    ) = intent {
        val newAnswers = state.createExam.answers.toMutableList()

        newAnswers[questionIndex].getEditedAnswers(
            answerIndex,
            answerType,
            answer,
            urlSource,
        ).let { newAnswers[questionIndex] = it }

        val newCorrectAnswers = state.createExam.correctAnswers.toMutableList()
        if (answerType == Answer.Type.ShortAnswer) {
            newCorrectAnswers[questionIndex] = answer ?: ""
        }

        reduce {
            state.copy(
                createExam = state.createExam.copy(
                    answers = newAnswers.toPersistentList(),
                    correctAnswers = newCorrectAnswers.toPersistentList(),
                ),
            )
        }
    }

    /**
     * [questionIndex + 1] 번 문제의 [answerIndex + 1] 번 답안을 설정합니다.
     * 이미지 설정이 필요한 경우에만 사용되며, 이미지 설정 이후 로직은 [setAnswer] 와 같습니다.
     */
    internal fun setAnswerWithImage(
        questionIndex: Int,
        answerIndex: Int,
        answerType: Answer.Type,
        answer: String? = null,
        urlSource: Uri,
        applicationContext: Context,
    ) = viewModelScope.launch {
        urlSource.run {
            runCatching {
                requestImage(FileType.ProblemAnswer, urlSource, applicationContext)
            }.onSuccess { serverUrl ->
                setAnswer(questionIndex, answerIndex, answerType, answer, serverUrl)
            }.onFailure {
                intent { postSideEffect(CreateProblemSideEffect.ReportError(it)) }
            }
        }
    }

    /** [questionIndex + 1] 번 문제의 [answerIndex + 1] 번 답안을 삭제 합니다. */
    internal fun removeAnswer(
        questionIndex: Int,
        answerIndex: Int,
    ) = intent {
        val newAnswers = state.createExam.answers.toMutableList()
        val newAnswer = newAnswers[questionIndex]
        newAnswers[questionIndex] = when (newAnswer) {
            is Answer.Short -> duckieClientLogicProblemException(message = "주관식 답변은 삭제할 수 없습니다.")

            is Answer.Choice -> Answer.Choice(
                newAnswer.choices.copy { removeAt(answerIndex) }.toImmutableList(),
            )

            is Answer.ImageChoice -> Answer.ImageChoice(
                newAnswer.imageChoice.copy { removeAt(answerIndex) }.toImmutableList(),
            )
        }

        // 만약 정답 처리 된 내용을 삭제하는 경우 정답내용(여기에서는 정답 index)를 초기화 시킨다.
        val newCorrectAnswers = state.createExam.correctAnswers.toMutableList()
        if ("$answerIndex" == newCorrectAnswers[questionIndex]) {
            newCorrectAnswers[questionIndex] = ""
        }

        reduce {
            state.copy(
                createExam = state.createExam.copy(
                    answers = newAnswers.toPersistentList(),
                    correctAnswers = newCorrectAnswers.toPersistentList(),
                ),
            )
        }
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
        urlSource: String?,
    ): Answer {
        return when (answerType) {
            Answer.Type.ShortAnswer -> this.toShort()
            Answer.Type.Choice -> this.toChoice(answerIndex, answer)
            Answer.Type.ImageChoice -> this.toImageChoice(answerIndex, answer, urlSource)
        }
    }

    /** [questionIndex + 1] 번 문제의 [정답][correctAnswer]을 설정합니다. */
    internal fun setCorrectAnswer(
        questionIndex: Int,
        correctAnswer: String,
    ) = intent {
        val newCorrectAnswers = state.createExam.correctAnswers.toMutableList()
        newCorrectAnswers[questionIndex] = correctAnswer

        reduce {
            state.copy(
                createExam = state.createExam.copy(
                    correctAnswers = newCorrectAnswers.toPersistentList(),
                ),
            )
        }
    }

    /**
     * [questionIndex + 1] 번 문제의 답안을 추가합니다.
     * [답안의 유형][answerType]에 맞춰 값이 추가 됩니다.
     */
    internal fun addAnswer(
        questionIndex: Int,
        answerType: Answer.Type,
    ) = intent {
        val newAnswers = state.createExam.answers.toMutableList()
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

        reduce {
            state.copy(
                createExam = state.createExam.copy(
                    questions = state.createExam.questions,
                    answers = newAnswers.toPersistentList(),
                ),
            )
        }
    }

    /** 특정 화면으로 이동한다. */
    internal fun nextBtnClick() {
        val state = container.stateFlow.value
        if (state.createProblemType == CreateProblemType.Exam) {
            navigateStep(CreateProblemStep.AdditionalInformation)
        } else if (createExamIsValidate()) {
            submitExam()
        }
    }

    /** 문제 만들기 2단계 화면의 유효성을 체크한다. */
    internal fun createExamIsValidate(): Boolean {
        val state = container.stateFlow.value
        return with(state.createExam) {
            val examCountValidate =
                this.questions.size in state.createProblemType.minCount..state.createProblemType.maxCount
            val questionsValidate = this.questions.asSequence()
                .map { it.validate() }
                .reduce { acc, next -> acc && next }
            val answersValidate = this.answers.asSequence()
                .map { it.validate() }
                .reduce { acc, next -> acc && next }
            val correctAnswersValidate = this.correctAnswers.asSequence()
                .map { it.isNotEmpty() }
                .reduce { acc, next -> acc && next }

            examCountValidate && questionsValidate && answersValidate && correctAnswersValidate
        }
    }

    // AdditionalInfo
    /**
     * 카테고리 썸네일을 선택한다.
     * 선택 후 동작해야하는 로직 (ex. API 요청 등) 을 수행한다.
     */
    internal fun selectThumbnail(
        thumbnailType: ThumbnailType,
        thumbnailUri: Uri? = null,
        applicationContext: Context? = null,
    ) = viewModelScope.launch {
        thumbnailUri?.let {
            runCatching {
                requestImage(FileType.ExamThumbnail, thumbnailUri, applicationContext)
            }.onSuccess { serverUrl ->
                setThumbnailUrl(thumbnailType, serverUrl)
            }.onFailure {
                intent { postSideEffect(CreateProblemSideEffect.ReportError(it)) }
            }
        } ?: setThumbnailUrl(thumbnailType)
    }

    /** 카테고리 썸네일을 정한다. */
    private suspend fun setThumbnailUrl(
        thumbnailType: ThumbnailType,
        thumbnailUri: String? = null,
    ) = intent {
        reduce {
            state.copy(
                additionalInfo = state.additionalInfo.copy(
                    thumbnail = thumbnailUri ?: state.defaultThumbnail,
                    thumbnailType = thumbnailType,
                ),
            )
        }
    }

    /** 시험 응시하기 버튼 제목을 정한다. */
    internal fun setButtonTitle(buttonTitle: String) = intent {
        reduce {
            state.copy(
                additionalInfo = state.additionalInfo.copy(
                    takeTitle = buttonTitle,
                ),
            )
        }
    }

    /** 문제 만들기 3단계 화면의 유효성을 체크한다. */
    private fun additionInfoIsValidate(): Boolean {
        return with(container.stateFlow.value.additionalInfo) {
            thumbnail.toString().isNotEmpty() && takeTitle.isNotEmpty()
        }
    }

    /** 문제 만들기 전체 화면의 유효성을 체크한다. */
    internal fun isAllFieldsNotEmpty(): Boolean {
        return examInformationIsValidate() && createExamIsValidate() && additionInfoIsValidate()
    }

    /** 태그 항목들을 등록하기 위한 검색화면으로 진입한다. */
    internal fun goToSearchSubTags() = intent {
        reduce {
            state.copy(
                createExamStep = CreateProblemStep.Search,
                findResultType = FindResultType.SubTags,
            )
        }
    }

    // Search
    /** Tag 화면에서 [입력한 값][query] 에 맞는 검색 목록 값을 가져온다. */
    private suspend fun searchTags(query: String) {
        _getSearchTagsFlow.emit(query)
    }

    /** 검색 입력 필드의 값을 설정(= 갱신) 한다. */
    internal fun setTextFieldValue(textFieldValue: String) = intent {
        reduce {
            when (state.findResultType) {
                FindResultType.MainTag -> {
                    state.copy(
                        examInformation = state.examInformation.copy(
                            searchMainTag = state.examInformation.searchMainTag.copy(
                                textFieldValue = textFieldValue,
                            ),
                        ),
                    )
                }

                FindResultType.SubTags -> {
                    state.copy(
                        additionalInfo = state.additionalInfo.copy(
                            searchSubTags = state.additionalInfo.searchSubTags.copy(
                                textFieldValue = textFieldValue,
                            ),
                        ),
                    )
                }
            }
        }.run { searchTags(textFieldValue) }
    }

    /** 추천 검색 목록에서 헤더(1번째 항목)을 클릭한다. */
    internal suspend fun onClickSearchListHeader() {
        val state = container.stateFlow.value
        val tagText = when (state.findResultType) {
            FindResultType.MainTag -> state.examInformation.searchMainTag.textFieldValue
            FindResultType.SubTags -> state.additionalInfo.searchSubTags.textFieldValue
        }

        runCatching { tagRepository.create(tagText) }
            .onSuccess {
                exitSearchScreenAfterAddTag(it)
            }.onFailure {
                intent {
                    postSideEffect(
                        if (it.isTagAlreadyExist) {
                            CreateProblemSideEffect.TagAlreadyExist(it, tagText)
                        } else {
                            CreateProblemSideEffect.ReportError(it)
                        },
                    )
                }
            }
    }

    /** 추천 검색 목록에서 헤더(1번째 항목) 이외의 항목을 클릭한다. */
    internal fun onClickSearchList(index: Int) {
        val state = container.stateFlow.value
        val tagText = when (state.findResultType) {
            FindResultType.MainTag ->
                state.examInformation.searchMainTag.searchResults[index]

            FindResultType.SubTags -> state.additionalInfo.searchSubTags.searchResults[index]
        }

        exitSearchScreenAfterAddTag(tagText)
    }

    /**
     * 검색 화면을 종료한다. 태그 추가를 완료한 후 실행되어야 한다.
     * // TODO(riflockle7): 추후 [exitSearchScreen] 와 합칠 수 있을지 확인하기
     */
    private fun exitSearchScreenAfterAddTag(newTag: Tag) = intent {
        reduce {
            when (state.findResultType) {
                FindResultType.MainTag -> {
                    state.copy(
                        createExamStep = CreateProblemStep.ExamInformation,
                        examInformation = state.examInformation.run {
                            copy(
                                isMainTagSelected = true,
                                searchMainTag = searchMainTag.copy(
                                    results = persistentListOf(newTag),
                                    textFieldValue = "",
                                ),
                            )
                        },
                    )
                }

                FindResultType.SubTags -> {
                    state.copy(
                        additionalInfo = state.additionalInfo.run {
                            val newSearchResults = searchSubTags.results
                                .copy { add(newTag) }
                                .toPersistentList()
                            copy(
                                isSubTagsAdded = true,
                                searchSubTags = searchSubTags.copy(
                                    results = newSearchResults,
                                    textFieldValue = "",
                                ),
                            )
                        },
                    )
                }
            }
        }
    }

    /** 검색 입력 필드의 focus 를 변경한다. */
    internal fun onSearchTextFocusChanged(isFocused: Boolean) = intent {
        reduce {
            state.copy(
                examInformation = state.examInformation.copy(
                    examDescriptionFocused = isFocused,
                ),
            )
        }
    }

    /** 검색 화면을 종료한다. */
    internal fun exitSearchScreen(isComplete: Boolean = false) = intent {
        reduce {
            when (state.findResultType) {
                FindResultType.MainTag -> state.copy(
                    createExamStep = CreateProblemStep.ExamInformation,
                    examInformation = state.examInformation.run {
                        copy(
                            isMainTagSelected = false,
                            searchMainTag = searchMainTag.copy(
                                results = persistentListOf(),
                                textFieldValue = "",
                            ),
                        )
                    },
                )

                FindResultType.SubTags -> state.copy(
                    createExamStep = CreateProblemStep.AdditionalInformation,
                    additionalInfo = state.additionalInfo.run {
                        copy(
                            isSubTagsAdded = isComplete,
                            searchSubTags = searchSubTags.copy(
                                results = if (isComplete) {
                                    searchSubTags.results
                                } else {
                                    persistentListOf()
                                },
                                textFieldValue = "",
                            ),
                        )
                    },
                )
            }
        }
    }
}
