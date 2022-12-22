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
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.suspendCancellableCoroutine
import team.duckie.app.android.feature.photopicker.PhotoPicker
import team.duckie.app.android.feature.ui.create.problem.R
import team.duckie.app.android.feature.ui.create.problem.common.PrevAndNextTopAppBar
import team.duckie.app.android.feature.ui.create.problem.common.TitleAndComponent
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.util.compose.CoroutineScopeContent
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.launch
import team.duckie.app.android.util.compose.rememberToast
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.icon.QuackIcon
import kotlin.coroutines.resume

/** 문제 만들기 3단계 (추가정보 입력) Screen */
@Composable
fun AdditionalInformationScreen(modifier: Modifier) = CoroutineScopeContent {
    val context = LocalContext.current
    val vm = LocalViewModel.current as CreateProblemViewModel
    val state =
        vm.state.collectAsStateWithLifecycle().value.examInformation.additionalInfoArea
    val keyboard = LocalSoftwareKeyboardController.current
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val toast = rememberToast()

    // Gallery 관련
    val selectedGalleryImage = remember(state.thumbnail) { state.thumbnail }
    val galleryImages = remember(vm.galleryImages) { vm.galleryImages }
    val galleryImagesSelections = remember(vm.galleryImages) {
        mutableStateListOf(
            elements = Array(
                size = galleryImages.size,
                init = { false },
            )
        )
    }
    var photoPickerVisible by remember { mutableStateOf(false) }
    var galleryImagesSelectionIndex by remember { mutableStateOf(0) }

    // 권한 관련
    // TODO(riflockle7): 추후 공용 권한 가이드 나올 시 제거 필요
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            launch {
                vm.loadGalleryImages()
                photoPickerVisible = true   // 추후 삭제될 코드여서 변수 위치에 따라 오류나는 케이스는 고려하지 않음
            }
        } else {
            toast("권한이 없습니다,")
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
                        .background(Color(0xffd9d9d9))
                )

                // 선택 목록
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 24.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 24.dp,
                        ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 기본 썸네일 선택
                    // TODO(riflockle7): quack_ic_profile_24 -> 백앤드에서 받아온 이미지
                    AdditionalBottomSheetThumbnailLayout(
                        title = "기본 썸네일",
                        src = team.duckie.quackquack.ui.R.drawable.quack_ic_profile_24,
                        onClick = {
                            launch {
                                vm.setThumbnail(team.duckie.quackquack.ui.R.drawable.quack_ic_profile_24)
                                sheetState.hide()
                            }
                        }
                    )

                    // 갤러리에서 선택
                    AdditionalBottomSheetThumbnailLayout(
                        title = "",
                        src = team.duckie.quackquack.ui.R.drawable.quack_ic_area_24,
                        onClick = {
                            launch {
                                // TODO(riflockle7): 추후 공용 권한 가이드 나올 시 제거 필요
                                val permissionCheckResult = checkPermission(context, launcher)

                                if (permissionCheckResult) {
                                    vm.loadGalleryImages()
                                }
                                photoPickerVisible = permissionCheckResult
                            }
                        }
                    )
                }
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 상단 탭바
            PrevAndNextTopAppBar(
                onLeadingIconClick = {
                    launch { vm.navigateStep(CreateProblemStep.CreateProblem) }
                },
                trailingText = stringResource(id = R.string.additional_information_next),
                onTrailingTextClick = {
                    launch { vm.onClickArrowBack() }
                },
                trailingTextEnabled = vm.isAllFieldsNotEmpty(),
            )

            // 컨텐츠 Layout
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                // 썸네일 선택 (어떤 분야를 좋아하나요?) Layout
                AdditionalThumbnailLayout(thumbnail = selectedGalleryImage) {
                    launch {
                        keyboard?.hide()
                        sheetState.animateTo(ModalBottomSheetValue.Expanded)
                    }
                }

                // 시험 응시 텍스트 선택 (시험 응시하기 버튼) Layout
                AdditionalTakeLayout()

                // 시험 태그 추가 (태그 추가) Layout
                AdditionalTagLayout()
            }
        }
    }

    // 갤러리 썸네일 선택 picker
    if (photoPickerVisible) {
        PhotoPicker(
            modifier = Modifier
                .padding(top = systemBarPaddings.calculateTopPadding())
                .fillMaxSize()
                .background(color = QuackColor.White.composeColor),
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
                    photoPickerVisible = false
                    sheetState.hide()
                }
            },
            onAddClick = {
                launch {
                    vm.setThumbnail(galleryImages[galleryImagesSelectionIndex].toUri())
                    photoPickerVisible = false
                    sheetState.hide()
                }
            },
        )
    }
}

/** 썸네일 선택 (어떤 분야를 좋아하나요?) Layout */
@Composable
private fun AdditionalThumbnailLayout(
    thumbnail: Any?,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val thumbnailWidthDp = screenWidthDp - 32.dp
    val imeInsets = WindowInsets.ime
    val navigationBarInsets = WindowInsets.navigationBars

    TitleAndComponent(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        stringResource = R.string.category_title
    ) {
        QuackImage(
            size = DpSize(thumbnailWidthDp, thumbnailWidthDp * 240 / 328),
            contentScale = ContentScale.FillWidth,
            src = thumbnail,
        )

        // 썸네일 종류 선택 버튼
        // TODO(riflockle7): trailingIcon 추가 필요
        QuackLargeButton(
            modifier = Modifier
                .padding(top = 4.dp)
                // TODO(riflockle7): QuackLargeButton offset 추가되는 것 제거 필요
                .offset {
                    val imeHeight = imeInsets.getBottom(this)
                    val nagivationBarHeight = navigationBarInsets.getBottom(this)
                    // ime height 에 navigation height 가 포함되는 것으로 추측됨
                    val yOffset = imeHeight
                        .minus(nagivationBarHeight)
                        .coerceAtLeast(0)
                    IntOffset(x = 0, y = yOffset)
                },
            type = QuackLargeButtonType.Border,
            text = stringResource(id = R.string.additional_information_thumbnail_select),
            leadingIcon = QuackIcon.ArrowRight,
            onClick = onClick,
        )
    }
}

/** 시험 응시 텍스트 선택 (시험 응시하기 버튼) Layout */
@Composable
private fun AdditionalTakeLayout() {
    val vm = LocalViewModel.current as CreateProblemViewModel
    val state = vm.state.collectAsStateWithLifecycle().value.examInformation

    TitleAndComponent(
        modifier = Modifier.padding(top = 48.dp),
        stringResource = R.string.additional_information_take_title,
    ) {
        QuackBasicTextField(
            text = state.additionalInfoArea.takeTitle,
            onTextChanged = vm::setButtonTitle,
            placeholderText = stringResource(id = R.string.additional_information_take_input_hint),
        )
    }
}

/** 시험 태그 추가 (태그 추가) Layout */
@Composable
private fun AdditionalTagLayout() {
    val vm = LocalViewModel.current as CreateProblemViewModel
    val state = vm.state.collectAsStateWithLifecycle().value.examInformation

    TitleAndComponent(
        modifier = Modifier.padding(top = 48.dp),
        stringResource = R.string.additional_information_tag_title,
    ) {
        QuackBasicTextField(
            text = state.additionalInfoArea.tempTag,
            onTextChanged = vm::setTempTag,
            placeholderText = stringResource(id = R.string.additional_information_tag_input_hint),
        )
    }
}

/** BottomSheet 썸네일 선택 아이템 Layout */
@Composable
private fun AdditionalBottomSheetThumbnailLayout(
    title: String,
    src: Any,
    onClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val thumbnailWidthDp = (screenWidthDp - 40.dp) / 2
    Column {
        QuackImage(
            size = DpSize(thumbnailWidthDp, thumbnailWidthDp * 240 / 328),
            contentScale = ContentScale.FillWidth,
            src = src,
            onClick = onClick,
        )

        QuackSubtitle(
            modifier = Modifier.padding(top = 8.dp),
            text = title,
        )
    }
}

/**
 * 권한을 체크한다.
 * TODO(riflockle7): 추후 공용 권한 가이드 나올 시 제거 필요
 */
private suspend fun checkPermission(
    context: Context,
    launcher: ManagedActivityResultLauncher<String, Boolean>
) = suspendCancellableCoroutine {
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_EXTERNAL_STORAGE
        ) -> it.resume(true)

        // Asking for permission
        else -> launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}
