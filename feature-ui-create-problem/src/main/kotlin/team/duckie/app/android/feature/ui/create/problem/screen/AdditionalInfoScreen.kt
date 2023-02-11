/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class,
)

package team.duckie.app.android.feature.ui.create.problem.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.domain.exam.model.ThumbnailType
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.photopicker.PhotoPicker
import team.duckie.app.android.feature.ui.create.problem.R
import team.duckie.app.android.feature.ui.create.problem.common.CreateProblemBottomLayout
import team.duckie.app.android.feature.ui.create.problem.common.FadeAnimatedVisibility
import team.duckie.app.android.feature.ui.create.problem.common.ImeActionNext
import team.duckie.app.android.feature.ui.create.problem.common.PrevAndNextTopAppBar
import team.duckie.app.android.feature.ui.create.problem.common.TitleAndComponent
import team.duckie.app.android.feature.ui.create.problem.common.getCreateProblemMeasurePolicy
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemPhotoState
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.util.compose.GetHeightRatioW328H240
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.app.android.util.compose.rememberToast
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackLazyVerticalGridTag
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackTagType
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable

private const val TopAppBarLayoutId = "AdditionalInfoScreenTopAppBarLayoutId"
private const val ContentLayoutId = "AdditionalInfoScreenContentLayoutId"
private const val BottomLayoutId = "AdditionalInfoScreenBottomLayoutId"

private const val TakeTitleMaxLength = 12;

/** 문제 만들기 3단계 (추가정보 입력) Screen */
@Composable
internal fun AdditionalInformationScreen(
    modifier: Modifier,
    vm: CreateProblemViewModel = activityViewModel(),
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val rootState = vm.collectAsState().value
    val state = rootState.additionalInfo
    val keyboard = LocalSoftwareKeyboardController.current
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val toast = rememberToast()
    val permissionErrorMessage =
        stringResource(id = R.string.create_problem_permission_toast_message)

    // Gallery 관련
    val selectedGalleryImage = remember(state.thumbnail) { state.thumbnail }
    val galleryImages = remember(vm.galleryImages) { vm.galleryImages }
    val galleryImagesSelections = remember(vm.galleryImages) {
        mutableStateListOf(
            elements = Array(
                size = galleryImages.size,
                init = { false },
            ),
        )
    }
    val photoState = remember(rootState.photoState) { rootState.photoState }
    var galleryImagesSelectionIndex by remember { mutableStateOf(0) }

    // 단일 권한 설정 launcher
    // TODO(riflockle7): 권한 로직은 추후 PermissionViewModel 과 같이 쓰면서 지워질 예정
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            vm.loadGalleryImages()
            vm.updatePhotoState(null)
        } else {
            toast(permissionErrorMessage)
        }
    }

    BackHandler {
        if (photoState != null) {
            vm.updatePhotoState(null)
            coroutineScope.launch {
                sheetState.hide()
            }
        } else {
            vm.navigateStep(CreateProblemStep.CreateProblem)
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
                        .background(QuackColor.Gray2.composeColor),
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
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    // 기본 썸네일 선택
                    AdditionalBottomSheetThumbnailLayout(
                        title = "기본 썸네일",
                        src = rootState.defaultThumbnail,
                        onClick = {
                            vm.selectThumbnail(thumbnailType = ThumbnailType.Text)
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                        },
                    )

                    // 갤러리에서 선택
                    AdditionalBottomSheetThumbnailLayout(
                        title = "",
                        src = team.duckie.quackquack.ui.R.drawable.quack_ic_area_24,
                        onClick = {
                            val result = imagePermission.check(context)
                            if (result) {
                                vm.loadGalleryImages()
                                vm.updatePhotoState(CreateProblemPhotoState.AdditionalThumbnailType)
                            } else {
                                launcher.launch(imagePermission)
                            }
                        },
                    )
                }
            }
        },
    ) {
        Layout(
            modifier = modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            measurePolicy = getCreateProblemMeasurePolicy(
                TopAppBarLayoutId,
                ContentLayoutId,
                BottomLayoutId,
            ),
            content = {
                // 상단 탭바
                PrevAndNextTopAppBar(
                    modifier = Modifier.layoutId(TopAppBarLayoutId),
                    onLeadingIconClick = { vm.navigateStep(CreateProblemStep.CreateProblem) },
                )

                // 컨텐츠 Layout
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                        .layoutId(ContentLayoutId),
                ) {
                    // 썸네일 선택 (어떤 분야를 좋아하나요?) Layout
                    AdditionalThumbnailLayout(thumbnail = selectedGalleryImage) {
                        keyboard?.hide()
                        coroutineScope.launch {
                            sheetState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }

                    // 시험 응시 텍스트 선택 (시험 응시하기 버튼) Layout
                    AdditionalTakeLayout()

                    // 시험 태그 추가 (태그 추가) Layout
                    AdditionalSubTagsLayout()
                }

                // 최하단 Layout
                CreateProblemBottomLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .layoutId(BottomLayoutId),
                    tempSaveButtonText = stringResource(id = R.string.create_problem_temp_save_button),
                    tempSaveButtonClick = {},
                    nextButtonText = stringResource(id = R.string.next),
                    nextButtonClick = {
                        coroutineScope.launch {
                            vm.makeExam()
                        }
                    },
                    isValidateCheck = vm::isAllFieldsNotEmpty,
                )
            },
        )
    }

    // 갤러리 썸네일 선택 picker
    if (photoState != null) {
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
                coroutineScope.launch {
                    vm.updatePhotoState(null)
                    sheetState.hide()
                }
            },
            onAddClick = {
                coroutineScope.launch {
                    vm.selectThumbnail(
                        ThumbnailType.Image,
                        galleryImages[galleryImagesSelectionIndex].toUri(),
                        context.applicationContext,
                    )
                    vm.updatePhotoState(null)
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
    onClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val thumbnailWidthDp = screenWidthDp - 32.dp

    TitleAndComponent(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        stringResource = R.string.category_title,
    ) {
        QuackImage(
            size = DpSize(
                thumbnailWidthDp,
                thumbnailWidthDp * GetHeightRatioW328H240,
            ),
            contentScale = ContentScale.FillWidth,
            src = thumbnail,
        )

        // 썸네일 종류 선택 버튼
        // TODO(riflockle7): trailingIcon 추가 필요
        QuackLargeButton(
            modifier = Modifier.padding(top = 4.dp),
            type = QuackLargeButtonType.Border,
            text = stringResource(id = R.string.additional_information_thumbnail_select),
            leadingIcon = QuackIcon.ArrowRight,
            onClick = onClick,
        )
    }
}

/** 시험 응시 텍스트 선택 (시험 응시하기 버튼) Layout */
@Composable
private fun AdditionalTakeLayout(vm: CreateProblemViewModel = activityViewModel()) {
    val state = vm.collectAsState().value.additionalInfo

    TitleAndComponent(
        modifier = Modifier.padding(top = 48.dp),
        stringResource = R.string.additional_information_take_title,
    ) {
        QuackBasicTextField(
            text = state.takeTitle,
            onTextChanged = { newText ->
                if (state.takeTitle.length <= TakeTitleMaxLength) {
                    vm.setButtonTitle(newText)
                } else {
                    vm.setButtonTitle(state.takeTitle)
                }
            },
            placeholderText = stringResource(id = R.string.additional_information_take_input_hint),
            keyboardOptions = ImeActionNext,
        )
    }
}

/** 시험 태그 추가 (태그 추가) Layout */
@Composable
private fun AdditionalSubTagsLayout(vm: CreateProblemViewModel = activityViewModel()) {
    val state = vm.collectAsState().value.additionalInfo

    TitleAndComponent(
        modifier = Modifier.padding(top = 48.dp),
        stringResource = R.string.additional_information_sub_tags_title,
    ) {
        QuackBasicTextField(
            modifier = Modifier.quackClickable {
                vm.goToSearchSubTags()
            },
            text = "",
            onTextChanged = {},
            placeholderText = stringResource(id = R.string.additional_information_sub_tags_placeholder),
            enabled = false,
        )

        if (state.isSubTagsAdded) {
            FadeAnimatedVisibility(visible = true) {
                // TODO(riflockle7): 추후 꽥꽥에서, 전체 너비만큼 태그 Composable 을 넣을 수 있는 Composable 적용 필요
                QuackLazyVerticalGridTag(
                    horizontalSpace = 4.dp,
                    items = state.subTags.fastMap(Tag::name),
                    tagType = QuackTagType.Circle(QuackIcon.Close),
                    onClick = { vm.onClickCloseTag(it) },
                    itemChunkedSize = 3,
                )
            }
        }
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
    Column(
        modifier = Modifier.quackClickable(
            rippleEnabled = true,
            onClick = onClick,
        ),
    ) {
        QuackImage(
            size = DpSize(
                thumbnailWidthDp,
                thumbnailWidthDp * GetHeightRatioW328H240,
            ),
            contentScale = ContentScale.FillWidth,
            src = src,
        )

        QuackSubtitle(
            modifier = Modifier.padding(top = 8.dp),
            text = title,
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

// /** 여러 개의 권한을 체크한다. */
// private fun Array<String>.check(context: Context): Boolean {
//     this.all { permission ->
//         ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
//     }.let { isAllGranted ->
//         return isAllGranted
//     }
// }
