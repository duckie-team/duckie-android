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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.feature.photopicker.PhotoPicker
import team.duckie.app.android.feature.ui.create.problem.R
import team.duckie.app.android.feature.ui.create.problem.common.PrevAndNextTopAppBar
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.util.compose.CoroutineScopeContent
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.launch
import team.duckie.app.android.util.compose.rememberToast
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBasic2TextField
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.component.QuackDropDownCard
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.icon.QuackIcon

private const val TopAppBarLayoutId = "CreateProblemScreenTopAppBarLayoutId"
private const val ContentLayoutId = "CreateProblemScreenContentLayoutId"
private const val GalleryListLayoutId = "CreateProblemScreenGalleryListLayoutId"
// private const val BottomLayoutId = "CreateProblemScreenBottomLayoutId"

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
    val state =
        vm.state.collectAsStateWithLifecycle().value.examInformation.createProblemArea
    val keyboard = LocalSoftwareKeyboardController.current
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val toast = rememberToast()
    val permissionErrorMessage =
        stringResource(id = R.string.create_problem_permission_toast_message)

    // Gallery 관련
    val selectedQuestionGalleryImage =
        remember(state.questionGalleryMap) { state.questionGalleryMap }
    val selectedAnswersGalleryImage = remember(state.answersGalleryMap) { state.answersGalleryMap }
    val galleryImages = remember(vm.galleryImages) { vm.galleryImages }
    val galleryImagesSelections = remember(vm.galleryImages) {
        mutableStateListOf(
            elements = Array(
                size = galleryImages.size,
                init = { false },
            )
        )
    }
    var photoPickerVisible by remember { mutableStateOf<PhotoState?>(null) }
    var galleryImagesSelectionIndex by remember { mutableStateOf(0) }

    // 단일 권한 설정 launcher
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            launch {
                photoPickerVisible = null // TODO(riflockle7): photoState 관리 방법을 고민해볼 예정
            }
        } else {
            toast(permissionErrorMessage)
        }
    }

    BackHandler {
        vm.navigateStep(CreateProblemStep.ExamInformation)
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
                    // 객관식/글 버튼
                    QuackSubtitle(
                        modifier = Modifier
                            .fillMaxWidth(),
                        padding = PaddingValues(
                            top = 12.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 12.dp,
                        ),
                        text = stringResource(id = R.string.create_problem_bottom_sheet_title_choice_text),
                        onClick = {
                            launch {
                                sheetState.hide()
                            }
                        }
                    )

                    // 객관식/사진 버튼
                    QuackSubtitle(
                        modifier = Modifier
                            .fillMaxWidth(),
                        padding = PaddingValues(
                            top = 12.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 12.dp,
                        ),
                        text = stringResource(id = R.string.create_problem_bottom_sheet_title_choice_media),
                        onClick = {
                            launch {
                                sheetState.hide()
                            }
                        }
                    )

                    // 주관식 버튼
                    QuackSubtitle(
                        modifier = Modifier
                            .fillMaxWidth(),
                        padding = PaddingValues(
                            top = 12.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 12.dp,
                        ),
                        text = stringResource(id = R.string.create_problem_bottom_sheet_title_short_form),
                        onClick = {
                            launch {
                                sheetState.hide()
                            }
                        }
                    )
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
                    trailingTextEnabled = vm.isAllFieldsNotEmpty(),
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
                            sizes.take(sizes.size - 1).forEachIndexed { index, it ->
                                outPositions[index] = current
                                current += it
                            }
                            outPositions[sizes.lastIndex] = totalSize - sizes.last()
                        }
                    },
                    content = {
                        items(19) {
                            if (it % 3 == 0) {
                                ShortFormProblemLayout(
                                    index = it,
                                    questionImage = selectedQuestionGalleryImage[it],
                                    imageClick = {
                                        launch {
                                            val result = imagePermission.check(context)
                                            if (result) {
                                                vm.loadGalleryImages()
                                                photoPickerVisible = PhotoState.Question(it)
                                            } else {
                                                launcher.launch(imagePermission)
                                            }
                                        }
                                    },
                                    onDropdownItemClick = {
                                        launch { sheetState.animateTo(ModalBottomSheetValue.Expanded) }
                                    }
                                )
                            } else if (it % 3 == 1) {
                                // TODO(riflockle7): 객관식/글 Layout 구현하기
                            } else {
                                // TODO(riflockle7): 객관식/사진 Layout 구현하기
                            }
                        }

                        item {
                            QuackLargeButton(
                                modifier = Modifier
                                    .padding(
                                        start = 20.dp,
                                        end = 20.dp,
                                        bottom = 20.dp,
                                        top = 12.dp,
                                    ),
                                type = QuackLargeButtonType.Border,
                                text = stringResource(id = R.string.create_problem_add_problem_button),
                                leadingIcon = QuackIcon.Plus,
                            ) {
                                launch { sheetState.animateTo(ModalBottomSheetValue.Expanded) }
                            }
                        }
                    }
                )
            }
        )
    }

    // 갤러리 썸네일 선택 picker
    if (photoPickerVisible != null) {
        Log.i("sangwo-o.lee", "${galleryImages.size}")
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
                    photoPickerVisible = null
                    galleryImagesSelections[galleryImagesSelectionIndex] = false
                    sheetState.hide()
                }
            },
            onAddClick = {
                launch {
                    with(photoPickerVisible) {
                        when {
                            this is PhotoState.Question -> {
                                vm.setQuestionImage(
                                    this.index,
                                    galleryImages[galleryImagesSelectionIndex].toUri()
                                )
                                photoPickerVisible = null
                            }

                            else -> {

                            }
                        }
                    }
                    galleryImagesSelections[galleryImagesSelectionIndex] = false
                    sheetState.hide()
                }
            },
        )
    }
}

/** 문제 만들기 주관식 Layout */
@Composable
fun ShortFormProblemLayout(
    index: Int,
    questionImage: Any?,
    imageClick: () -> Unit,
    onDropdownItemClick: (Int) -> Unit,
) {
    val questionNo = index + 1
    var input = remember { "" }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // TODO(riflockle7): 최상단 Line 없는 TextField 필요
        QuackBasic2TextField(
            modifier = Modifier,
            text = input,
            onTextChanged = { input = it },
            placeholderText = "$questionNo. 문제를 입력해주세요.",
            trailingIcon = QuackIcon.Image,
            trailingIconOnClick = imageClick,
        )

        questionImage?.let {
            QuackImage(
                modifier = Modifier.padding(top = 24.dp),
                src = it,
                size = DpSize(200.dp, 200.dp),
            )
        }

        // TODO(riflockle7): border 없는 DropDownCard 필요
        QuackDropDownCard(
            modifier = Modifier.padding(top = 24.dp),
            text = "주관식",
            onClick = { onDropdownItemClick(index) }
        )

        // TODO(riflockle7): underLine 없는 TextField 필요
        QuackBasicTextField(
            text = input,
            onTextChanged = { input = it },
            placeholderText = "답안 입력"
        )
    }
}


/** 문제 만들기 2단계 최하단 Layout  */
@Deprecated("임시 저장 기능 부활 시 다시 사용")
@Composable
fun CreateProblemBottomLayout() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
//            .layoutId(BottomLayoutId)
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 12.dp,
                bottom = 20.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // 임시저장 버튼
        QuackLargeButton(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.create_problem_temp_save_button),
            type = QuackLargeButtonType.Border,
        ) {
        }

        // 다음 버튼
        QuackLargeButton(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.next),
            type = QuackLargeButtonType.Fill,
            enabled = false,
        ) {

        }
    }
}

sealed class PhotoState {
    data class Question(val index: Int) : PhotoState()
    data class Answers(val index: Int, val number: Int) : PhotoState()
}

/** 이미지 권한 체크시 사용해야하는 permission */
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
