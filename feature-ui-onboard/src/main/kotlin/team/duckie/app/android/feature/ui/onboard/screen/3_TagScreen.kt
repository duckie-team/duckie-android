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

package team.duckie.app.android.feature.ui.onboard.screen

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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.datastore.preferences.core.edit
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.datastore.PreferenceKey
import team.duckie.app.android.feature.datastore.dataStore
import team.duckie.app.android.feature.ui.onboard.R
import team.duckie.app.android.feature.ui.onboard.common.OnboardTopAppBar
import team.duckie.app.android.feature.ui.onboard.common.TitleAndDescription
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.shared.ui.compose.ImeSpacer
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.rememberToast
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.kotlin.fastAny
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.fastFlatten
import team.duckie.app.android.util.kotlin.fastForEachIndexed
import team.duckie.app.android.util.kotlin.fastMap
import team.duckie.app.android.util.kotlin.npe
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
    val context = LocalContext.current.applicationContext
    val keyboard = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()

    var isLoadingToFinish by remember { mutableStateOf(false) }
    var isStartable by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val addedTags = remember { mutableStateListOf<String>() }

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
                    startableUpdate = { startable ->
                        isStartable = startable
                    },
                )
                QuackLargeButton(
                    modifier = Modifier
                        .layoutId(TagScreenQuackLargeButtonLayoutId)
                        .padding(horizontal = 20.dp),
                    text = stringResource(id = R.string.button_start_duckie),
                    type = QuackLargeButtonType.Fill,
                    enabled = isStartable,
                    isLoading = isLoadingToFinish,
                ) {
                    isLoadingToFinish = true
                    coroutineScope.launch {
                        val updateUserProfileImageJob = launch {
                            vm.updateUserProfileImage()
                        }
                        val updateUserFavorateTagJob = launch {
                            vm.updateUserFavorateTags(favorateTagNames = addedTags)
                        }
                        joinAll(updateUserProfileImageJob, updateUserFavorateTagJob)
                        vm.updateUser(
                            id = vm.me.id,
                            nickname = vm.me.temporaryNickname,
                            profileImageUrl = vm.me.temporaryProfileImageUrl,
                            favoriteCategories = vm.selectedCategories,
                            favoriteTags = vm.me.temporaryFavoriteTags,
                        )
                        context.dataStore.edit { preference ->
                            preference[PreferenceKey.Onboard.Finish] = true
                        }
                        isLoadingToFinish = false
                        vm.finishOnboard()
                    }
                }
            },
            measurePolicy = TagScreenMeasurePolicy,
        )
    }
}

@Composable
private fun TagSelection(
    modifier: Modifier,
    sheetState: ModalBottomSheetState,
    addedTags: SnapshotStateList<String>,
    requestRemoveAddedTag: (index: Int) -> Unit,
    startableUpdate: (startable: Boolean) -> Unit,
    vm: OnboardViewModel = activityViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val hottestTags = remember(vm.selectedCategories) {
        List(
            size = vm.selectedCategories.size,
            init = { index ->
                vm.selectedCategories[index].popularTags?.fastMap(Tag::name).orEmpty()
            },
        )
    }
    val hottestTagSelections = remember(vm.selectedCategories) {
        List(
            size = vm.selectedCategories.size,
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
        }.collect { startable ->
            startableUpdate(startable)
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
                        sheetState.animateTo(ModalBottomSheetValue.Expanded)
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
            vm.selectedCategories.fastForEachIndexed { categoryIndex, category ->
                QuackSingeLazyRowTag(
                    title = stringResource(R.string.tag_hottest_tag, category),
                    items = hottestTags[categoryIndex],
                    itemSelections = hottestTagSelections[categoryIndex],
                    tagType = QuackTagType.Circle(),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    onClick = { tagIndex ->
                        hottestTagSelections[categoryIndex][tagIndex] = !hottestTagSelections[categoryIndex][tagIndex]
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
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            QuackTitle2(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(R.string.tag_added_tag),
            )
            QuackSubtitle(
                text = stringResource(R.string.button_done),
                color = QuackColor.DuckieOrange,
                padding = PaddingValues(
                    horizontal = 20.dp,
                    vertical = 10.dp,
                ),
                onClick = {
                    onDismissRequest(inputtedTags) {
                        inputtedTags.clear()
                    }
                },
            )
        }
        Box {
            QuackCircleTag(
                modifier = Modifier
                    .zIndex(0f)
                    .drawWithContent { }, // invisible
                text = "",
                isSelected = false,
            )
            FlowRow(
                modifier = Modifier
                    .zIndex(1f)
                    .padding(horizontal = 20.dp),
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
        QuackBasic2TextField(
            modifier = Modifier.padding(top = 16.dp),
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
