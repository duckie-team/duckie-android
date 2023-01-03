/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalLifecycleComposeApi::class
)

package team.duckie.app.android.feature.ui.create.problem.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.photopicker.PhotoPicker
import team.duckie.app.android.feature.ui.create.problem.R
import team.duckie.app.android.feature.ui.create.problem.common.PrevAndNextTopAppBar
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemPhotoState
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.util.compose.CoroutineScopeContent
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.launch
import team.duckie.app.android.util.compose.rememberToast
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.fastForEach
import team.duckie.app.android.util.kotlin.npe
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.border.applyAnimatedQuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBasic2TextField
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackBorderTextField
import team.duckie.quackquack.ui.component.QuackDialog
import team.duckie.quackquack.ui.component.QuackDropDownCard
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackRoundCheckBox
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable

private const val TopAppBarLayoutId = "CreateProblemScreenTopAppBarLayoutId"
private const val ContentLayoutId = "CreateProblemScreenContentLayoutId"
private const val GalleryListLayoutId = "CreateProblemScreenGalleryListLayoutId"
// private const val BottomLayoutId = "CreateProblemScreenBottomLayoutId"

private const val MaximumChoice = 5

private val createProblemMeasurePolicy = MeasurePolicy { measurableItems, constraints ->
    // 1. topAppBar, createProblemButton 높이값 측정
    val looseConstraints = constraints.asLoose()

    val topAppBarMeasurable = measurableItems.fastFirstOrNull { measureItem ->
        measureItem.layoutId == TopAppBarLayoutId
    }?.measure(looseConstraints) ?: npe()
    val topAppBarHeight = topAppBarMeasurable.height

    val contentThresholdHeight = constraints.maxHeight - topAppBarHeight
    val contentConstraints = constraints.copy(
        minHeight = contentThresholdHeight,
        maxHeight = contentThresholdHeight,
    )
    val contentMeasurable = measurableItems.fastFirstOrNull { measurable ->
        measurable.layoutId == ContentLayoutId
    }?.measure(contentConstraints) ?: npe()

    // 3. 위에서 추출한 값들을 활용해 레이아웃 위치 처리
    layout(
        width = constraints.maxWidth,
        height = constraints.maxHeight,
    ) {
        topAppBarMeasurable.place(
            x = 0,
            y = 0,
        )
        contentMeasurable.place(
            x = 0,
            y = topAppBarHeight,
        )
    }
}

/** 문제 만들기 2단계 (문제 만들기) Screen */
@Composable
fun CreateProblemScreen(modifier: Modifier) = CoroutineScopeContent {
    val context = LocalContext.current
    val vm = LocalViewModel.current as CreateProblemViewModel
    val examInformationState = vm.state.collectAsStateWithLifecycle().value.examInformation
    val state = examInformationState.createProblemArea
    val keyboard = LocalSoftwareKeyboardController.current
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val toast = rememberToast()
    val permissionErrorMessage =
        stringResource(id = R.string.create_problem_permission_toast_message)
    var selectedQuestionIndex: Int? by remember { mutableStateOf(null) }
    val correctAnswers = remember(state.correctAnswers) { state.correctAnswers }

    // Gallery 관련
    val selectedQuestions = remember(state.questions) { state.questions }
    val selectedAnswers = remember(state.answers) { state.answers }
    val galleryImages = remember(vm.galleryImages) { vm.galleryImages }
    val galleryImagesSelections = remember(vm.galleryImages) {
        mutableStateListOf(
            elements = Array(
                size = galleryImages.size,
                init = { false },
            )
        )
    }
    val photoState = remember(examInformationState.photoState) { examInformationState.photoState }
    // first: questionIndex / second: answerIndex
    var deleteDialogNo: (Pair<Int, Int?>)? by remember { mutableStateOf(null) }
    var galleryImagesSelectionIndex by remember { mutableStateOf(0) }

    // 단일 권한 설정 launcher
    // TODO(riflockle7): 권한 로직은 추후 PermissionViewModel 과 같이 쓰면서 지워질 예정
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            launch { vm.updatePhotoState(null) }
        } else {
            toast(permissionErrorMessage)
        }
    }

    BackHandler {
        if (sheetState.isVisible) {
            hideBottomSheet(sheetState) { selectedQuestionIndex = null }
        } else if (photoState != null) {
            vm.updatePhotoState(null)
        } else {
            vm.navigateStep(CreateProblemStep.ExamInformation)
        }
    }

    LaunchedEffect(Unit) {
        val sheetStateFlow = snapshotFlow { sheetState.currentValue }
        sheetStateFlow.collect { state ->
            if (state == ModalBottomSheetValue.Hidden) {
                keyboard?.hide()
            }
        }
    }

    ModalBottomSheetLayout(
        modifier = modifier,
        sheetState = sheetState,
        sheetBackgroundColor = QuackColor.White.composeColor,
        scrimColor = QuackColor.Dimmed.composeColor,
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
        ),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // 상단 회색 마크
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(QuackColor.Gray2.composeColor)
                )

                // 선택 목록
                Column(modifier = Modifier.fillMaxWidth()) {
                    listOf(
                        // 객관식/글 버튼
                        stringResource(id = R.string.create_problem_bottom_sheet_title_choice_text)
                                to Answer.Type.Choice,
                        // 객관식/사진 버튼
                        stringResource(id = R.string.create_problem_bottom_sheet_title_choice_media)
                                to Answer.Type.ImageChoice,
                        // 주관식 버튼
                        stringResource(id = R.string.create_problem_bottom_sheet_title_short_form)
                                to Answer.Type.ShortAnswer,
                    ).fastForEach {
                        QuackSubtitle(
                            modifier = Modifier.fillMaxWidth(),
                            padding = PaddingValues(
                                vertical = 12.dp,
                                horizontal = 16.dp,
                            ),
                            text = it.first,
                            onClick = {
                                launch {
                                    selectedQuestionIndex?.let { questionIndex ->
                                        // 특정 문제의 답안 유형 수정
                                        vm.setAnswers(questionIndex, it.second)
                                        selectedQuestionIndex = null
                                    } ?: kotlin.run {
                                        // 문제 추가
                                        vm.addProblem(it.second)
                                    }
                                    hideBottomSheet(sheetState) { selectedQuestionIndex = null }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) {
        Layout(
            modifier = modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            measurePolicy = createProblemMeasurePolicy,
            content = {
                // 상단 탭바
                PrevAndNextTopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .layoutId(TopAppBarLayoutId),
                    onLeadingIconClick = {
                        launch { vm.navigateStep(CreateProblemStep.ExamInformation) }
                    },
                    trailingText = stringResource(id = R.string.next),
                    onTrailingTextClick = {
                        launch { vm.navigateStep(CreateProblemStep.AdditionalInformation) }
                    },
                    trailingTextEnabled = true,
                )

                // 컨텐츠 Layout
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .layoutId(ContentLayoutId),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = object : Arrangement.Vertical {
                        override fun Density.arrange(
                            totalSize: Int,
                            sizes: IntArray,
                            outPositions: IntArray
                        ) {
                            var current = 0
                            sizes.take(sizes.size - 1).forEachIndexed { index, size ->
                                outPositions[index] = current
                                current += size
                            }
                            outPositions[sizes.lastIndex] = totalSize - sizes.last()
                        }
                    },
                    content = {
                        items(selectedQuestions.size) { questionIndex ->
                            val question = selectedQuestions[questionIndex]
                            val answers = selectedAnswers[questionIndex]
                            val correctAnswer = correctAnswers[questionIndex]

                            when (answers) {
                                is Answer.Short -> ShortAnswerProblemLayout(
                                    questionIndex = questionIndex,
                                    question = question,
                                    titleChanged = { newTitle ->
                                        vm.setQuestion(
                                            question.type,
                                            questionIndex,
                                            title = newTitle,
                                        )
                                    },
                                    imageClick = {
                                        openPhotoPicker(
                                            context,
                                            vm,
                                            CreateProblemPhotoState.QuestionImageType(
                                                questionIndex,
                                                question
                                            ),
                                            keyboard,
                                            launcher
                                        )
                                    },
                                    onDropdownItemClick = {
                                        launch {
                                            selectedQuestionIndex = questionIndex
                                            keyboard?.hide()
                                            sheetState.animateTo(ModalBottomSheetValue.Expanded)
                                        }
                                    },
                                    answers = answers,
                                    answerTextChanged = { newTitle, answerIndex ->
                                        vm.setAnswer(
                                            questionIndex,
                                            answerIndex,
                                            Answer.Type.ShortAnswer,
                                            answer = newTitle,
                                        )
                                    },
                                    deleteLongClick = { deleteDialogNo = Pair(questionIndex, null) }
                                )

                                is Answer.Choice -> ChoiceProblemLayout(
                                    questionIndex = questionIndex,
                                    question = question,
                                    titleChanged = { newTitle ->
                                        vm.setQuestion(
                                            question.type,
                                            questionIndex,
                                            title = newTitle,
                                        )
                                    },
                                    imageClick = {
                                        openPhotoPicker(
                                            context,
                                            vm,
                                            CreateProblemPhotoState.QuestionImageType(
                                                questionIndex,
                                                question
                                            ),
                                            keyboard,
                                            launcher
                                        )
                                    },
                                    onDropdownItemClick = {
                                        launch {
                                            selectedQuestionIndex = questionIndex
                                            keyboard?.hide()
                                            sheetState.animateTo(ModalBottomSheetValue.Expanded)
                                        }
                                    },
                                    answers = answers,
                                    answerTextChanged = { newTitle, answerIndex ->
                                        vm.setAnswer(
                                            questionIndex,
                                            answerIndex,
                                            Answer.Type.Choice,
                                            answer = newTitle,
                                        )
                                    },
                                    addAnswerClick = {
                                        vm.addAnswer(
                                            questionIndex = questionIndex,
                                            Answer.Type.Choice
                                        )
                                    },
                                    correctAnswers = correctAnswer,
                                    setCorrectAnswerClick = { newCorrectAnswer ->
                                        vm.setCorrectAnswer(
                                            questionIndex = questionIndex,
                                            correctAnswer = newCorrectAnswer,
                                        )
                                    },
                                    deleteLongClick = { answerIndex: Int? ->
                                        deleteDialogNo = Pair(questionIndex, answerIndex)
                                    }
                                )

                                is Answer.ImageChoice -> ImageChoiceProblemLayout(
                                    questionIndex = questionIndex,
                                    question = question,
                                    titleChanged = { newTitle ->
                                        vm.setQuestion(
                                            question.type,
                                            questionIndex,
                                            title = newTitle,
                                        )
                                    },
                                    imageClick = {
                                        openPhotoPicker(
                                            context,
                                            vm,
                                            CreateProblemPhotoState.QuestionImageType(
                                                questionIndex,
                                                question
                                            ),
                                            keyboard,
                                            launcher
                                        )
                                    },
                                    onDropdownItemClick = {
                                        launch {
                                            selectedQuestionIndex = questionIndex
                                            keyboard?.hide()
                                            sheetState.animateTo(ModalBottomSheetValue.Expanded)
                                        }
                                    },
                                    answers = answers,
                                    answerTextChanged = { newTitle, answerIndex ->
                                        vm.setAnswer(
                                            questionIndex,
                                            answerIndex,
                                            Answer.Type.ImageChoice,
                                            answer = newTitle,
                                        )
                                    },
                                    answerImageClick = { answersNo ->
                                        launch {
                                            val result = imagePermission.check(context)
                                            if (result) {
                                                vm.loadGalleryImages()
                                                vm.updatePhotoState(
                                                    CreateProblemPhotoState.AnswerImageType(
                                                        questionIndex,
                                                        answersNo,
                                                        answers,
                                                    )
                                                )
                                                keyboard?.hide()
                                            } else {
                                                launcher.launch(imagePermission)
                                            }
                                        }
                                    },
                                    addAnswerClick = {
                                        vm.addAnswer(
                                            questionIndex = questionIndex,
                                            Answer.Type.ImageChoice
                                        )
                                    },
                                    correctAnswers = correctAnswer,
                                    setCorrectAnswerClick = { newCorrectAnswer ->
                                        vm.setCorrectAnswer(
                                            questionIndex = questionIndex,
                                            correctAnswer = newCorrectAnswer,
                                        )
                                    },
                                    deleteLongClick = { answerIndex ->
                                        deleteDialogNo = Pair(questionIndex, answerIndex)
                                    }
                                )

                                else -> {}
                            }
                        }

                        item {
                            QuackLargeButton(
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    end = 20.dp,
                                    bottom = 20.dp,
                                    top = 12.dp,
                                ),
                                type = QuackLargeButtonType.Border,
                                text = stringResource(id = R.string.create_problem_add_problem_button),
                                leadingIcon = QuackIcon.Plus,
                            ) {
                                launch {
                                    keyboard?.hide()
                                    sheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            }
                        }
                    }
                )
            }
        )
    }

    // 갤러리 썸네일 선택 picker
    if (photoState != null) {
        PhotoPicker(
            modifier = Modifier
                .padding(top = systemBarPaddings.calculateTopPadding())
                .fillMaxSize()
                .background(color = QuackColor.White.composeColor)
                .layoutId(GalleryListLayoutId),
            imageUris = galleryImages,
            imageSelections = galleryImagesSelections,
            onCameraClick = {},
            onImageClick = { index, _ ->
                galleryImagesSelections[index] = !galleryImagesSelections[index]
                if (galleryImagesSelectionIndex != index) {
                    galleryImagesSelections[galleryImagesSelectionIndex] = false
                }
                galleryImagesSelectionIndex = index
            },
            onCloseClick = {
                launch {
                    vm.updatePhotoState(null)
                    galleryImagesSelections[galleryImagesSelectionIndex] = false
                    hideBottomSheet(sheetState) { selectedQuestionIndex = null }
                }
            },
            onAddClick = {
                launch {
                    with(photoState) {
                        when (this) {
                            is CreateProblemPhotoState.QuestionImageType -> {
                                vm.setQuestion(
                                    Question.Type.Image,
                                    this.questionIndex,
                                    urlSource = galleryImages[galleryImagesSelectionIndex].toUri(),
                                )
                                vm.updatePhotoState(null)
                            }

                            is CreateProblemPhotoState.AnswerImageType -> {
                                vm.setAnswer(
                                    questionIndex,
                                    answerIndex,
                                    Answer.Type.ImageChoice,
                                    urlSource = galleryImages[galleryImagesSelectionIndex].toUri(),
                                )
                                vm.updatePhotoState(null)
                            }

                            else -> {}
                        }
                    }
                    galleryImagesSelections[galleryImagesSelectionIndex] = false
                    hideBottomSheet(sheetState) { selectedQuestionIndex = null }
                }
            },
        )
    }

    QuackDialog(
        title = stringResource(id = R.string.create_problem_delete_dialog_title),
        visible = deleteDialogNo != null,
        leftButtonText = stringResource(id = R.string.cancel),
        leftButtonOnClick = { deleteDialogNo = null },
        rightButtonText = stringResource(id = R.string.ok),
        rightButtonOnClick = {
            deleteDialogNo?.let {
                val questionIndex = it.first
                it.second?.let { answerIndex ->
                    vm.removeAnswer(questionIndex, answerIndex)
                } ?: vm.removeProblem(questionIndex)
            }
            deleteDialogNo = null
        },
        onDismissRequest = { }
    )
}

/** BottomSheet 를 닫습니다. */
private fun CoroutineScopeContent.hideBottomSheet(
    sheetState: ModalBottomSheetState,
    afterAction: (() -> Unit)? = null
) = launch {
    sheetState.hide()
    afterAction?.invoke()
}

/** 사진 선택 화면을 엽니다. */
private fun CoroutineScopeContent.openPhotoPicker(
    context: Context,
    vm: CreateProblemViewModel,
    createProblemPhotoState: CreateProblemPhotoState,
    keyboard: SoftwareKeyboardController?,
    launcher: ManagedActivityResultLauncher<String, Boolean>
) = launch {
    val result = imagePermission.check(context)
    if (result) {
        vm.loadGalleryImages()
        vm.updatePhotoState(createProblemPhotoState)
        keyboard?.hide()
    } else {
        launcher.launch(imagePermission)
    }
}

/** 문제 항목 Layout 내 공통 제목 Layout */
@Composable
private fun CreateProblemTitleLayout(
    questionIndex: Int,
    question: Question?,
    titleChanged: (String) -> Unit,
    imageClick: () -> Unit,
    dropDownTitle: String,
    onDropdownItemClick: (Int) -> Unit,
    deleteLongClick: () -> Unit,
) {
    // TODO(riflockle7): 최상단 Line 없는 TextField 필요
    QuackBasic2TextField(
        modifier = Modifier.quackClickable(onLongClick = { deleteLongClick() }) {},
        text = question?.text ?: "",
        onTextChanged = titleChanged,
        placeholderText = stringResource(
            id = R.string.create_problem_question_placeholder,
            "${questionIndex + 1}"
        ),
        trailingIcon = QuackIcon.Image,
        trailingIconOnClick = imageClick,
    )

    (question as? Question.Image)?.imageUrl?.let {
        QuackImage(
            modifier = Modifier.padding(top = 24.dp),
            src = it,
            size = DpSize(200.dp, 200.dp),
        )
    }

    // TODO(riflockle7): border 없는 DropDownCard 필요
    QuackDropDownCard(
        modifier = Modifier.padding(top = 24.dp),
        text = dropDownTitle,
        onClick = { onDropdownItemClick(questionIndex) }
    )
}

/** 객관식/글 Layout */
@Composable
private fun ChoiceProblemLayout(
    questionIndex: Int,
    question: Question?,
    titleChanged: (String) -> Unit,
    imageClick: () -> Unit,
    onDropdownItemClick: (Int) -> Unit,
    answers: Answer.Choice,
    answerTextChanged: (String, Int) -> Unit,
    addAnswerClick: () -> Unit,
    correctAnswers: String?,
    setCorrectAnswerClick: (String) -> Unit,
    deleteLongClick: (Int?) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CreateProblemTitleLayout(
            questionIndex,
            question,
            titleChanged,
            imageClick,
            answers.type.title,
            onDropdownItemClick
        ) { deleteLongClick(null) }

        answers.choices.forEachIndexed { answerIndex, choiceModel ->
            // TODO(riflockle7): border 가 존재하는 TextField 필요
            QuackBorderTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                text = choiceModel.text,
                onTextChanged = { newAnswer -> answerTextChanged(newAnswer, answerIndex) },
                placeholderText = stringResource(
                    id = R.string.create_problem_answer_placeholder,
                    "${answerIndex + 1}"
                ),
                trailingContent = {
                    val isChecked = correctAnswers == "$answerIndex"
                    Column(
                        modifier = Modifier.quackClickable(
                            onLongClick = { deleteLongClick(answerIndex) },
                            onClick = { setCorrectAnswerClick(if (isChecked) "" else "$answerIndex") }
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        QuackRoundCheckBox(checked = isChecked)

                        if (isChecked) {
                            QuackBody3(
                                modifier = Modifier.padding(top = 2.dp),
                                color = QuackColor.DuckieOrange,
                                text = stringResource(id = R.string.answer),
                            )
                        }
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (answers.choices.size < MaximumChoice) {
            QuackSubtitle(
                modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp),
                text = stringResource(id = R.string.create_problem_add_button),
                onClick = { addAnswerClick() }
            )
        }
    }
}

/**
 * 객관식/사진 Layout
 * // TODO(riflockle7): 정답 체크 연동 필요
 */
@Composable
private fun ImageChoiceProblemLayout(
    questionIndex: Int,
    question: Question?,
    titleChanged: (String) -> Unit,
    imageClick: () -> Unit,
    onDropdownItemClick: (Int) -> Unit,
    answers: Answer.ImageChoice,
    answerTextChanged: (String, Int) -> Unit,
    answerImageClick: (Int) -> Unit,
    addAnswerClick: () -> Unit,
    correctAnswers: String?,
    setCorrectAnswerClick: (String) -> Unit,
    deleteLongClick: (Int?) -> Unit,
) {
    // TODO(riflockle7): 정답 체크 연동 필요 (failed 를 막기 위해 추가한 코드)
    Log.i("riflockle7", "$correctAnswers $setCorrectAnswerClick")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CreateProblemTitleLayout(
            questionIndex,
            question,
            titleChanged,
            imageClick,
            answers.type.title,
            onDropdownItemClick,
        ) { deleteLongClick(null) }

        NoLazyGridItems(
            count = answers.imageChoice.size,
            nColumns = 2,
            paddingValues = PaddingValues(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            itemContent = { answerIndex ->
                val answerItem = answers.imageChoice[answerIndex]

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .applyAnimatedQuackBorder(border = QuackBorder(color = QuackColor.Gray4))
                        .padding(12.dp)
                ) {
                    if (answerItem.imageUrl.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .quackClickable { answerImageClick(answerIndex) }
                                .background(color = QuackColor.Gray4.composeColor)
                                .padding(52.dp),
                        ) {
                            QuackImage(
                                src = QuackIcon.Image,
                                size = DpSize(32.dp, 32.dp)
                            )
                        }
                    } else {
                        QuackImage(
                            src = answerItem.imageUrl,
                            size = DpSize(136.dp, 136.dp),
                            onClick = { answerImageClick(answerIndex) },
                            onLongClick = { deleteLongClick(answerIndex) },
                        )
                    }

                    QuackBasicTextField(
                        text = answers.imageChoice[answerIndex].text,
                        onTextChanged = { newAnswer ->
                            answerTextChanged(newAnswer, answerIndex)
                        },
                        placeholderText = stringResource(
                            id = R.string.create_problem_answer_placeholder,
                            "${answerIndex + 1}"
                        ),
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (answers.imageChoice.size < MaximumChoice) {
            QuackSubtitle(
                modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp),
                text = stringResource(id = R.string.create_problem_add_problem_button),
                onClick = { addAnswerClick() }
            )
        }
    }
}

/** 주관식 Layout */
@Composable
private fun ShortAnswerProblemLayout(
    questionIndex: Int,
    question: Question?,
    titleChanged: (String) -> Unit,
    imageClick: () -> Unit,
    onDropdownItemClick: (Int) -> Unit,
    answers: Answer.Short,
    answerTextChanged: (String, Int) -> Unit,
    deleteLongClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CreateProblemTitleLayout(
            questionIndex,
            question,
            titleChanged,
            imageClick,
            answers.type.title,
            onDropdownItemClick,
        ) { deleteLongClick() }

        QuackBasicTextField(
            text = answers.answer,
            onTextChanged = { newAnswer -> answerTextChanged(newAnswer, 0) },
            placeholderText = stringResource(id = R.string.create_problem_short_answer_placeholder)
        )
    }
}

/**
 * 이미지 권한 체크시 사용해야하는 permission
 * TODO(riflockle7): 권한 로직은 추후 PermissionViewModel 과 같이 쓰면서 지워질 예정
 */
private val imagePermission
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

/** 한 개의 권한을 체크한다. */
private fun String.check(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, this) == PackageManager.PERMISSION_GRANTED
}

/**
 * Lazy 하지 않은 그리드 목록 빌더 (개수로 계산)
 * (https://stackoverflow.com/questions/69336555/fixed-grid-inside-lazycolumn-in-jetpack-compose)
 *
 * @param count 전체 아이템 개수
 * @param nColumns 한 행의 개수
 * @param paddingValues 그리드 목록 패딩
 * @param horizontalArrangement 정렬 방향
 * @param itemContent index 기반으로 만들어지는 아이템 컨텐츠 빌더
 */
@Composable
fun NoLazyGridItems(
    count: Int,
    nColumns: Int,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(Int) -> Unit,
) {
    NoLazyGridItems(
        data = List(count) { it },
        nColumns = nColumns,
        paddingValues = paddingValues,
        horizontalArrangement = horizontalArrangement,
        itemContent = itemContent,
    )
}

/**
 * Lazy 하지 않은 그리드 목록 빌더 (개수로 계산)
 * (https://stackoverflow.com/questions/69336555/fixed-grid-inside-lazycolumn-in-jetpack-compose)
 *
 * @param data 전체 아이템
 * @param nColumns 한 행의 개수
 * @param paddingValues 그리드 목록 패딩
 * @param horizontalArrangement 정렬 방향
 * @param key 사전 실행 로직 함수?
 * @param itemContent data[`index`] 기반으로 만들어지는 아이템 컨텐츠 빌더
 */
@Composable
fun <T> NoLazyGridItems(
    data: List<T>,
    nColumns: Int,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val rows = if (data.isEmpty()) 0 else 1 + (data.count() - 1) / nColumns
    for (rowIndex in 0 until rows) {
        Row(
            modifier = Modifier.padding(paddingValues),
            horizontalArrangement = horizontalArrangement
        ) {
            for (columnIndex in 0 until nColumns) {
                val itemIndex = rowIndex * nColumns + columnIndex
                if (itemIndex < data.count()) {
                    val item = data[itemIndex]
                    androidx.compose.runtime.key(key?.invoke(item)) {
                        Box(
                            modifier = Modifier.weight(1f, fill = true),
                            propagateMinConstraints = true
                        ) {
                            itemContent.invoke(this, item)
                        }
                    }
                } else {
                    Spacer(Modifier.weight(1f, fill = true))
                }
            }
        }
    }
}
