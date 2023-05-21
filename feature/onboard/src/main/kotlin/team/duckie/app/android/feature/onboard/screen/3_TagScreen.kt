/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@file:Suppress(
    "ConstPropertyName",
    "PrivatePropertyName",
    "SpacingBetweenDeclarationsWithAnnotations",
)

package team.duckie.app.android.feature.onboard.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.onboard.R
import team.duckie.app.android.feature.onboard.common.OnboardTopAppBar
import team.duckie.app.android.feature.onboard.common.TitleAndDescription
import team.duckie.app.android.feature.onboard.constant.OnboardStep
import team.duckie.app.android.feature.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.feature.onboard.viewmodel.state.OnboardState
import team.duckie.app.android.common.compose.ui.ImeSpacer
import team.duckie.app.android.common.compose.ToastWrapper
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.asLoose
import team.duckie.app.android.common.compose.invisible
import team.duckie.app.android.common.compose.rememberToast
import team.duckie.app.android.common.compose.systemBarPaddings
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.common.kotlin.fastAny
import team.duckie.app.android.common.kotlin.fastFirstOrNull
import team.duckie.app.android.common.kotlin.fastFlatten
import team.duckie.app.android.common.kotlin.fastForEachIndexed
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.common.kotlin.npe
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBasic2TextField
import team.duckie.quackquack.ui.component.QuackCircleTag
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackSingeLazyRowTag
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackTagType
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.icon.QuackIcon

private val currentStep = OnboardStep.Tag

private const val TagScreenTopAppBarLayoutId = "TagScreenTopAppBar"
private const val TagScreenTagSelectionLayoutId = "TagScreenTagSelection"
private const val TagScreenQuackLargeButtonLayoutId = "TagScreenQuackLargeButton"

private val TagScreenMeasurePolicy = MeasurePolicy { measurables, constraints ->
    val looseConstraints = constraints.asLoose()

    val topAppBarPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == TagScreenTopAppBarLayoutId
    }?.measure(looseConstraints) ?: npe()

    val quackLargeButtonPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == TagScreenQuackLargeButtonLayoutId
    }?.measure(looseConstraints) ?: npe()

    // TagSelection content 에는 vertical scroll 이 있음 (최대 높이 지정 필요)
    val tagSelectionConstraints = looseConstraints.copy(
        maxHeight = constraints.maxHeight - topAppBarPlaceable.height - quackLargeButtonPlaceable.height,
    )
    val tagSelectionPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == TagScreenTagSelectionLayoutId
    }?.measure(tagSelectionConstraints) ?: npe()

    layout(
        width = constraints.maxWidth,
        height = constraints.maxHeight,
    ) {
        topAppBarPlaceable.place(
            x = 0,
            y = 0,
        )
        tagSelectionPlaceable.place(
            x = 0,
            y = topAppBarPlaceable.height,
        )
        quackLargeButtonPlaceable.place(
            x = 0,
            y = constraints.maxHeight - quackLargeButtonPlaceable.height,
        )
    }
}

@Composable
internal fun TagScreen(vm: OnboardViewModel = activityViewModel()) {
    val keyboard = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val toast = rememberToast()

    var isLoadingToFinish by remember { mutableStateOf(false) }
    val addedTags = remember { mutableStateListOf<String>() }

    val onboardState by vm.collectAsState()
    val sheetState =
        rememberModalBottomSheetState(
            ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true,
        )

    LaunchedEffect(sheetState) {
        val sheetStateFlow = snapshotFlow { sheetState.currentValue }
        sheetStateFlow.collect { state ->
            if (state == ModalBottomSheetValue.Hidden) {
                keyboard?.hide()
            }
        }
    }

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch {
            sheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetBackgroundColor = QuackColor.White.composeColor,
        scrimColor = QuackColor.Dimmed.composeColor,
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
        ),
        sheetContent = {
            TagScreenModalBottomSheetContent(
                onDismissRequest = { newAddedTags, clearAction ->
                    coroutineScope.launch {
                        addedTags.addAll(newAddedTags)
                        clearAction()
                        sheetState.hide()
                    }
                },
            )
        },
    ) {
        Layout(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = systemBarPaddings.calculateBottomPadding() + 16.dp),
            content = {
                OnboardTopAppBar(
                    modifier = Modifier.layoutId(TagScreenTopAppBarLayoutId),
                    currentStep = currentStep,
                )
                TagSelection(
                    modifier = Modifier
                        .layoutId(TagScreenTagSelectionLayoutId)
                        .padding(vertical = 12.dp),
                    sheetState = sheetState,
                    addedTags = addedTags,
                    requestRemoveAddedTag = { index ->
                        addedTags.remove(addedTags[index])
                    },
                )
                QuackLargeButton(
                    modifier = Modifier
                        .layoutId(TagScreenQuackLargeButtonLayoutId)
                        .padding(horizontal = 20.dp),
                    text = stringResource(id = R.string.button_start_duckie),
                    type = QuackLargeButtonType.Fill,
                    enabled = true,
                    isLoading = isLoadingToFinish,
                ) {
                    updateUserAndFinishOnboard(
                        coroutineScope = coroutineScope,
                        toast = toast,
                        vm = vm,
                        onboardState = onboardState,
                        addedTags = addedTags,
                        updateIsLoadingToFinishState = { isLoading ->
                            isLoadingToFinish = isLoading
                        },
                    )
                }
            },
            measurePolicy = TagScreenMeasurePolicy,
        )
    }
}

private fun updateUserAndFinishOnboard(
    coroutineScope: CoroutineScope,
    toast: ToastWrapper,
    vm: OnboardViewModel,
    onboardState: OnboardState,
    addedTags: List<String>,
    updateIsLoadingToFinishState: (isLoading: Boolean) -> Unit,
) {
    updateIsLoadingToFinishState(true)
    coroutineScope.launch {
        val profileImageUrlDeferred = onboardState.temporaryProfileImageFile?.let {
            async { vm.uploadProfileImage(file = it) }
        }
        val favoriteTagsDeferred = async {
            vm.updateUserFavoriteTags(names = addedTags)
        }
        val (profileImageUrl, favoriteTagsResult) = awaitAll(
            profileImageUrlDeferred ?: async { "" },
            favoriteTagsDeferred,
        )
        @Suppress("UNCHECKED_CAST")
        val favoriteTags =
            (favoriteTagsResult as List<Result<Tag>>).mapNotNull(Result<Tag>::getOrNull)
        if (favoriteTags.size == addedTags.size) {
            vm.updateUser(
                id = vm.me.id,
                nickname = onboardState.temporaryNickname,
                profileImageUrl = (profileImageUrl as String).takeIf { profileImageUrlDeferred != null },
                favoriteCategories = onboardState.selectedCategories,
                favoriteTags = favoriteTags,
            )
            updateIsLoadingToFinishState(false)
        } else {
            val favoriteTagNames = favoriteTags.fastMap(Tag::name)
            val createFailedTags = addedTags.dropWhile(favoriteTagNames::contains)
            toast(stringRes = R.string.tag_toast_create_failed, createFailedTags.joinToString(", "))
            updateIsLoadingToFinishState(false)
        }
    }
}

@Composable
private fun TagSelection(
    modifier: Modifier,
    sheetState: ModalBottomSheetState,
    addedTags: SnapshotStateList<String>,
    requestRemoveAddedTag: (index: Int) -> Unit,
    vm: OnboardViewModel = activityViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val onboardState by vm.collectAsState()

    val hottestTags = remember(onboardState.selectedCategories) {
        List(
            size = onboardState.selectedCategories.size,
            init = { index ->
                onboardState.selectedCategories[index].popularTags?.fastMap(Tag::name).orEmpty()
            },
        )
    }
    val hottestTagSelections = remember(onboardState.selectedCategories) {
        List(
            size = onboardState.selectedCategories.size,
            init = { index ->
                mutableStateListOf(
                    elements = Array(
                        size = hottestTags[index].size,
                        init = { false },
                    ),
                )
            },
        )
    }

    LaunchedEffect(hottestTagSelections, addedTags) {
        // https://stackoverflow.com/a/70429284/14299073
        val hottestTagSelectionsFlow = snapshotFlow { hottestTagSelections.fastFlatten() }
        val addedTagsFlow = snapshotFlow { addedTags.toList() }

        // hottest tag 에 최소 1개가 선택됐거나, 사용자가 최소 1개의 태그를 추가했을 때
        hottestTagSelectionsFlow.combine(addedTagsFlow) { hottestTagSelections, addedTags ->
            hottestTagSelections.fastAny { it } || addedTags.isNotEmpty()
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        TitleAndDescription(
            modifier = Modifier.padding(horizontal = 20.dp),
            titleRes = R.string.tag_title,
            descriptionRes = R.string.tag_description,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp),
        ) {
            QuackTitle2(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = stringResource(R.string.tag_added_tag),
            )
            if (addedTags.isNotEmpty()) {
                // TODO(sungbin): 반주자 제거 (foundation 으로 합체됨)
                FlowRow(
                    modifier = Modifier.padding(
                        vertical = 10.dp,
                        horizontal = 20.dp,
                    ),
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp,
                ) {
                    addedTags.fastForEachIndexed { index, tag ->
                        QuackCircleTag(
                            text = tag,
                            isSelected = false,
                            trailingIcon = QuackIcon.Close,
                        ) {
                            requestRemoveAddedTag(index)
                        }
                    }
                }
            }
            QuackHeadLine2(
                modifier = Modifier.padding(
                    top = if (addedTags.isNotEmpty()) 0.dp else 4.dp,
                    start = 10.dp,
                ),
                text = stringResource(R.string.tag_add_manual),
                padding = PaddingValues(
                    top = if (addedTags.isNotEmpty()) 0.dp else 4.dp,
                    start = 10.dp,
                    end = 10.dp,
                    bottom = 8.dp,
                ),
                color = QuackColor.DuckieOrange,
                onClick = {
                    coroutineScope.launch {
                        sheetState.show()
                    }
                },
            )
        }
        @AllowMagicNumber(because = "(34 - 8).dp")
        Column(
            modifier = Modifier
                .padding(top = (34 - 8).dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            onboardState.selectedCategories.fastForEachIndexed { categoryIndex, category ->
                QuackSingeLazyRowTag(
                    title = stringResource(R.string.tag_hottest_tag, category.name),
                    items = hottestTags[categoryIndex],
                    itemSelections = hottestTagSelections[categoryIndex],
                    tagType = QuackTagType.Circle(),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    onClick = { tagIndex ->
                        hottestTagSelections[categoryIndex][tagIndex] =
                            !hottestTagSelections[categoryIndex][tagIndex]
                    },
                )
            }
        }
    }
}

@Composable
private fun TagScreenModalBottomSheetContent(
    onDismissRequest: (addedTags: List<String>, clearAction: () -> Unit) -> Unit,
) {
    val toast = rememberToast()
    val context = LocalContext.current

    val inputtedTags = remember { mutableStateListOf<String>() }
    var tagInput by remember { mutableStateOf("") }

    fun updateTagInput() {
        if (tagInput.isNotBlank()) {
            if (inputtedTags.contains(tagInput)) {
                toast(context.getString(R.string.tag_toast_already_added))
                return
            }
            inputtedTags.add(0, tagInput)
            tagInput = ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = systemBarPaddings.calculateBottomPadding()),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 16.dp,
                    horizontal = 16.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackTitle2(text = stringResource(R.string.tag_added_tag))
            QuackSubtitle(
                text = stringResource(R.string.button_done),
                color = QuackColor.DuckieOrange,
                padding = PaddingValues(vertical = 4.dp),
                onClick = {
                    onDismissRequest(inputtedTags) {
                        inputtedTags.clear()
                    }
                },
            )
        }
        if (inputtedTags.isNotEmpty()) {
            Box(modifier = Modifier.padding()) {
                QuackCircleTag(
                    modifier = Modifier
                        .zIndex(0f)
                        .invisible(),
                    text = "",
                    isSelected = false,
                )

                // TODO(sungbin): 애니메이션 출처 밝히기
                FlowRow(
                    modifier = Modifier
                        .zIndex(1f)
                        .padding(
                            vertical = 12.dp,
                            horizontal = 16.dp,
                        ),
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp,
                ) {
                    inputtedTags.fastForEachIndexed { index, tag ->
                        QuackCircleTag(
                            text = tag,
                            isSelected = false,
                            trailingIcon = QuackIcon.Close,
                        ) {
                            inputtedTags.remove(inputtedTags[index])
                        }
                    }
                }
            }
        }
        QuackBasic2TextField(
            text = tagInput,
            onTextChanged = { tagInput = it },
            placeholderText = stringResource(R.string.tag_add_manual_placeholder),
            leadingStartPadding = 16.dp,
            trailingEndPadding = 10.dp,
            trailingIcon = QuackIcon.ArrowSend,
            trailingIconOnClick = ::updateTagInput,
            keyboardActions = KeyboardActions { updateTagInput() },
        )
        ImeSpacer()
    }
}
